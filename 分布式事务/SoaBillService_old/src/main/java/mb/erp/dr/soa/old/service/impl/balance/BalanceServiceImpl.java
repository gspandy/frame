package mb.erp.dr.soa.old.service.impl.balance;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.bean.BalanceBean;
import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OBillConstant.LimitType;
import mb.erp.dr.soa.constant.O2OBillConstant.TranType;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.old.dao.BalanceMapper;
import mb.erp.dr.soa.old.service.balance.BalanceService;
import mb.erp.dr.soa.old.service.bill.CommonService;
import mb.erp.dr.soa.old.vo.AcTran;
import mb.erp.dr.soa.old.vo.AcTranDtl;
import mb.erp.dr.soa.old.vo.CreditLimitUseVo;
import mb.erp.dr.soa.old.vo.CreditTranDtlVo;
import mb.erp.dr.soa.old.vo.CreditTranVo;
import mb.erp.dr.soa.old.vo.FreezeTran;
import mb.erp.dr.soa.old.vo.FsclAc;
import mb.erp.dr.soa.old.vo.LoanTranVo;
import mb.erp.dr.soa.old.vo.PubLoanDoc;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 资金数据库接口
 * 包含接口：增加余额、正数扣减余额、负数扣减余额、正数冻结金额、负数冻结金额、释放冻结
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         BalanceServiceImpl
 * @since       全流通改造
 */
@Service
public class BalanceServiceImpl implements BalanceService{
	
	private final Logger logger = LoggerFactory.getLogger(BalanceServiceImpl.class);

	final String TRAN_NUM = "TRAN_NUM"; // 往来事务记录号
	final String FREEZE_TRAN_NUM = "FREEZE_TRAN_NUM"; // 冻结事务记录号
	final String CREDIT_TRAN_ID = "CREDIT_TRAN_ID";//信用额度事务流水号
	final String CREDIT_TRAN_DTL_ID = "CREDIT_TRAN_DTL_ID";//信用额度明细事务流水号
	final String LOAN_TRAN_ID = "LOAN_TRAN_ID";//借款事务流水号
	@Resource
	private CommonService commonService;
	@Resource
	private BalanceMapper balanceMapper;
	// balance.low.exception=提示：购货方{0}账户{1}，余额不足，差异额为{2}，请核实
	@Value("${balance.low.exception}")
    private String balanceLowException;
	
	@Value("${balance.param.null}")
	private String  balanceParamNull;
	//balance.fsclAc.exits = 提示：组织{0}没有设置相应的往来账户
	@Value("${balance.fsclAc.exits}")
	private String  balanceFsclAcExits;
//	balance.param.exits=提示：往来账户**已停用，请核实
	@Value("${balance.param.exits}")
	private String  balanceParamExits;
//	balance.param.exits.many=提示：设置了过多的往来账户，请联系系统管理员配置
	@Value("${balance.param.exits.many}")
	private String  balanceParamExitsMany;
	
	/**
	 * 增加余额	给往来账户增加余额(BALANCE)
	 * @return
	 */
	public MsgVo increaseBalance(BalanceBean balanceBean){
		logger.warn("供货方:"+balanceBean.getUid()+" 购货方:"+balanceBean.getCpdUid()+"增加余额:"+balanceBean.getBalance());
		Map<String, Object> result = validateExist(balanceBean.getUid(), balanceBean.getCpdUid());
		MsgVo msg = (MsgVo) result.get("msg");
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		FsclAc fsclAc = (FsclAc) result.get("fsclAc");
		saveActran(fsclAc,balanceBean,true);// 保存往来事务
		fsclAc.setBalance(balanceBean.getBalance()); // 设置余额偏移量
		fsclAc.setDebitVal(0d); // 增加余额时，借款额度不变
		balanceMapper.updateBalance(fsclAc);
		return msg;
	}
	/**
	 * 正数扣减余额	给往来账户扣减余额(BALANCE)，余额不允许出现负数
	 * @return
	 */
	public MsgVo reduceBalancePositive(BalanceBean balanceBean){
		Map<String, Object> result = validateExist(balanceBean.getUid(), balanceBean.getCpdUid());
		MsgVo msg = (MsgVo) result.get("msg");
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		FsclAc fsclAc = (FsclAc) result.get("fsclAc");
		Double balance = balanceBean.getBalance();
		balanceBean.setFsclAcId(fsclAc.getFsclAcId()); // 往来账号，使用专项资金时，查询可支配金额用到
		Double useAmount = balanceMapper.useAmountEnable(balanceBean); //  可支配金额
		Double diff = useAmount-balance; // 可支配金额- 需扣减金额
		logger.warn("useAmount:"+useAmount+" , diff:"+diff+" , 查询可支配金额参数："+JSON.toJSONString(balanceBean));
		Double diff_abs = Math.abs(diff); // diff的绝对值，用于生成借款单和更新往来账户的借款余额
		if (diff < 0) {
			//查询是否有可用专项资金金额  false:表示没有
			if (!reduceCreditBalance(balanceBean,diff_abs,useAmount)) {
				msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
				// 提示：购货方{0}账户{1}，余额不足，差异额为{2}，请核实
				msg.setMsg(MessageFormat.format(balanceLowException, balanceBean.getCpdUid(), fsclAc.getFsclAcId(),diff_abs));
				throw new RuntimeException(msg.getMsg());
			}
			// 生成借款单
			PubLoanDoc pubLoanDoc = new PubLoanDoc();
			pubLoanDoc.setUnitId(balanceBean.getUid());
			pubLoanDoc.setVendeeId(balanceBean.getCpdUid());
			pubLoanDoc.setFsclAcid(fsclAc.getFsclAcId());
			pubLoanDoc.setDocNum(balanceBean.getDocNum());
			pubLoanDoc.setDocType(balanceBean.getDocType().name());
			pubLoanDoc.setLoanAmount(diff_abs);
			pubLoanDoc.setCreateUser("SA");
			pubLoanDoc.setRemark(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())+" ,"+balanceBean.getCpdUid()+"向"+balanceBean.getUid()+"借款"+diff_abs);
			logger.warn("生成借款单，fsclAc:"+fsclAc.getFsclAcId()+" docType:"+balanceBean.getDocType().name()+" docNum:"+balanceBean.getDocNum());
			balanceMapper.savePubLoanDoc(pubLoanDoc);
		}
		saveActran(fsclAc,balanceBean,false);// 保存往来事务，冲单的时候，事务中的发生金额才设置成负数
		fsclAc.setBalance(-balance); // 设置余额偏移量（负数）
		if (diff>0) { // 如果不产生借款，则不更新借款余额
			diff_abs = 0d;
		}
		fsclAc.setDebitVal(diff_abs); // 更新借款余额
		balanceMapper.updateBalance(fsclAc);
		return msg;
	}
	/**
	 * 负数扣减余额	给往来账户扣减余额(BALANCE)，余额允许出现负数
	 * @return
	 * negative
	 */
	public MsgVo reduceBalanceNegative(BalanceBean balanceBean){
		logger.warn("供货方:"+balanceBean.getUid()+" 购货方:"+balanceBean.getCpdUid()+ "金额:"+balanceBean.getBalance()+" "+balanceBean.getDocType()+" "+balanceBean.getDocNum());
		Map<String, Object> result = validateExist(balanceBean.getUid(), balanceBean.getCpdUid());
		MsgVo msg = (MsgVo) result.get("msg");
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		FsclAc fsclAc = (FsclAc) result.get("fsclAc");
		saveActran(fsclAc,balanceBean,false); // 保存往来事务，冲单的时候，事务中的发生金额才设置成负数
		fsclAc.setBalance(-balanceBean.getBalance()); // 设置余额偏移量（负数）
		fsclAc.setDebitVal(0d);// 负数扣减余额时，借款额度不变
		balanceMapper.updateBalance(fsclAc);
		return msg;
	}
	/**
	 * 正数冻结金额	在往来账户冻结金额(FRZ_VAL),可支配金额不足，不允许冻结
	 * @return
	 */
	public MsgVo increaseFreezePositive(BalanceBean balanceBean){
		Map<String, Object> result = validateExist(balanceBean.getUid(), balanceBean.getCpdUid());
		MsgVo msg = (MsgVo) result.get("msg");
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		FsclAc fsclAc = (FsclAc) result.get("fsclAc");
		Double balance = balanceBean.getBalance();
		balanceBean.setFsclAcId(fsclAc.getFsclAcId()); // 往来账号，使用专项资金时，查询可支配金额用到
		Double useAmount = balanceMapper.useAmountEnable(balanceBean); //  可支配金额
		Double diff = useAmount-balance; // 可支配金额- 需冻结金额
		logger.warn("useAmount:"+useAmount+" , diff:"+diff+" , 查询可支配金额参数："+JSON.toJSONString(balanceBean));
		Double diff_abs = Math.abs(diff); // diff的绝对值
		if (diff <0) {
			//查询是否有可用专项资金金额  false:表示没有
			if (!reduceCreditBalance(balanceBean,diff_abs,useAmount)) {
				msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
				// 提示：购货方{0}账户{1}，余额不足，差异额为{2}，请核实
				msg.setMsg(MessageFormat.format(balanceLowException, balanceBean.getCpdUid(), fsclAc.getFsclAcId(),diff_abs));
				throw new RuntimeException(msg.getMsg());
			}
		}
		saveFreezeTran(fsclAc,balanceBean);
		fsclAc.setFrzBal(balanceBean.getBalance()); // 设置冻结金额偏移量
		balanceMapper.updateFrzbal(fsclAc);
		return msg;
	}
	/**
	 * 负数冻结金额	在往来账户冻结金额(FRZ_VAL),可支配金额不足，允许冻结
	 * @return
	 */
	public MsgVo increaseFreezeNegative(BalanceBean balanceBean){
		Map<String, Object> result = validateExist(balanceBean.getUid(), balanceBean.getCpdUid());
		MsgVo msg = (MsgVo) result.get("msg");
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		FsclAc fsclAc = (FsclAc) result.get("fsclAc");
		saveFreezeTran(fsclAc,balanceBean);
		fsclAc.setFrzBal(balanceBean.getBalance()); // 设置冻结金额偏移量
		balanceMapper.updateFrzbal(fsclAc);
		return msg;
	}
	/**
	 * 释放冻结	在往来账户冻结金额(FRZ_VAL),释放冻结
	 * @return
	 */
	public MsgVo reduceFreeze(BalanceBean balanceBean){
		Map<String, Object> result = validateExist(balanceBean.getUid(), balanceBean.getCpdUid());
		MsgVo msg = (MsgVo) result.get("msg");
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		FsclAc fsclAc = (FsclAc) result.get("fsclAc");
		//判断是否冻结过专项资金 
		List<CreditTranVo> creditTranVos = selectCreditTranByDocTypeNum(
				balanceBean.getUid(),balanceBean.getCpdUid(),balanceBean.getDocType(),balanceBean.getDocNum());
		if(creditTranVos != null && creditTranVos.size() > 0){
			//释放专项资金
			CreditTranVo vo = creditTranVos.get(0);
			increaseCredit(vo,balanceBean);
		} 
		balanceBean.setBalance(-balanceBean.getBalance()); // 将偏移量转化成负数，插入事务表和更新往来账户都需要记录负数
		saveFreezeTran(fsclAc,balanceBean);
		fsclAc.setFrzBal(balanceBean.getBalance()); // 设置冻结金额偏移量（负数）
		balanceMapper.updateFrzbal(fsclAc);
		return msg;
	}

	/**
	 * 空验证
	 * @param uid
	 * @param cpdUid
	 * @return
	 */
	private String validateNull(String uid , String cpdUid){
		String result = null;
		if (SoaBillUtils.isBlank(uid) ) {
			result = "供货方编码";
		}else if (SoaBillUtils.isBlank(cpdUid) ) {
			result = "购货方编码";
		}
		
		if (result != null) {
			result = MessageFormat.format(balanceParamNull, "往来账户", result);
		}
		return result;
	}
	
	/**
	 * 有效性验证
	 * @param uid
	 * @param cpdUid
	 * @return
	 */
	private Map<String, Object>  validateExist(String uid , String cpdUid){
		Map<String, Object> result = new HashMap<String, Object>();
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		result.put("msg", msg);
		// 空验证
		String nullProperty = validateNull(uid, cpdUid);
		if (nullProperty != null) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(nullProperty);
			logger.error(nullProperty);
			return result;
		}
		List<FsclAc> fsclAcInfos = getObjectCUByCpdUnitId(uid, cpdUid);
		
		String msgContent = null;
		if (fsclAcInfos == null || fsclAcInfos.size() == 0) {
			msgContent = MessageFormat.format(balanceFsclAcExits, cpdUid);
		}else if (fsclAcInfos.size() > 1) {
			// 提示：组织{0}设置了过多的往来账户，请联系系统管理员配置
			msgContent = MessageFormat.format(balanceParamExitsMany, cpdUid);
		}else if (!"A".equals(fsclAcInfos.get(0).getStatus())) { // 活动性验证
			msgContent = MessageFormat.format(balanceParamExits, fsclAcInfos.get(0).getFsclAcId());
		}
		// 存在性验证
		if (msgContent != null) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(msgContent);
			logger.error(msgContent);
			return result;
		}
		//查询专项资金
		result.put("fsclAc", fsclAcInfos.get(0));
		return result;
	}
			
	/**
	 * 保存往来事务
	 * @param fsclAc
	 * @param isAdd 是否增加余额业务
	 * @return
	 */
	private String saveActran(FsclAc fsclAc,BalanceBean balanceBean,boolean isAdd){
		String tranNum = commonService.getPrimaryKey(fsclAc.getUnitId(), TRAN_NUM);
		logger.warn("------------------------------------BalanceServiceImpl ->saveActran:"+tranNum+" , docType :"+balanceBean.getDocType()+" , Balance:"+balanceBean.getBalance());
		AcTran acTran = new AcTran();
		acTran.setUnitId(fsclAc.getUnitId());
		acTran.setTranNum(Double.parseDouble(tranNum));
		acTran.setTranDate(new Date());
		String tranType = O2OBillConstant.TRADE_TYPE._01; // 01 收款
		String direct = "";
		if (BillType.GDN.equals(balanceBean.getDocType())) { // 出库
			tranType = O2OBillConstant.TRADE_TYPE.GDDL;
			direct = "DR";
		}else if (BillType.GRN.equals(balanceBean.getDocType())) { // 入库
			tranType = O2OBillConstant.TRADE_TYPE.GDRV;
			direct = "CR";
		}
		acTran.setTranType(tranType);
		acTran.setDocType(BillType.ADN.equals(balanceBean.getDocType())?BillType.AAD:balanceBean.getDocType());
		acTran.setDocNum(balanceBean.getDocNum());
		AcTranDtl acTranDtl = new AcTranDtl();
		acTranDtl.setUnitId(fsclAc.getUnitId());
		acTranDtl.setTranNum(Double.parseDouble(tranNum));
		acTranDtl.setLineNum(1d);
		acTranDtl.setDebitOrCredit(direct); // DR:借	CR:贷
		acTranDtl.setFsclAcId(fsclAc.getFsclAcId());
		acTranDtl.setCurrency(fsclAc.getCurrency());
		acTranDtl.setTranAmnt(balanceBean.getBalance());
		acTranDtl.setBalanceDate(new Date());
		Double diffAmount = isAdd?balanceBean.getBalance():-balanceBean.getBalance();
		acTranDtl.setBalance(fsclAc.getBalance()+diffAmount);
		balanceMapper.saveActran(acTran);
		balanceMapper.saveActranDtl(acTranDtl);
		return tranNum;
	}
	
	/**
	 * 保存冻结事务
	 * @param fsclAc
	 * @return
	 */
	private String saveFreezeTran(FsclAc fsclAc,BalanceBean balanceBean){
		FreezeTran freezeTran = new FreezeTran();
		freezeTran.setUnitId(fsclAc.getUnitId());
		String freezeTranNum = commonService.getPrimaryKey(fsclAc.getUnitId(), FREEZE_TRAN_NUM);
		freezeTran.setTranNum(Double.parseDouble(freezeTranNum));
		freezeTran.setFsclAcId(fsclAc.getFsclAcId());
		freezeTran.setCurrency(fsclAc.getCurrency());
		freezeTran.setTranAmnt(balanceBean.getBalance());
		logger.warn("------------------------------------BalanceServiceImpl ->freezeTranNum:"+freezeTranNum+" ,docType: "+balanceBean.getDocType()+" , Balance:"+balanceBean.getBalance());
		freezeTran.setBalance(fsclAc.getFrzBal()+balanceBean.getBalance()); // 冻结金额以后，往来账户冻结余额
		freezeTran.setDocType(BillType.ADN.equals(balanceBean.getDocType())?BillType.AAD:balanceBean.getDocType());
		freezeTran.setDocNum(balanceBean.getDocNum());
		freezeTran.setTranDate(new Date());
		balanceMapper.saveFreezeTran(freezeTran);
		return freezeTranNum;
	}
	
	
	
	
	/**
	 * 获取是否有冻结事务
	 * @param unitId 组织编码
	 * @param docType
	 * @param cpdUnitId 下级组织编码(代理商编码)
	 * @param docNum
	 * @return FsclAc
	 */
	public List<FreezeTran> selectFreezeTranByDocTypeNum(String unitId,String cpdUnitId , BillType docType , String docNum){
		logger.warn("获取是否有冻结事务，参数:docNum:{},cpdUnitId:{},docType:{},unitId:{}",new Object[]{docNum,cpdUnitId,docType,unitId} );
		if (SoaBillUtils.isBlank(docNum) || docType == null || SoaBillUtils.isBlank(unitId)) {
			logger.error("获取是否有冻结事务，参数不正确,docNum:{},cpdUnitId:{},docType:{},unitId:{}",new Object[]{docNum,cpdUnitId,docType,unitId} );
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("docNum", docNum);
        params.put("docType", docType);
        params.put("cpdUnitId", cpdUnitId);
        params.put("unitId", unitId);
		return balanceMapper.selectFreezeTranByDocTypeNum(params);
	}
	
	/**
	 * 获取账户信息
	 * @param uid
	 * @param cpdUid
	 * @return FsclAc
	 */
	public List<FsclAc> getObjectCUByCpdUnitId(String uid , String cpdUid){
		if (SoaBillUtils.isBlank(uid) || SoaBillUtils.isBlank(cpdUid)) {
			logger.error("获取账户信息，参数不正确,uid:{},cpdUid:{}",new Object[]{uid,cpdUid} );
			return null;
		}
		Map<String, String> params = new HashMap<String, String>();
        params.put("unitId", uid);
        params.put("cpdUnitId", cpdUid);
        List<FsclAc> c = balanceMapper.getFsclInfo(params);
		return c;
	}
	
	public MsgVo increaseFreezeByIdt(String venderId, String vendeeId,
			Double amount ,String docNum) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.ERROR,"","",null);
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("p_vender", venderId);
			result.put("p_vendee", vendeeId);
			result.put("p_docNum", docNum);
			result.put("p_amount", amount);
			result.put("result", "");
			logger.warn("dubbo:::increaseFreezeByIdt -> init param : "+JSON.toJSONString(result));
			balanceMapper.increaseFreezeByIdt(result);
			String errMsg = result.get("result") == null?"":result.get("result").toString();
			if ("".equals(errMsg)) {
				msg.setCode(O2OMsgConstant.MSG_CODE.SUCCESS);
			}else {
				msg.setMsg(errMsg);
			}
		} catch (Exception e) {
			msg.setMsg("DUBBO接口，现货冻结时出现异常："+e.getMessage());
			logger.error(msg.getMsg());
		}
		logger.warn("dubbo:::increaseFreezeByIdt -> result : "+JSON.toJSONString(msg));
		return msg;
	}
	
	public MsgVo reduceFreezeByIdt(String venderId, String vendeeId,
			Double amount ,String docNum) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.ERROR,"","",null);
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("p_vender", venderId);
			result.put("p_vendee", vendeeId);
			result.put("p_docNum", docNum);
			result.put("p_amount", amount);
			result.put("result", "");
			logger.warn("dubbo:::reduceFreezeByIdt -> init param : "+JSON.toJSONString(result));
			balanceMapper.reduceFreezeByIdt(result);
			String errMsg = result.get("result") == null?"":result.get("result").toString();
			if ("".equals(errMsg)) {
				msg.setCode(O2OMsgConstant.MSG_CODE.SUCCESS);
			}else {
				msg.setMsg(errMsg);
			}
		} catch (Exception e) {
			msg.setMsg("DUBBO接口，释放现货冻结时出现异常："+e.getMessage());
			logger.error(msg.getMsg());
		}
		logger.warn("dubbo:::reduceFreezeByIdt -> result : "+JSON.toJSONString(msg));
		return msg;
	}
	
	public MsgVo judgeBalance(String venderId, String vendeeId) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.ERROR,"","",null);
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("p_vender", venderId);
			result.put("p_vendee", vendeeId);
			result.put("amount", "");
			result.put("errMsg", "");
			logger.warn("dubbo:::judgeBalance -> init param : "+JSON.toJSONString(result));
			balanceMapper.judgeBalance(result);
			String errMsg = result.get("errMsg") == null?"":result.get("errMsg").toString();
			if ("".equals(errMsg)) {
				msg.setPrice(Double.parseDouble(result.get("amount").toString()));
				msg.setCode(O2OMsgConstant.MSG_CODE.SUCCESS);
			}else {
				msg.setMsg(errMsg);
			}
		} catch (Exception e) {
			msg.setMsg("DUBBO接口，判断余额是否足够 , 输出是可支配金额时出现异常："+e.getMessage());
			logger.error(msg.getMsg());
		}
		logger.warn("dubbo:::judgeBalance -> result : "+JSON.toJSONString(msg));
		return msg ;
	}
	
	/**
	 * 扣减专项资金
	 * @param balanceBean
	 * @param diff 扣减金额
	 */
	private boolean reduceCreditBalance(BalanceBean balanceBean, Double diff,Double useAmount) {
		// 查看是否启用专项资金
		Integer isCreditEnable = balanceMapper.isCreditEnable();
		if (isCreditEnable == null) {
			logger.error("没有启用专项资金，请设置系统参数。");
			return false;
		}
		String unitId = balanceBean.getUid();
		String vendeeId = balanceBean.getCpdUid();
		if (useAmount < 0) {
			logger.error("可支配金额不足，useAmount："+useAmount+" , unitId : "+unitId+" , vendeeId:"+vendeeId);
			return false;
		}
		//查询可用专项资金
		Double creditMoney = searchCreditMoney(unitId, vendeeId);
		logger.warn("扣减专项资金 , unitId:"+unitId+" vendeeId:"+vendeeId+" creditMoney:"+creditMoney+" diff:"+diff+" isCreditEnable:"+isCreditEnable+" useAmount:"+useAmount);
		if(creditMoney == null) {
			return false;
		}
		if(creditMoney < diff) {
			throw new RuntimeException(MessageFormat.format("可用余额不足,其中可使用信用额度{0}元",creditMoney));
		}
		List<CreditLimitUseVo> limitUseVos = searchCredit(balanceBean.getUid(),balanceBean.getCpdUid());
		List<CreditLimitUseVo> updateLimitUseVos = new ArrayList<CreditLimitUseVo>();
		Double temp = diff;
		//选择最快过期并金额较小的信用额度逐一扣减
		for(CreditLimitUseVo vo : limitUseVos){
			temp = vo.getLimitMoney() - temp;
			if (temp < 0) {
				temp = -temp;
				//该次扣减金额数量
				vo.setTranMoney(-vo.getLimitMoney());
				//余额
				vo.setLimitMoney(0d);
				updateLimitUseVos.add(vo);
			} else {
				//该次扣减金额数量
				vo.setTranMoney(-(vo.getLimitMoney()- temp));
				//余额
				vo.setLimitMoney(temp);
				updateLimitUseVos.add(vo);
				break;
			}
		}
		//更新金额
		balanceMapper.creditBatchUpdate(updateLimitUseVos);
		//保存信用额度事务
		saveCreditTran(balanceBean,-diff,updateLimitUseVos);
		//保存借款事务
		saveLoanTran(balanceBean,diff);
		return true;
	}
	
	/**
	 * 释放专项资金
	 * @param vo
	 */
	private void increaseCredit(CreditTranVo vo,BalanceBean balanceBean) {
		logger.warn("释放专项资金, UnitId:"+vo.getUnitId()+" , CreditTranId:"+vo.getCreditTranId()+" , LimitMoney:"+vo.getLimitMoney());
		//释放专项资金
		balanceMapper.updateCreditLimitUse(vo);
		//保存信用额度事务(根据冻结时的事务保存)
		List<CreditTranDtlVo> creditTranDtlVos = selectCreditTranDtl(vo.getUnitId(),vo.getCreditTranId());
		if(creditTranDtlVos == null || creditTranDtlVos.size() <= 0) {
			throw new RuntimeException("冻结的信用额度事务不存在,释放信用额度失败");
		}
		List<CreditLimitUseVo> creditLimitUseVos = new ArrayList<CreditLimitUseVo>();
		for(CreditTranDtlVo creditTranDtlVo : creditTranDtlVos) {
			CreditLimitUseVo limitUseVo = new CreditLimitUseVo();
			limitUseVo.setDocNum(creditTranDtlVo.getDocNum());
			limitUseVo.setTranMoney(-creditTranDtlVo.getTranMoney());
			limitUseVo.setDocSetUpNum(creditTranDtlVo.getDocSetUpNum());
			creditLimitUseVos.add(limitUseVo);
		}
		saveCreditTran(balanceBean,-vo.getLimitMoney(),creditLimitUseVos);
		//保存借款事务
		saveLoanTran(balanceBean,vo.getLimitMoney());
	}
	
	/**
	 * 保存借款事务
	 * @param balanceBean
	 * @param diff
	 */
	private void saveLoanTran(BalanceBean balanceBean, Double diff) {
		LoanTranVo loanTranVo = new LoanTranVo();
		String primaryKey = commonService.getPrimaryKey(balanceBean.getUid(), LOAN_TRAN_ID);
		loanTranVo.setLoanTranId(primaryKey);
		loanTranVo.setUnitId(balanceBean.getUid());
		loanTranVo.setVendeeId(balanceBean.getCpdUid());
		loanTranVo.setTranMoney(diff);
		loanTranVo.setTranType(TranType.LA);
		loanTranVo.setDocType(balanceBean.getDocType().equals(BillType.AAD)?BillType.ADN:balanceBean.getDocType());
		loanTranVo.setDocNum(balanceBean.getDocNum());
		balanceMapper.saveLoanTran(loanTranVo);
	}
	/**
	 * 保存信用额度事务(冻结时用)
	 * @param balanceBean
	 * @param diff
	 * @param updateLimitUseVos
	 */
	private void saveCreditTran(BalanceBean balanceBean, Double diff,
			List<CreditLimitUseVo> updateLimitUseVos) {
		if(updateLimitUseVos != null && updateLimitUseVos.size() > 0){
			CreditTranVo vo = new CreditTranVo();
			String primaryKey = commonService.getPrimaryKey(balanceBean.getUid(), CREDIT_TRAN_ID);
			vo.setCreditTranId(primaryKey);
			vo.setUnitId(balanceBean.getUid());
			vo.setVendeeId(balanceBean.getCpdUid());
			vo.setLimitType(LimitType.OO);
			vo.setLimitMoney(diff);
			vo.setDocType(balanceBean.getDocType().equals(BillType.AAD)?BillType.ADN:balanceBean.getDocType());
			vo.setDocNum(balanceBean.getDocNum());
			//保存信用额度事务
			balanceMapper.saveCreditTran(vo);
			
			List<CreditTranDtlVo> creditTranDtlVos = new ArrayList<CreditTranDtlVo>();
			for (CreditLimitUseVo limitUseVo : updateLimitUseVos){
				CreditTranDtlVo dtlVo = new CreditTranDtlVo();
				String dtlTranId = commonService.getPrimaryKey(vo.getUnitId(), CREDIT_TRAN_DTL_ID);
				dtlVo.setCreditTranDtlId(dtlTranId);
				dtlVo.setCreditTranId(vo.getCreditTranId());
				dtlVo.setUnitId(vo.getUnitId());
				dtlVo.setDocNum(limitUseVo.getDocNum());
				dtlVo.setTranMoney(limitUseVo.getTranMoney());
				dtlVo.setDocSetUpNum(limitUseVo.getDocSetUpNum());
				creditTranDtlVos.add(dtlVo);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("creditTran", creditTranDtlVos);
			//保存信用额度明细事务
			balanceMapper.saveCreditTranDtl(map);
		}
	}
	/**
	 * 查询可用专项资金金额数
	 * @param unitId
	 * @param cpdUnitId
	 * @return Double
	 */
	private Double searchCreditMoney(String unitId,String vendeeId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("unitId", unitId);
		map.put("vendeeId", vendeeId);
		return balanceMapper.searchCreditMoney(map);
 	}
	
	/**
	 * 查询可用专项资金
	 * @param unitId
	 * @param cpdUnitId
	 * @return Double
	 */
	private List<CreditLimitUseVo> searchCredit(String unitId,String vendeeId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("unitId", unitId);
		map.put("vendeeId", vendeeId);
		return balanceMapper.searchCredit(map);
 	}
	
	/**
	 * 获取是否有信用额度事务
	 * @param unitId 组织编码
	 * @param docType
	 * @param cpdUnitId 下级组织编码(代理商编码)
	 * @param docNum
	 * @return FsclAc
	 */
	private List<CreditTranVo> selectCreditTranByDocTypeNum(String unitId,String cpdUnitId , BillType docType , String docNum){
		logger.warn("获取是否有信用额度事务，参数:docNum:{},cpdUnitId:{},docType:{},unitId:{}",new Object[]{docNum,cpdUnitId,docType,unitId} );
		if (SoaBillUtils.isBlank(docNum) || docType == null || SoaBillUtils.isBlank(unitId)) {
			logger.error("获取是否有信用额度事务，参数不正确,docNum:{0},cpdUnitId:{1},docType:{2},unitId:{3}",new Object[]{docNum,cpdUnitId,docType,unitId} );
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("docNum", docNum);
        params.put("docType", docType.equals(BillType.AAD)?BillType.ADN:docType);
        params.put("vendeeId", cpdUnitId);
        params.put("unitId", unitId);
		return balanceMapper.selectCreditTranByDocTypeNum(params);
	}
	
	/**
	 * 根据组织编码和信用额度事务编码查询信用额度明细事务信息
	 * @param unitId
	 * @param creditTranId
	 * @return
	 * List<CreditTranDtlVo>
	 */
	private List<CreditTranDtlVo> selectCreditTranDtl(String unitId,String creditTranId) {
	
		if(SoaBillUtils.isBlank(unitId) || SoaBillUtils.isBlank(creditTranId)){
			logger.error("获取是否有信用额度明细事务，参数不正确,unitId:{0},creditTranId:{1}",new Object[]{unitId,creditTranId});
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("unitId", unitId);
		params.put("creditTranId", creditTranId);
		return balanceMapper.selectCreditTranDtl(params);
		
	}
	
}
