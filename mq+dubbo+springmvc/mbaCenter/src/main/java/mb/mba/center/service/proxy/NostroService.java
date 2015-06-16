package mb.mba.center.service.proxy;

import java.util.Date;

import javax.annotation.Resource;

import mb.erp.dr.soa.bean.BalanceBean;
import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.old.service.balance.BalanceService;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.springframework.util.Assert;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 往来账服务接口
 * 包含接口：增加余额、正数扣减余额、负数扣减余额、正数冻结金额、负数冻结金额、释放冻结
 * @author     余从玉
 * @version    1.0, 2015-6-4
 * @see         NostroService
 * @since       账务中心
 */
@Service
public class NostroService{

	@Resource
	private BalanceService balanceService;
	
	/**
	 * 增加余额	给往来账户增加余额(BALANCE)
	 * @param unitId			供货方组织编码
	 * @param cpdUnitId	购货方组织编码
	 * @param balance		金额
	 * @return
	 */
	public MsgVo increaseBalance(String unitId,String cpdUnitId ,Double balance){
		Assert.notNull(unitId, "unitId不能为空");
		Assert.notNull(cpdUnitId, "cpdUnitId不能为空");
		Assert.notNull(balance, "balance不能为空");
		BalanceBean balanceBean = new BalanceBean();
		balanceBean.setBalance(balance);
		balanceBean.setUid(unitId);
		balanceBean.setCpdUid(cpdUnitId);
//		MsgVo msgVo = balanceService.increaseBalance(balanceBean);
//		Message mbaMsg = new Message();
//		mbaMsg.setCode(msgVo.getCode());
//		mbaMsg.setMsg(msgVo.getMsg());
		return balanceService.increaseBalance(balanceBean);
	}
	
	/**
	 * 正数扣减余额	给往来账户扣减余额(BALANCE)，余额不允许出现负数
	 * @param unitId			供货方组织编码
	 * @param cpdUnitId	购货方组织编码
	 * @param balance		金额
	 * @param docDate		单据日期
	 * @param docType		单据类型
	 * @param docNum	单据编号
	 * @return
	 */
	public MsgVo reduceBalancePositive(String unitId,String cpdUnitId ,Double balance,Date docDate,String docType,String docNum){
		BalanceBean balanceBean = new BalanceBean();
		balanceBean.setUid(unitId); // 供货方（发货组织）
        balanceBean.setCpdUid(cpdUnitId); // 购货方(接收方)
        balanceBean.setBalance(balance); // 总金额
        balanceBean.setDocDate(docDate); // 单据日期，查询可支配金额用到
        balanceBean.setDocType(BillType.valueOf(docType));
        balanceBean.setDocNum(docNum);
//		MsgVo msgVo = balanceService.reduceBalancePositive(balanceBean);
//		Message mbaMsg = new Message();
//		mbaMsg.setCode(msgVo.getCode());
//		mbaMsg.setMsg(msgVo.getMsg());
		return balanceService.reduceBalancePositive(balanceBean);
	}
	
	/**
	 * 负数扣减余额	给往来账户扣减余额(BALANCE)，余额允许出现负数
	 * @param unitId			供货方组织编码
	 * @param cpdUnitId	购货方组织编码
	 * @param balance		金额
	 * @return
	 */
	public MsgVo reduceBalanceNegative(String unitId,String cpdUnitId ,Double balance){
		BalanceBean balanceBean = new BalanceBean();
		balanceBean.setUid(unitId); // 供货方（发货组织）
        balanceBean.setCpdUid(cpdUnitId); // 购货方(接收方)
        balanceBean.setBalance(balance); // 总金额
//		MsgVo msgVo = balanceService.reduceBalanceNegative(balanceBean);
//		Message mbaMsg = new Message();
//		mbaMsg.setCode(msgVo.getCode());
//		mbaMsg.setMsg(msgVo.getMsg());
		return balanceService.reduceBalanceNegative(balanceBean);
	}
	
	/**
	 * 正数冻结金额	在往来账户冻结金额(FRZ_VAL),可支配金额不足，不允许冻结
	 * @param unitId			供货方组织编码
	 * @param cpdUnitId	购货方组织编码
	 * @param balance		金额
	 * @return
	 */
	public MsgVo increaseFreezePositive(String unitId,String cpdUnitId ,Double balance){
		BalanceBean balanceBean = new BalanceBean();
		balanceBean.setUid(unitId); // 供货方（发货组织）
        balanceBean.setCpdUid(cpdUnitId); // 购货方(接收方)
        balanceBean.setBalance(balance); // 总金额
//		MsgVo msgVo = balanceService.increaseFreezePositive(balanceBean);
//		Message mbaMsg = new Message();
//		mbaMsg.setCode(msgVo.getCode());
//		mbaMsg.setMsg(msgVo.getMsg());
		return balanceService.increaseFreezePositive(balanceBean);
	}
	
	/**
	 * 负数冻结金额	在往来账户冻结金额(FRZ_VAL),可支配金额不足，允许冻结
	 * @param unitId			供货方组织编码
	 * @param cpdUnitId	购货方组织编码
	 * @param balance		金额
	 * @return
	 */
	public MsgVo increaseFreezeNegative(String unitId,String cpdUnitId ,Double balance){
		BalanceBean balanceBean = new BalanceBean();
		balanceBean.setUid(unitId); // 供货方（发货组织）
        balanceBean.setCpdUid(cpdUnitId); // 购货方(接收方)
        balanceBean.setBalance(balance); // 总金额
//		MsgVo msgVo = balanceService.increaseFreezeNegative(balanceBean);
//		Message mbaMsg = new Message();
//		mbaMsg.setCode(msgVo.getCode());
//		mbaMsg.setMsg(msgVo.getMsg());
		return balanceService.increaseFreezeNegative(balanceBean);
	}
	
	/**
	 * 释放冻结	在往来账户冻结金额(FRZ_VAL),释放冻结
	 * @param unitId			供货方组织编码
	 * @param cpdUnitId	购货方组织编码
	 * @param balance		金额
	 * @param docType		单据类型
	 * @param docNum	单据编号
	 * @return
	 */
	public MsgVo reduceFreeze(String unitId,String cpdUnitId ,Double balance,String docType,String docNum){
		BalanceBean balanceBean = new BalanceBean();
		balanceBean.setUid(unitId); // 供货方（发货组织）
        balanceBean.setCpdUid(cpdUnitId); // 购货方(接收方)
        balanceBean.setBalance(balance); // 总金额
        balanceBean.setDocType(BillType.valueOf(docType));
        balanceBean.setDocNum(docNum);
//		MsgVo msgVo = balanceService.reduceFreeze(balanceBean);
//		Message mbaMsg = new Message();
//		mbaMsg.setCode(msgVo.getCode());
//		mbaMsg.setMsg(msgVo.getMsg());
		return balanceService.reduceFreeze(balanceBean);
	}
	
}
