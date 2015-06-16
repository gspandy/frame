package mb.erp.dr.soa.service.impl.bill;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant.NEW_DOC_STATE;
import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;
import mb.erp.dr.soa.constant.O2OBillConstant.TBN_PROGRESS;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.dao.DrTbnMapper;
import mb.erp.dr.soa.dao.SfDgnMapper;
import mb.erp.dr.soa.dao.SfGdnMapper;
import mb.erp.dr.soa.dao.SfGrnMapper;
import mb.erp.dr.soa.dao.SfRvdMapper;
import mb.erp.dr.soa.dao.common.NewERPCommonMapper;
import mb.erp.dr.soa.drools.utils.KieSessionFactory;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnDtlVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.service.bill.NewBillService;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.service.price.NewSettlementPriceRateService;
import mb.erp.dr.soa.service.wareh.NewWarehService;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.utils.StatusChangeUtils;
import mb.erp.dr.soa.vo.DrTbnDtlVo;
import mb.erp.dr.soa.vo.DrTbnVo;
import mb.erp.dr.soa.vo.SfDgnVo;
import mb.erp.dr.soa.vo.SfGdnVo;
import mb.erp.dr.soa.vo.SfGrnVo;
import mb.erp.dr.soa.vo.SfRvdVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 新ERP调配单服务
 * 包含接口：保存，确认，审批、撤销
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         DrTbnService
 * @since       全流通改造
 */
@Service("sfTbnService")
public class DrTbnService  extends NewBillService<DrTbnVo>{
	private final Logger logger = LoggerFactory.getLogger(DrTbnService.class);
	
    @Resource
    private DrTbnMapper drTbnMapper;
    @Resource
    private SfDgnMapper sfDgnMapper;
    @Resource
    private SfGdnMapper sfGdnMapper;
    @Resource
    private SfRvdMapper sfRvdMapper;
    @Resource
    private SfGrnMapper sfGrnMapper;
    @Resource
    private NewWarehService newWarehService;
    @Resource
    private NewERPCommonService newERPCommonService;
    @Resource
    private NewERPCommonMapper newERPCommonMapper;
    @Resource
    private NewSettlementPriceRateService newSettlementPriceRateService;
    @Value("${save.param.null}")
    private String saveParamNull;
    @Value("closed.account")
    private String closedAccount;
    
    final static NewBillType NEW_BILL_TYPE = NewBillType.TBN;
    
    public MsgVo save(DrTbnVo tbn)  {
    	MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.SAVE);
    	
    	// 验证参数是否为空
    	String nullProperty = nullValidate(tbn);
    	if (nullProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(nullProperty);
			throw new RuntimeException(nullProperty);
		}
    	
      //获取主键ID
    	Long tbnId = newERPCommonService.getPrimaryIdNew("DR_TBN",1);
    	tbn.setId(tbnId);
    	tbn.setDocState(NEW_DOC_STATE.PG_NEW);
    	//获取订单tbnNum
    	String tbnNum = newERPCommonService.getPrimaryCode(NewBillType.TBN.toString(), 1);
    	tbn.setTbnNum(tbnNum);
    	// 根据规则，判断是否重新获取单价
    	tbn.setControlStatus(newERPCommonService.selectDirectoryCode("DRTBN", "SAVE"));
    	KieSession kieSession = KieSessionFactory.getKieSession("newTbn-rules-save");
		kieSession.insert(tbn);
		kieSession.insert(newSettlementPriceRateService);
		kieSession.insert(new SoaBillUtils());
		kieSession.fireAllRules();
		kieSession.dispose();
		
        Integer ssid = drTbnMapper.save(tbn);
        StringBuffer sb = new StringBuffer();
        sb.append("tbnNum:"+tbnNum+"  "+ssid);
        for (DrTbnDtlVo tbnDtlVo : tbn.getDtlVos()) {
        	tbnDtlVo.setId(newERPCommonService.getPrimaryIdNew("DR_TBN_DTL",1));
        	tbnDtlVo.setDrTbnId(tbnId);
        	sb.append(drTbnMapper.saveDtl(tbnDtlVo));
		}
        msg.setMsg(sb.toString());
        msg.setBillNum(tbnNum);
        msg.setNewBillId(tbnId);
        logger.warn("保存新ERP调配单,id:{},code:{}",tbnId,tbnNum);
        return msg;
    }

    /**
	 * 确认
	 * 修改新ERP调配单的进度为CN，必须是PG->CN
	 */
	public MsgVo confirm(DrTbnVo tbn){
		logger.debug("确认新ERP调配单,代理商:{},单据号:{}",tbn.getVenderId(),tbn.getTbnNum());
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.CONFIRM);
		// 查询新ERP调配单当前状态
		Integer processNow = drTbnMapper.selectProcess(tbn);
		if (!(NEW_DOC_STATE.PG_NEW == processNow)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP调配单状态更新为【已确认】时发生异常：订单编号："+tbn.getTbnNum()+" 当前订单状态："+StatusChangeUtils.getStatus(processNow,NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			tbn.setDocState(NEW_DOC_STATE.CN_NEW);
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(NEW_DOC_STATE.PG_NEW);
			tbn.setDocStateList(docStateList);
			drTbnMapper.updateProcess(tbn);
		}
		return msg;
	}
	
	/**
	 * 审批	
	 * 修改新ERP调配单的进度为AP,必须是CN->AP
	 */
	public MsgVo audit(DrTbnVo tbn){
		logger.debug("审批新ERP调配单,代理商:{},单据号:{}",tbn.getVenderId(),tbn.getTbnNum());
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.AUDIT);
		
		// 查询新ERP调配单当前状态
		Integer processNow = drTbnMapper.selectProcess(tbn);
		if (!(NEW_DOC_STATE.CN_NEW== processNow)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP调配单状态更新为【已审批】时发生异常：订单编号："+tbn.getTbnNum()+" 当前订单状态："+StatusChangeUtils.getStatus(processNow,NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			// 根据规则，判断是否锁定库存
			tbn.setDocState(NEW_DOC_STATE.AP_NEW);
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(NEW_DOC_STATE.CN_NEW);
			tbn.setDocStateList(docStateList);
			drTbnMapper.updateProcess(tbn);
		}
		return msg;
	}
	
	/**
	 * 撤销	
	 * 可撤销新ERP调配单，进度在录入中、已确认、已审核才可以撤销
	 */
	public MsgVo cancel(DrTbnVo tbn){
		logger.debug("撤销新ERP调配单,代理商:{},单据号:{}",tbn.getVenderId(),tbn.getTbnNum());
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.CANCEL);
		
		// 查询新ERP调配单当前状态
		Integer processNow = drTbnMapper.selectProcess(tbn);
		if (!((NEW_DOC_STATE.PG_NEW == processNow)
				|| (NEW_DOC_STATE.CN_NEW == processNow)
				|| (NEW_DOC_STATE.AP_NEW == processNow))) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP调配单状态更新为【已撤销】时发生异常：订单编号："+tbn.getTbnNum()+" 当前订单状态："+StatusChangeUtils.getStatus(processNow,NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			tbn.setDocState(NEW_DOC_STATE.CC_NEW);
			drTbnMapper.updateProcess(tbn);
		}
		return msg;
	}
	
	/**
	 * 验证需要保存的新ERP调配单
	 * @param tbnVo
	 * @return
	 */
	private String nullValidate(DrTbnVo tbnVo){
		String property = null;
		String result = null;
		if (null == tbnVo.getVendeeId()) {
			property = "购货方编码";
		}else if (null == tbnVo.getVenderId()) {
			property = "供货方编码";
		}else if (null == tbnVo.getDispWarehId()) {
			property = "发货仓库编码";
		}else if (null == tbnVo.getRcvWarehId()) {
			property = "接收仓库编码";
		}else if (null == tbnVo.getBrandId()) {
			property = "品牌编码";
		}else if (null == tbnVo.getDispTime()) {
			property = "要求发货时间";
		}else if (null == tbnVo.getDocDate()) {
			property = "单据日期";
		}else if (SoaBillUtils.isBlank(tbnVo.getReasonCode())) {
			property = "调配原因";
		}
		if (property != null) {
			result = MessageFormat.format(saveParamNull, "新ERP调配单", property);
		}
		return result;
		
	}
    
	@Override
	public MsgVo orderAG(Long tbnId,String code,Long sfDgnId,AdnVo vo)  {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.ORDERAG);
		DrTbnVo drTbnVo = new DrTbnVo();
		if (SoaBillUtils.isNotBlank(code)) {
			drTbnVo = drTbnMapper.selectTbnByCode(code);
		}else {
			drTbnVo = drTbnMapper.selectTbnById(tbnId);
		}
		//更新调配单进度
		if (null == drTbnVo || !(NEW_DOC_STATE.AP_NEW == drTbnVo.getDocState())){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP调配单状态更新为【配货中】时发生异常：订单编号："+drTbnVo.getTbnNum()+" 当前订单状态："+StatusChangeUtils.getStatus(drTbnVo.getDocState(),NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());  
		} else {
		    drTbnVo.setDocState(TBN_PROGRESS.AG_NEW);
		    List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(NEW_DOC_STATE.AP_NEW);
			drTbnVo.setDocStateList(docStateList);
		    drTbnMapper.updateProcess(drTbnVo);
		}
		
		//新增对分配明细中交货单号为空的记录的更新
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("drTbnId", drTbnVo.getId());
		params.put("dgnId", sfDgnId);
		//添加分配明细
		Integer num = drTbnMapper.insertDrTbnAllocByDgn(params);
        if (num <= 0){
            	throw new RuntimeException("新ERP调配单，原始单据配货中时出现异常");
        }else {
        	msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
        	msg.setMsg("调配单更新配货中出现异常");
		}
		return msg;
	}

	@Override
	public MsgVo  orderAD(Long tbnId,String code,Long sfDgnId,AdnVo vo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.ORDERAG);
		DrTbnVo drTbnVo = new DrTbnVo();
		if (SoaBillUtils.isNotBlank(code)) {
			drTbnVo = drTbnMapper.selectTbnByCode(code);
		}else {
			drTbnVo = drTbnMapper.selectTbnById(tbnId);
		}
		
		//更新调配单进度
		if (null == drTbnVo || !(TBN_PROGRESS.AG_NEW == drTbnVo.getDocState())){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP调配单状态更新为【已配货】时发生异常：订单编号："+drTbnVo.getTbnNum()+" 当前订单状态："+StatusChangeUtils.getStatus(drTbnVo.getDocState(),NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());  
		} else {
			drTbnVo.setDocState(TBN_PROGRESS.AD_NEW);
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(TBN_PROGRESS.AG_NEW);
			drTbnVo.setDocStateList(docStateList);
			drTbnMapper.updateProcess(drTbnVo);
		}
		tbnId = drTbnVo.getId();
		//更新至已配货
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("drTbnId", drTbnVo.getId());
		params.put("dgnId", sfDgnId);
        Integer isSuccess = drTbnMapper.updateDrTbnAllocToDc(params);
        if (isSuccess <=0) {
        	throw new RuntimeException("新ERP调配单，原始单据已配货时出现异常");
        }
        drTbnMapper.updateDrTbnQty(tbnId);
        drTbnMapper.updateDrTbnQtyVAL(tbnId);
		return msg;
	}
	
	@Override
	public MsgVo orderDG(Long tbnId,String code,Long sfGdrnId)  {
		DrTbnVo drTbnVo = new DrTbnVo();
		if (SoaBillUtils.isNotBlank(code)) {
			drTbnVo = drTbnMapper.selectTbnByCode(code);
		}else {
			drTbnVo = drTbnMapper.selectTbnById(tbnId);
		}
		logger.debug("新ERP调配单发货中,代理商:{},单据号:{}",drTbnVo.getVenderId(),drTbnVo.getTbnNum());
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.ORDERDG);
		//更新调配单进度
		if (null == drTbnVo || !(TBN_PROGRESS.AD_NEW == drTbnVo.getDocState())){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP调配单状态更新为【发货中】时发生异常：订单编号："+drTbnVo.getTbnNum()+" 当前订单状态："+StatusChangeUtils.getStatus(drTbnVo.getDocState(),NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());  
		} else {
			drTbnVo.setDocState(TBN_PROGRESS.DG_NEW);
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(TBN_PROGRESS.AD_NEW);
			drTbnVo.setDocStateList(docStateList);
			drTbnMapper.updateProcess(drTbnVo);
		}
		//出库单
        SfGdnVo sfGdn = sfGdnMapper.selectGdnById(sfGdrnId); 
        if (sfGdn == null) throw new RuntimeException("新ERP调配单，原始单据发货中时找不到出库单:"+sfGdrnId);
        if (sfGdn.getSrcDocType().equals(NewBillType.DGN.name())) throw new RuntimeException("新ERP调配单，原始单据发货中时出库单的原始单据类型不是交货单");
        if (SoaBillUtils.isBlank(sfGdn.getSrcDocCode())) throw new RuntimeException("新ERP调配单，原始单据发货中时出库单的原始单据号为空");
        //交货单
        String dgnCode = sfGdn.getSrcDocCode();
        SfDgnVo sfDgn = sfDgnMapper.selectDgnByCode(dgnCode);
        if (sfDgn == null) throw new RuntimeException("新ERP调配单，原始单据发货中时找不到交货单单:"+dgnCode);
        
        //更新至发货中
        Map<String, Object> params = new HashMap<String, Object>();
		params.put("drTbnId", tbnId);
		params.put("dgnCode", dgnCode);
        Integer isSuccess = drTbnMapper.updateDrTbnAllocToSp(params);
        if (isSuccess <= 0) throw new RuntimeException("新ERP调配单，原始单据发货中时，更新配货明细失败:"+tbnId);
        return msg;
	}
	
	@Override
	public MsgVo orderDD(Long tbnId,String code,Long sfGdrnId,GdnVo vo)  {
		DrTbnVo drTbnVo = new DrTbnVo();
		if (SoaBillUtils.isNotBlank(code)) {
			drTbnVo = drTbnMapper.selectTbnByCode(code);
		}else {
			drTbnVo = drTbnMapper.selectTbnById(tbnId);
		}
		logger.debug("新ERP调配单已发货,代理商:{},单据号:{}",drTbnVo.getVenderId(),drTbnVo.getTbnNum());
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.ORDERDD);
		//更新调配单进度
		if (null == drTbnVo || !(TBN_PROGRESS.DG_NEW == drTbnVo.getDocState())){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP调配单状态更新为【已发货】时发生异常：订单编号："+drTbnVo.getTbnNum()+" 当前订单状态："+StatusChangeUtils.getStatus(drTbnVo.getDocState(),NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());  
		} else {
			drTbnVo.setDocState(TBN_PROGRESS.DD_NEW);
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(TBN_PROGRESS.DG_NEW);
			drTbnVo.setDocStateList(docStateList);
			drTbnMapper.updateProcess(drTbnVo);
		}
		tbnId = drTbnVo.getId();
		
        SfGdnVo sfGdn = sfGdnMapper.selectGdnById(sfGdrnId); // vo.getGdnId 
        if (sfGdn == null) throw new RuntimeException("新ERP调配单，原始单据发货中时找不到出库单:"+sfGdrnId);
        if (sfGdn.getSrcDocType().equals(NewBillType.DGN.name())) throw new RuntimeException("新ERP调配单，原始单据发货中时出库单的原始单据类型不是交货单");
        if (SoaBillUtils.isBlank(sfGdn.getSrcDocCode())) throw new RuntimeException("新ERP调配单，原始单据发货中时出库单的原始单据号为空");

        //交货单
        String dgnCode = sfGdn.getSrcDocCode();
        SfDgnVo sfDgn = sfDgnMapper.selectDgnByCode(dgnCode);
        if (sfDgn == null) throw new RuntimeException("新ERP调配单，原始单据发货中时找不到交货单单:"+dgnCode);

        //更新至已发货
        Map<String, Object> params = new HashMap<String, Object>();
		params.put("drTbnId", tbnId);
		params.put("dgnCode", dgnCode);
		params.put("sfGdnId", sfGdrnId);
		Integer isSuccess = drTbnMapper.updateDrTbnAllocToSc(params);
		if (isSuccess <= 0) throw new RuntimeException("新ERP调配单，原始单据已发货时，更新配货明细失败:"+tbnId);

        //更新数量
        drTbnMapper.updateDrTbnQty(tbnId);
        drTbnMapper.updateDrTbnQtyVAL(tbnId);

        //增加在途库存 TODO 2015.2.5 注掉了，业务还没设计。另：一个事务内，对同一个SF_WAREH_PROD插入多次相同的记录，会有问题
//        MsgVo result = newWarehService.increaseQtyInTransitByGdn(sfGdrnId);
//        if (O2OMsgConstant.MSG_CODE.ERROR.equals(result.getCode())) {
//			throw new RuntimeException("");
//		}
        //修改锁定库存流程---增加判断 获取交货单信息 查看IS_AUTO_DIST字段是否走统一配货流程
        // TODO 新老ERP目前都不需要处理统一配货的场景
//        if ("1".equals(sfDgn.getIsAutoDist())){
//            Boolean rltuniform = new Uniform_Stock().GdnCancelByUniform(gdnId, sfGdn.ORIG_DOC_TYPE, sfGdn.ORIG_DOC_CODE);
//        }
		return msg;
	}
	
	@Override
	public MsgVo orderRG(Long tbnId,String code,Long sfRvdId,GrnVo vo)  {
		DrTbnVo drTbnVo = new DrTbnVo();
		if (SoaBillUtils.isNotBlank(code)) {
			drTbnVo = drTbnMapper.selectTbnByCode(code);
		}else {
			drTbnVo = drTbnMapper.selectTbnById(tbnId);
		}
		logger.debug("新ERP调配单收货中,代理商:{},单据号:{}",drTbnVo.getVenderId(),drTbnVo.getTbnNum());
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.ORDERRG);

		//更新调配单进度
		if (null == drTbnVo || !(TBN_PROGRESS.DD_NEW == drTbnVo.getDocState())){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP调配单状态更新为【收货中】时发生异常：订单编号："+drTbnVo.getTbnNum()+" 当前订单状态："+StatusChangeUtils.getStatus(drTbnVo.getDocState(),NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());  
		} else {
			drTbnVo.setDocState(TBN_PROGRESS.RG_NEW);
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(TBN_PROGRESS.DD_NEW);
			drTbnVo.setDocStateList(docStateList);
			drTbnMapper.updateProcess(drTbnVo);
		}
		tbnId = drTbnVo.getId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("drTbnId", tbnId);
		if (null != sfRvdId) {
			//到货通知单
			SfRvdVo sfRvd = sfRvdMapper.selectSfRvdById(sfRvdId);
			if (sfRvd == null) throw new RuntimeException("新ERP调配单，原始单据收货中时找不到到货通知单:"+sfRvdId);
			//获取出库单CODE
			String gdnCode = null;
			if (NewBillType.GDN.name().equals(sfRvd.getSrcDocType())) {
				gdnCode = sfRvd.getSrcDocNum();
			}else {
				gdnCode = sfGdnMapper.getCodeByRvd(sfRvd.getId());
			}
			if (SoaBillUtils.isBlank(gdnCode)) throw new RuntimeException("新ERP调配单，原始单据收货中时找不到出库单:"+sfRvd.getId());
			//更新至收货中
			params.put("gdnCode", gdnCode);
			Integer isSuccess = drTbnMapper.updateDrTbnAllocToRp(params);
			if (isSuccess <= 0) throw new RuntimeException("新ERP调配单，原始单据收货中时，更新配货明细失败:"+tbnId);
		}else {
			params.put("gdnCode", vo.getSfGdnCode());
			Integer isSuccess = drTbnMapper.updateDrTbnAllocToRp(params);
			if (isSuccess <= 0) throw new RuntimeException("新ERP调配单，原始单据收货中时，更新配货明细失败:"+tbnId);
		}
        return msg;
	}
	
	@Override
	public MsgVo orderRD(Long tbnId,String code,Long sfGdrnId,GrnVo vo)  {
		DrTbnVo drTbnVo = new DrTbnVo();
		if (SoaBillUtils.isNotBlank(code)) {
			drTbnVo = drTbnMapper.selectTbnByCode(code);
		}else {
			drTbnVo = drTbnMapper.selectTbnById(tbnId);
		}
		logger.debug("新ERP调配单已收货,代理商:{},单据号:{}",drTbnVo.getVenderId(),drTbnVo.getTbnNum());
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.ORDERRG);
		
		//更新调配单进度
		if (null == drTbnVo || !(TBN_PROGRESS.RG_NEW == drTbnVo.getDocState())){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP调配单状态更新为【已收货】时发生异常：订单编号："+drTbnVo.getTbnNum()+" 当前订单状态："+StatusChangeUtils.getStatus(drTbnVo.getDocState(),NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());  
		} else {
			drTbnVo.setDocState(TBN_PROGRESS.RD_NEW);
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(TBN_PROGRESS.RG_NEW);
			drTbnVo.setDocStateList(docStateList);
			drTbnMapper.updateProcess(drTbnVo);
		}
		tbnId = drTbnVo.getId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("drTbnId", tbnId);
		if (null != sfGdrnId) {
			//入库单
			SfGrnVo sfGrn = sfGrnMapper.selectGrnById(sfGdrnId); // vo.getGrnId 
			if (sfGrn == null) throw new RuntimeException("新ERP调配单，原始单据已收货时找不到入库单:"+sfGdrnId);
			if (sfGrn.getSrcDocType().equals(NewBillType.RVD.name())) throw new RuntimeException("新ERP调配单，原始单据已收货时出库单的原始单据类型不是到货通知单:"+sfGdrnId);
			//到货通知单
			String rvdCode = sfGrn.getSrcDocNum();
			SfRvdVo sfRvd = sfRvdMapper.selectSfRvdByCode(rvdCode);
			if (sfRvd == null) throw new RuntimeException("新ERP调配单，原始单据已收货时找不到到货通知单:"+rvdCode);
			
			//获取出库单CODE
			String gdnCode = null;
			if (NewBillType.GDN.name().equals(sfRvd.getSrcDocType())) {
				gdnCode = sfRvd.getSrcDocNum();
			}else {
				gdnCode = sfGdnMapper.getCodeByRvd(sfRvd.getId());
			}
			if (SoaBillUtils.isBlank(gdnCode)) throw new RuntimeException("新ERP调配单，原始单据已收货时找不到出库单:"+sfRvd.getId());
			
			//更新至已收货
			params.put("gdnCode", gdnCode);
			params.put("sfGrnId", sfGdrnId);
			Integer isSuccess = drTbnMapper.updateDrTbnAllocToRc(params);
			if (isSuccess <=0) throw new RuntimeException("新ERP调配单，原始单据更新至已收货时异常:"+tbnId);
		}else {
			for (GrnDtlVo grnDtlVo : vo.getGrnDtlVos()) {
				params.put("grn_code", vo.getGrnNum());
				params.put("rcv_qty", grnDtlVo.getQuantity());
				Long prod_id = newERPCommonMapper.getProdIdByProdNum(grnDtlVo.getProdId());
				params.put("prod_id", prod_id);
				params.put("gdnCode", vo.getSfGdnCode());
				Integer isSuccess = drTbnMapper.updateDrTbnAllocByGrnToRc(params);
				if (isSuccess <=0) throw new RuntimeException("新ERP调配单，原始单据更新至已收货时异常:"+tbnId);
			}
		}

        //更新收发数量金额
        drTbnMapper.updateDrTbnQty(tbnId);
        drTbnMapper.updateDrTbnQtyVAL(tbnId);
        
        //减少在途库存
//        msg = newWarehService.reduceQtyInTransitByGrn(sfGdrnId);
		return msg;
	}
	
	/**
	 * 得到配货单里各状态的数量，以便于更新调配单的状态
	 * 部分配货，部分收货等场景需要根据配货明细的状态来进行判断
	 * @param drTbnId
	 * @return
	 */
//	private Map<String, Integer> getTbnAllocCount(Integer drTbnId){
//		Map<String, Integer> result = new HashMap<String, Integer>();
//		List<DrTbnAllocDtlVo> list = drTbnMapper.searchDrTbnAllocDtl(drTbnId);
//		if (list !=  null && list.size() > 0) {
//			result.put("total", list.size()); // 配货单总数
//			for (DrTbnAllocDtlVo alloc : list) {
//				String state = alloc.getDocState().toString();
//				if (result.containsKey(state)) {
//					result.put(state, result.get(state)+1);
//				}else {
//					result.put(state, 1);
//				}
//			}
//		}
//		return result;
//	}

}
