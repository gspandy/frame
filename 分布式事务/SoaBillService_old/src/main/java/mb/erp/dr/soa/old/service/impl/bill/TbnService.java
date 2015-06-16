package mb.erp.dr.soa.old.service.impl.bill;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.drools.utils.KieSessionFactory;
import mb.erp.dr.soa.old.dao.TbnMapper;
import mb.erp.dr.soa.old.dao.WarehMapper;
import mb.erp.dr.soa.old.service.bill.BillService;
import mb.erp.dr.soa.old.service.bill.CommonService;
import mb.erp.dr.soa.old.service.price.SettlementPriceRateService;
import mb.erp.dr.soa.old.service.wareh.WarehService;
import mb.erp.dr.soa.old.vo.TbnDtlVo;
import mb.erp.dr.soa.old.vo.TbnVo;
import mb.erp.dr.soa.utils.DateUtil;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 调配单服务
 * 包含接口：保存，确认，审批、撤销
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         TbnService
 * @since       全流通改造
 */
@Service("tbnService")
public class TbnService  extends BillService<TbnVo>{
	private final Logger logger = LoggerFactory.getLogger(TbnService.class);
	
    @Resource
    private TbnMapper tbnMapper;
    @Resource
    private WarehMapper warehMapper;
    @Resource
    private WarehService warehService;
    @Resource
    private CommonService commonService;
    @Resource
    private SettlementPriceRateService settlementPriceRateService;
    @Value("${save.param.null}")
    private String saveParamNull;
    @Value("closed.account")
    private String closedAccount;
    
    final static BillType BILL_TYPE = BillType.TBN;
    
    public MsgVo save(TbnVo tbn)  {
    	MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.SAVE,BILL_TYPE);
    	
    	// 验证参数是否为空
    	String nullProperty = nullValidate(tbn);
    	if (nullProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(nullProperty);
			throw new RuntimeException(nullProperty);
		}
    	
    	// 得到tbnNum的值
        String tbnNum = commonService.getPrimaryKey(tbn.getVenderId(),"TBN_NUM");
        tbn.setTbnNum(tbnNum);
    	
    	// 根据规则，判断是否重新获取单价
    	tbn.setControlStatus(commonService.selectDirectoryCode("TBN","SAVE"));
    	KieSession kieSession = KieSessionFactory.getKieSession("tbn-rules-save");
		kieSession.insert(tbn);
		kieSession.insert(new SoaBillUtils());
		kieSession.insert(settlementPriceRateService);
		kieSession.fireAllRules();
		kieSession.dispose();
		
        Integer ssid = tbnMapper.save(tbn);
        StringBuffer sb = new StringBuffer();
        sb.append("tbnNum:"+tbnNum+"  "+ssid);
        for (TbnDtlVo tbnDtlVo : tbn.getTbnDtlVos()) {
        	tbnDtlVo.setTbnNum(tbnNum);
        	sb.append(tbnMapper.saveDtl(tbnDtlVo));
		}
        msg.setMsg(sb.toString());
        msg.setBillNum(tbnNum);
        logger.warn("保存调配单,购货方:{},单据号:{}",tbn.getVendeeId(),tbnNum);
        return msg;
    }

    /**
	 * 确认
	 * 修改调配单的进度为CN，必须是PG->CN
	 */
	public MsgVo confirm(TbnVo tbn){
		logger.debug("确认调配单,代理商:{},单据号:{}",tbn.getVenderId(),tbn.getTbnNum());
		MsgVo msg = validatePrimaryParam(tbn, O2OMsgConstant.BIZTYPE.CONFIRM);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		// 查询调配单当前状态
		String processNow = tbnMapper.selectProcess(tbn);
		if (!O2OBillConstant.PROGRESS.PG.equals(processNow)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("确认调配单异常，代理商："+tbn.getVenderId()+"，订单编号："+tbn.getTbnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			tbn.setProgress(O2OBillConstant.PROGRESS.CN); // 更改状态为CN
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.PG);
			tbn.setProgressList(progressList);
			tbnMapper.updateProcess(tbn);
		}
		return msg;
	}
	
	/**
	 * 审批	
	 * 修改调配单的进度为AP,必须是CN->AP
	 */
	public MsgVo audit(TbnVo tbn){
		logger.debug("审批调配单,代理商:{},单据号:{}",tbn.getVenderId(),tbn.getTbnNum());
		MsgVo msg = validatePrimaryParam(tbn, O2OMsgConstant.BIZTYPE.AUDIT);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		
		// 查询调配单当前状态
		String processNow = tbnMapper.selectProcess(tbn);
		if (!O2OBillConstant.PROGRESS.CN.equals(processNow)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("审批调配单异常，代理商："+tbn.getVenderId()+"，订单编号："+tbn.getTbnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			// 根据规则，判断是否锁定库存
			String controlCode = commonService.selectDirectoryCode("TBN","AUDIT");//"1";
			logger.warn("统一配货是否已经锁定过已分配库存 : "+tbn.getHadLockWareh());
			if(tbn.getDispWarehId().equals(tbn.getLastFactDispWarehId()) 
					&& "1".equals(tbn.getHadLockWareh())){
				// 如果是实际发货仓，且已经锁定过已分配库存，则不锁定已分配库存(实际发货仓在物流模块已经被锁定过已分配库存)
	            logger.warn( tbn.getDispWarehId()+"是实际发货仓，且已经锁定过已分配库存 , 不锁定已分配库存" );
	            controlCode = "0";
			}
			tbn.setControlStatus(controlCode);
			KieSession kieSession = KieSessionFactory.getKieSession("tbn-rules-audit");
			kieSession.insert(warehService);
			kieSession.insert(tbn);
			kieSession.fireAllRules();
			kieSession.dispose();
			tbn.setProgress(O2OBillConstant.PROGRESS.AP); // 更改状态为AP
			tbn.setInvsgTime(DateUtil.now());//审核时间
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.CN);
			tbn.setProgressList(progressList);
			tbnMapper.updateTbnByPrimaryKey(tbn);
//			tbnMapper.updateProcess(tbn);
		}
		return msg;
	}
	
	/**
	 * 撤销	
	 * 可撤销调配单，进度在录入中、已确认、已审核才可以撤销
	 */
	public MsgVo cancel(TbnVo tbn){
		logger.debug("撤销调配单,代理商:{},单据号:{}",tbn.getVenderId(),tbn.getTbnNum());
		MsgVo msg = validatePrimaryParam(tbn, O2OMsgConstant.BIZTYPE.AUDIT);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		
		// 查询调配单当前状态
		String processNow = tbnMapper.selectProcess(tbn);
		if (!(O2OBillConstant.PROGRESS.PG.equals(processNow)
				|| O2OBillConstant.PROGRESS.CN.equals(processNow)
				|| O2OBillConstant.PROGRESS.AP.equals(processNow))) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("撤销调配单异常，代理商："+tbn.getVenderId()+"，订单编号："+tbn.getTbnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			tbn.setCancelled(O2OBillConstant.TORF.TRUE); // 撤销
			tbnMapper.cancel(tbn);
		}
		return msg;
	}
	
	/**
	 * 验证需要保存的调配单
	 * @param tbnVo
	 * @return
	 */
	private String nullValidate(TbnVo tbnVo){
		String property = null;
		String result = null;
		if (SoaBillUtils.isBlank( tbnVo.getVendeeId())) {
			property = "购货方编码";
		}else if (SoaBillUtils.isBlank( tbnVo.getVenderId())) {
			property = "供货方编码";
		}else if (SoaBillUtils.isBlank( tbnVo.getDispWarehId())) {
			property = "发货仓库编码";
		}else if (SoaBillUtils.isBlank( tbnVo.getRcvWarehId())) {
			property = "接收仓库编码";
		}else if (SoaBillUtils.isBlank( tbnVo.getBrandId())) {
			property = "品牌编码";
		}else if (SoaBillUtils.isBlank(tbnVo.getReasonCode())) {
			property = "调配原因";
		}
		if (property != null) {
			result = MessageFormat.format(saveParamNull, "调配单", property);
		}
		return result;
		
	}
    
	/**
	 * 验证参数主键是否为空
	 * @param vo
	 * @return
	 */
	private MsgVo validatePrimaryParam(TbnVo vo,String bizType){
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",bizType,BILL_TYPE);
		if (SoaBillUtils.isBlank(vo.getVenderId()) 
				|| SoaBillUtils.isBlank(vo.getTbnNum())) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("验证调配单时参数异常，组织编码："+vo.getVenderId()+"，订单编号："+vo.getTbnNum()+"，进度："+vo.getProgress());
			logger.error(msg.getMsg());
		}
		return msg;
	}
	
	public MsgVo orderDG(TbnVo vo)  {
		logger.debug("调配单发货中,代理商:{},单据号:{}",vo.getVenderId(),vo.getTbnNum());
		MsgVo msg = validatePrimaryParam(vo, O2OMsgConstant.BIZTYPE.ORDERDG);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		// 查询调配单当前状态
		String processNow = tbnMapper.selectProcess(vo);
		if (!(O2OBillConstant.PROGRESS.AP.equals(processNow)
				|| O2OBillConstant.PROGRESS.FI.equals(processNow))) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("调配单状态更新为【发货中】异常，代理商："+vo.getVenderId()+"，订单编号："+vo.getTbnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			vo.setProgress(O2OBillConstant.PROGRESS.DG); // 更改状态为DG
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.AP);
			progressList.add(O2OBillConstant.PROGRESS.FI);
			vo.setProgressList(progressList);
			tbnMapper.updateOriginalTbnF(vo);
			tbnMapper.updateOriginalTbnDtlF(vo);
		}
		return msg;
	}

	public MsgVo orderDD(TbnVo vo)  {
		logger.debug("调配单已发货,代理商:{},单据号:{}",vo.getVenderId(),vo.getTbnNum());
		MsgVo msg = validatePrimaryParam(vo, O2OMsgConstant.BIZTYPE.ORDERDG);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		// 查询调配单当前状态
		String processNow = tbnMapper.selectProcess(vo);
		if (!O2OBillConstant.PROGRESS.DG.equals(processNow)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("调配单状态更新为【已发货】时异常，代理商："+vo.getVenderId()+"，订单编号："+vo.getTbnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			vo.setProgress(O2OBillConstant.PROGRESS.DD); // 更改状态为DD
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.DG);
			vo.setProgressList(progressList);
			tbnMapper.updateOriginalTbnYfh(vo);
			tbnMapper.updateOriginalTbnDtlYfh(vo);
		}
		
		// 查询调配单是否增加过已分配库存
//		OrderSearchBean orderSearchBean = new OrderSearchBean();
//		orderSearchBean.setDocNum(vo.getTbnNum());
//		orderSearchBean.setDocType(BILL_TYPE);
//		orderSearchBean.setWarehId(vo.getDispWarehId());
//		WarehCommitedTranVo commitTranVo = warehMapper.searchCommitTranInfo(orderSearchBean);
//		if(commitTranVo == null){
//			msg.setMsg("调配单已发货时，调配单没有增加过已分配库存，代理商："+vo.getVenderId()+"，订单编号："+vo.getTbnNum()+"，当前状态为"+processNow);
//			logger.info(msg.getMsg());
//			return msg;
//		}else{
//			// 释放已分配库存
//			WarehBean warehBean = new WarehBean();
//			warehBean.setUnitId(vo.getVenderId());
//			warehBean.setTbnNum(vo.getTbnNum());
//			warehBean.setWarehId(vo.getDispWarehId()); // 发货仓库编码
//			warehService.reduceCommitQtyByTBN(warehBean);
//		}
		return msg;
	}

	public MsgVo orderRG(TbnVo vo)  {
		logger.debug("调配单收货中,代理商:{},单据号:{}",vo.getVenderId(),vo.getTbnNum());
		MsgVo msg = validatePrimaryParam(vo, O2OMsgConstant.BIZTYPE.ORDERRG);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		// 查询调配单当前状态
		String processNow = tbnMapper.selectProcess(vo);
		if (!O2OBillConstant.PROGRESS.DD.equals(processNow)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("调配单状态更新为【收货中】时异常，代理商："+vo.getVenderId()+"，订单编号："+vo.getTbnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			vo.setProgress(O2OBillConstant.PROGRESS.RG); // 更改状态为RG
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.DD);
			vo.setProgressList(progressList);
			
			tbnMapper.updateOriginalTbnS(vo);
			tbnMapper.updateOriginalTbnDtlS(vo);
		}
		return msg;
	}

	public MsgVo orderRD(TbnVo vo)  {
		logger.debug("调配单已收货,代理商:{},单据号:{}",vo.getVenderId(),vo.getTbnNum());
		MsgVo msg = validatePrimaryParam(vo, O2OMsgConstant.BIZTYPE.ORDERRD);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		// 查询调配单当前状态
		String processNow = tbnMapper.selectProcess(vo);
		if (!O2OBillConstant.PROGRESS.RG.equals(processNow)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("调配单状态更新为【已收货】时异常，代理商："+vo.getVenderId()+"，订单编号："+vo.getTbnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			vo.setProgress(O2OBillConstant.PROGRESS.RD); // 更改状态为RD
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.RG);
			vo.setProgressList(progressList);
			
			tbnMapper.updateOriginalTbnYsh(vo);
			tbnMapper.updateOriginalTbnDtlYsh(vo);
		}
		return msg;
	}
	@Override
	public MsgVo orderGDDL(TbnVo vo)  {
		logger.debug("调配单出库冲单,代理商:{},单据号:{}",vo.getVenderId(),vo.getTbnNum());
		MsgVo msg = validatePrimaryParam(vo, O2OMsgConstant.BIZTYPE.ORDERGDDL);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		// 查询调配单当前状态
		String processNow = tbnMapper.selectProcess(vo);
		if (!O2OBillConstant.PROGRESS.DD.equals(processNow)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("调配单出库冲单异常，代理商："+vo.getVenderId()+"，订单编号："+vo.getTbnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			vo.setProgress(O2OBillConstant.PROGRESS.AP); // 更改状态为AP
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.DD);
			vo.setProgressList(progressList);
			
			tbnMapper.updateOriginalTbnF(vo);
			tbnMapper.updateOriginalTbnDtlF(vo);
		}
		return msg;
	}
	@Override
	public MsgVo orderGDRV(TbnVo vo)  {
		logger.debug("调配单入库冲单,代理商:{},单据号:{}",vo.getVenderId(),vo.getTbnNum());
		MsgVo msg = validatePrimaryParam(vo, O2OMsgConstant.BIZTYPE.ORDERGDRV);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		// 查询调配单当前状态
		String processNow = tbnMapper.selectProcess(vo);
		if (!O2OBillConstant.PROGRESS.RD.equals(processNow)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("调配单入库冲单异常，代理商："+vo.getVenderId()+"，订单编号："+vo.getTbnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			vo.setProgress(O2OBillConstant.PROGRESS.DD); // 更改状态为DD
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.RD);
			vo.setProgressList(progressList);
			
			tbnMapper.updateOriginalTbnS(vo);
			tbnMapper.updateOriginalTbnDtlS(vo);
		}
		return msg;
	}
	@Override
	public MsgVo orderGDDLCancel(TbnVo vo)  {
		logger.debug("调配单出库撤单,代理商:{},单据号:{}",vo.getVenderId(),vo.getTbnNum());
		MsgVo msg = validatePrimaryParam(vo, O2OMsgConstant.BIZTYPE.ORDERGDDLCANCEL);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		// 查询调配单当前状态
		String processNow = tbnMapper.selectProcess(vo);
		if (!O2OBillConstant.PROGRESS.DG.equals(processNow)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("调配单出库撤单异常，代理商："+vo.getVenderId()+"，订单编号："+vo.getTbnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			vo.setProgress(O2OBillConstant.PROGRESS.AP); // 更改状态为AP
			
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.DG);
			vo.setProgressList(progressList);
			
			tbnMapper.updateOriginalTbnF(vo);
			tbnMapper.updateOriginalTbnDtlF(vo);
		}
		return msg;
	}

	@Override
	public MsgVo orderGDRVCancel(TbnVo vo)  {
		logger.debug("调配单入库撤单,代理商:{},单据号:{}",vo.getVenderId(),vo.getTbnNum());
		MsgVo msg = validatePrimaryParam(vo, O2OMsgConstant.BIZTYPE.ORDERGDRVCANCEL);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		// 查询调配单当前状态
		String processNow = tbnMapper.selectProcess(vo);
		if (!O2OBillConstant.PROGRESS.RG.equals(processNow)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("调配单入库撤单异常，代理商："+vo.getVenderId()+"，订单编号："+vo.getTbnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			vo.setProgress(O2OBillConstant.PROGRESS.DD); // 更改状态为DD
			
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.RG);
			vo.setProgressList(progressList);
			
			tbnMapper.updateOriginalTbnS(vo);
			tbnMapper.updateOriginalTbnDtlS(vo);
		}
		return msg;
	}

}
