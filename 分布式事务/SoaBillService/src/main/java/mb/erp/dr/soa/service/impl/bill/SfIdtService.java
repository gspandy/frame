package mb.erp.dr.soa.service.impl.bill;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.APPROVED;
import mb.erp.dr.soa.constant.O2OBillConstant.DGN_PROGRESS;
import mb.erp.dr.soa.constant.O2OBillConstant.IDT_ALLOC_DTL_PROGRESS;
import mb.erp.dr.soa.constant.O2OBillConstant.IDT_PROGRESS;
import mb.erp.dr.soa.constant.O2OBillConstant.NEW_DOC_STATE;
import mb.erp.dr.soa.constant.O2OBillConstant.NEW_PROGRESS;
import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.dao.SfDgnMapper;
import mb.erp.dr.soa.dao.SfDocFlowMapper;
import mb.erp.dr.soa.dao.SfGdnMapper;
import mb.erp.dr.soa.dao.SfGrnMapper;
import mb.erp.dr.soa.dao.SfIdtAllocDtlMapper;
import mb.erp.dr.soa.dao.SfIdtMapper;
import mb.erp.dr.soa.dao.SfRvdMapper;
import mb.erp.dr.soa.dao.common.NewERPCommonMapper;
import mb.erp.dr.soa.drools.utils.KieSessionFactory;
import mb.erp.dr.soa.old.vo.AdnDtlVo;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.GdnDtlVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.service.balance.NewBalanceService;
import mb.erp.dr.soa.service.bill.NewBillService;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.service.price.NewSettlementPriceRateService;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.utils.StatusChangeUtils;
import mb.erp.dr.soa.vo.SfDgnVo;
import mb.erp.dr.soa.vo.SfGdnVo;
import mb.erp.dr.soa.vo.SfGrnVo;
import mb.erp.dr.soa.vo.SfIdtAllocDtlVo;
import mb.erp.dr.soa.vo.SfIdtDtlVo;
import mb.erp.dr.soa.vo.SfIdtVo;
import mb.erp.dr.soa.vo.SfRvdVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 新ERP现货订单生成
 * 包括生成现货订单，确认现货订单，审批现货订单和撤销现货订单相关操作
 * @author     郭明帅
 * @version    1.0, 2014-11-19
 * @see         SfIdtService
 * @since       全流通改造
 */
@Service("sfIdtService")
public class SfIdtService extends NewBillService<SfIdtVo>{
	private final Logger logger = LoggerFactory.getLogger(SfIdtService.class);
    @Resource
    private NewERPCommonService newERPCommonService;
    @Resource
    private NewSettlementPriceRateService newSettlementPriceRateService;
    @Resource
    private NewBalanceService newBalanceService;
    @Resource
    private SfIdtMapper sfIdtMapper;
    @Resource
    private SfGdnMapper sfGdnMapper;
    @Resource
    private SfGrnMapper sfGrnMapper;
    @Resource
    private SfDgnMapper sfDgnMapper;
    @Resource
    private SfRvdMapper sfRvdMapper;
    @Resource
    private SfIdtAllocDtlMapper sfIdtAllocDtlMapper;
    @Resource
    private NewERPCommonMapper newERPCommonMapper;
    @Resource
    private SfDocFlowMapper sfDocFlowMapper;
    @Value("${save.param.defValue}")
    private String saveParamDefValue; //提示：订单参数{0}默认值错误，请核实
    @Value("${save.param.null}")
    private String saveParamNull; //提示：生成{0}所需的{1}不能为空，请核实
	final static NewBillType NEW_BILL_TYPE = NewBillType.IDT;
	/**
	 * 生成现货订单
	 * @param args
	 */
	public MsgVo save(SfIdtVo sfIdtVo) {
		MsgVo msg = new MsgVo();
		//默认值验证
		String defValueProperty = defValueValidate(sfIdtVo);
    	if (defValueProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(defValueProperty);
			throw new RuntimeException(defValueProperty);
		}
    	
    	//默认值赋值
    	sfIdtVo = defAssign(sfIdtVo);
    	
		// 验证参数是否为空
    	String nullProperty = nullValidate(sfIdtVo);
    	if (nullProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(nullProperty);
			throw new RuntimeException(nullProperty);
		}
    	
    	//获取主键ID
    	Long id = newERPCommonService.getPrimaryIdNew("SF_IDT",1);
    	sfIdtVo.setId(id);
    	//获取订单CODE
    	String code = newERPCommonService.getPrimaryCode(NewBillType.IDT.name(), 1);
    	sfIdtVo.setCode(code);
    	msg.setNewBillId(id);
    	msg.setCode(code);
    	sfIdtVo.setDocState(NEW_DOC_STATE.PG_NEW);
    	sfIdtVo.setProgress(O2OBillConstant.PROGRESS.PG);
    	//TODO   规则引擎
    	//非线上订单执行业务规则校验 测试
    	if (!sfIdtVo.getIsOos().equals(O2OBillConstant.IS_OOS.M)) {
    		sfIdtVo.setControlStatus(newERPCommonService.selectDirectoryCode("SFIDT","SAVE"));
    		KieSession kSession = KieSessionFactory.getKieSession("sfIdt-rules-save");
    		kSession.insert(sfIdtVo);
    		kSession.insert(new SoaBillUtils());
    		kSession.insert(newSettlementPriceRateService);
    		kSession.fireAllRules();
    		kSession.dispose();
		}
    	sfIdtMapper.saveSfIdt(sfIdtVo);
    	for(SfIdtDtlVo sfIdtDtl : sfIdtVo.getSfIdtDtlVos()){
    		Long dtlId = newERPCommonService.getPrimaryIdNew("SF_IDT_DTL",1);
    		sfIdtDtl.setId(dtlId);
    		sfIdtDtl.setSfIdtId(id);
    		sfIdtMapper.saveSfIdtDtl(sfIdtDtl);
    	}
    	logger.warn("保存新ERP现货订单,id:{},code:{}",id,code);
		return msg;
	}
	
	/**
	 * 确认现货订单
	 * 修改现货订单的进度为CN，必须是PG 0 ->CN 1
	 * @param arg
	 */
	public MsgVo confirm(SfIdtVo sfIdtVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.CONFIRM);
		Integer	docState = sfIdtMapper.selectDocState(sfIdtVo);
		if(O2OBillConstant.NEW_DOC_STATE.PG_NEW != docState){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP现货订单状态更新为【已确认】时发生异常：订单编号："+sfIdtVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(docState,NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			sfIdtVo.setDocState(NEW_DOC_STATE.CN_NEW);
			sfIdtVo.setProgress(O2OBillConstant.PROGRESS.CN);
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(O2OBillConstant.NEW_DOC_STATE.PG_NEW);
			sfIdtVo.setDocStateList(docStateList);
			sfIdtMapper.updateSfIdt(sfIdtVo);
		}
		return msg;
	}
	
	/**
	 * 审批现货订单
	 * 修改现货订单的进度为AP,必须是CN 1->AP 2
	 * @param args
	 */
	public MsgVo audit(SfIdtVo sfIdtVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.AUDIT);
		int docState = sfIdtMapper.selectDocState(sfIdtVo);
		if(O2OBillConstant.NEW_DOC_STATE.CN_NEW != docState){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP现货订单状态更新为【已审批】时发生异常：订单编号："+sfIdtVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(docState,NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
	    	//业务规则校验 测试
			//TODO
			sfIdtVo.setControlStatus(newERPCommonService.selectDirectoryCode("SFIDT","AUDIT"));
		    KieSession kSession = KieSessionFactory.getKieSession("sfIdt-rules-audit");
		    kSession.insert(sfIdtVo);
		    kSession.insert(newSettlementPriceRateService);
//		    kSession.insert(newBalanceService);
		    kSession.insert(new SoaBillUtils());
	        kSession.fireAllRules();
	        kSession.dispose();
			sfIdtVo.setDocState(NEW_DOC_STATE.AP_NEW);
			sfIdtVo.setProgress(O2OBillConstant.PROGRESS.AP);
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(O2OBillConstant.NEW_DOC_STATE.CN_NEW);
			sfIdtVo.setDocStateList(docStateList);
			sfIdtMapper.updateSfIdt(sfIdtVo);
			//重算单价 更新明细
			if (sfIdtVo.getControlStatus().matches("^1{1}.*")){
				for(SfIdtDtlVo idtDtlVo : sfIdtVo.getSfIdtDtlVos()){
					sfIdtMapper.updateSfIdtDtlPriceAndDiscrate(idtDtlVo);
				}
			}
		}
		return msg;
	}
	
	/**
	 * 撤销现货订单
	 * 进度在录入中 PG、已确认 CN、已审核 AP才可以撤销
	 * @param args
	 */
	public MsgVo cancel(SfIdtVo sfIdtVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.CANCEL);
		int docState = sfIdtMapper.selectDocState(sfIdtVo);
		if(O2OBillConstant.NEW_DOC_STATE.PG_NEW != docState
				|| O2OBillConstant.NEW_DOC_STATE.CN_NEW != docState
				|| O2OBillConstant.NEW_DOC_STATE.AP_NEW != docState){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP现货订单状态更新为【已撤销】时发生异常：订单编号："+sfIdtVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(docState,NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			sfIdtVo.setDocState(NEW_DOC_STATE.CC_NEW);
			sfIdtVo.setProgress(O2OBillConstant.PROGRESS.AG);
			
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(O2OBillConstant.NEW_DOC_STATE.PG_NEW);
			docStateList.add(O2OBillConstant.NEW_DOC_STATE.CN_NEW);
			docStateList.add(O2OBillConstant.NEW_DOC_STATE.AP_NEW);
			sfIdtVo.setDocStateList(docStateList);
			
			sfIdtMapper.updateSfIdt(sfIdtVo);
		}
		return msg;
	}
	
	/**
	 * 验证需要保存的现货订单默认值是否匹配
	 * @param tbnVo
	 * @return
	 */
	private String defValueValidate(SfIdtVo sfIdtVo){
		String result = null;
		if (O2OBillConstant.NEW_DOC_STATE.PG_NEW != sfIdtVo.getDocState()) {
			result = MessageFormat.format(saveParamDefValue, "DOC_STATE(单据初始状态要为0)");
		}
		return result;
		
	}
	
	/**
	 * 现货订单参数赋默认值
	 * @param tbnVo
	 * @return
	 */
	private SfIdtVo defAssign(SfIdtVo sfIdtVo){
		sfIdtVo.setLowIdtFlag(StringUtils.isEmpty(sfIdtVo.getLowIdtFlag()) ? "F" : sfIdtVo.getLowIdtFlag());
		sfIdtVo.setIsDispReq(StringUtils.isEmpty(sfIdtVo.getIsDispReq()) ? "F" : sfIdtVo.getIsDispReq());
		sfIdtVo.setIsDftDisp(StringUtils.isEmpty(sfIdtVo.getIsDftDisp()) ? "F" : sfIdtVo.getIsDftDisp());
		sfIdtVo.setDataSource(SoaBillUtils.isBlank(sfIdtVo.getDataSource()) ? APPROVED.NEWERP : sfIdtVo.getDataSource());
		return sfIdtVo;
	}
	
	/**
	 * 验证需要保存的现货订单的必要参数是否为空
	 * @param tbnVo
	 * @return
	 */
	private String nullValidate(SfIdtVo sfIdtVo){
		String property = null;
		String result = null;
		if (null == sfIdtVo.getBfOrgVendeeId()) {
			property = "购货方编码";
		}
		if (null ==  sfIdtVo.getBfOrgVenderId()) {
			property = "供货方编码";
		}
		if (null == sfIdtVo.getBfOrgRcvWarehId()) {
			property = "接收仓库编码";
		}
		if (null == sfIdtVo.getBfOrgShopId()) {
			property = "订货门店";
		}
		if (null == sfIdtVo.getBrandId()) {
			property = "品牌编码";
		}
		if (SoaBillUtils.isBlank(sfIdtVo.getIdtType())) {
			property = "订单类型";
		}
		if (property != null) {
			result = MessageFormat.format(saveParamNull, "新ERP现货订单", property);
		}
		return result;
	}
	
	//现货订单进度控制
	public MsgVo controlDocState(long srcDocId){
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,"");
		//查询订单分配明细
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sfIdtId", srcDocId);
		List<SfIdtAllocDtlVo> sfIdtAllocDtlVos = sfIdtAllocDtlMapper.selectIdtAllocDtl(map);
		if (sfIdtAllocDtlVos.size() > 0) {
			int totalCount = sfIdtAllocDtlVos.size();
			Map<Integer,Integer> docStatMap = new HashMap<Integer,Integer>();
			for(SfIdtAllocDtlVo sfIdtAllocDtl : sfIdtAllocDtlVos){
				//配货中
				if(IDT_ALLOC_DTL_PROGRESS.DP == sfIdtAllocDtl.getDocState()){
					docStatMap.put(IDT_ALLOC_DTL_PROGRESS.DP, ((Integer) map.get(IDT_ALLOC_DTL_PROGRESS.DP)).intValue() + 1);
				}else {
					docStatMap.put(IDT_ALLOC_DTL_PROGRESS.DP, 1);
				}
				
				//已配货
				if(IDT_ALLOC_DTL_PROGRESS.DC == sfIdtAllocDtl.getDocState()){
					docStatMap.put(IDT_ALLOC_DTL_PROGRESS.DC, ((Integer) map.get(IDT_ALLOC_DTL_PROGRESS.DC)).intValue() + 1);
				}else {
					docStatMap.put(IDT_ALLOC_DTL_PROGRESS.DC, 1);
				}
				
				//发货中
				if(IDT_ALLOC_DTL_PROGRESS.SP == sfIdtAllocDtl.getDocState()){
					docStatMap.put(IDT_ALLOC_DTL_PROGRESS.SP, ((Integer) map.get(IDT_ALLOC_DTL_PROGRESS.SP)).intValue() + 1);
				}else {
					docStatMap.put(IDT_ALLOC_DTL_PROGRESS.SP, 1);
				}
				
				//已发货
				if(IDT_ALLOC_DTL_PROGRESS.SC == sfIdtAllocDtl.getDocState()){
					docStatMap.put(IDT_ALLOC_DTL_PROGRESS.SC, ((Integer) map.get(IDT_ALLOC_DTL_PROGRESS.SC)).intValue() + 1);
				}else {
					docStatMap.put(IDT_ALLOC_DTL_PROGRESS.SC, 1);
				}
				
				//收货中
				if(IDT_ALLOC_DTL_PROGRESS.RP == sfIdtAllocDtl.getDocState()){
					docStatMap.put(IDT_ALLOC_DTL_PROGRESS.RP, ((Integer) map.get(IDT_ALLOC_DTL_PROGRESS.RP)).intValue() + 1);
				}else {
					docStatMap.put(IDT_ALLOC_DTL_PROGRESS.RP, 1);
				}
				
				//已收货
				if(IDT_ALLOC_DTL_PROGRESS.RC == sfIdtAllocDtl.getDocState()){
					docStatMap.put(IDT_ALLOC_DTL_PROGRESS.RC, ((Integer) map.get(IDT_ALLOC_DTL_PROGRESS.RC)).intValue() + 1);
				}else {
					docStatMap.put(IDT_ALLOC_DTL_PROGRESS.RC, 1);
				}
			}
            //部分配货中
            if (totalCount > validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.DP)) && validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.DP)) > 0){
               
            }
            
            //配货中
            if (totalCount == validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.DP))){
               
            }
            
            //部分已配货
            if (totalCount > validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.DC)) && validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.DC)) > 0){
               
            }
            
            //已配货
            if (totalCount > validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.DC))){
               
            }
            
            //部分发货中
            if (totalCount > validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.SP)) && validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.SP)) > 0){
               
            }
            
            //发货中
            if (totalCount > validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.SP))){
               
            }
            
            //部分已发货
            if (totalCount > validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.SC)) && validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.SC)) > 0){
               
            }
            
            //已发货
            if (totalCount > validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.SC))){
               
            }
            
            //部分收货中
            if (totalCount > validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.RP)) && validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.RP)) > 0){
               
            }
            
            //收货中
            if (totalCount > validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.RP))){
               
            }
            
            //部分已收货
            if (totalCount > validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.RC)) && validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.RC)) > 0){
               
            }
            
            //已收货
            if (totalCount > validateValue(docStatMap.get(IDT_ALLOC_DTL_PROGRESS.RC))){
               
            }
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP现货订单进度控制异常，编号："+srcDocId);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	public int validateValue(Integer i){
		return i == null ? 0 : i;
	}
	
	/**
	 * 新ERP现货订单配货中 （状态更新：AP 已审核 --> AG 配货中)
	 */
	@Override
	public MsgVo orderAG(Long sfIdtId,String code,Long dgnId,AdnVo vo)  {
		//查询新ERP现货订单
		SfIdtVo sfIdtVo = new SfIdtVo();
		if (SoaBillUtils.isNotBlank(code)) {
			sfIdtVo = sfIdtMapper.selectSfIdtByCode(code);
		}else {
			sfIdtVo = sfIdtMapper.selectSfIdtById(sfIdtId);
		}
		MsgVo msg = validatePrimaryParam(sfIdtVo, O2OMsgConstant.BIZTYPE.ORDERAG);
		if (NEW_DOC_STATE.AP_NEW == sfIdtVo.getDocState() || IDT_PROGRESS.FI_NEW == sfIdtVo.getDocState()) {
			Map<String,Object> map = new HashMap<String,Object>();
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(IDT_PROGRESS.FI_NEW);
			docStateList.add(NEW_DOC_STATE.AP_NEW);
			map.put("sf_idt_id", sfIdtVo.getId());
			map.put("doc_state", IDT_PROGRESS.AG_NEW);
			map.put("actQty", 0.0); //发货数量
			map.put("rcvQty", 0.0); //收货数量
			map.put("allocQty", 0.0); //配货数量
			map.put("docStateList", docStateList);//单据当前状态
			
			//更新现货单进度 、配货数量、配货金额
			sfIdtMapper.updateIdtDocState(map);
			//更新现货单明细的配货数量
			sfIdtMapper.updateIdtDtlQty(map);
			//根据交货单跟新订单分配明细
			if (null != vo) {
				map.put("code", vo.getWarehId());
				Long wareh_id = newERPCommonMapper.getUnitIdByUnitCode(map);
				if (null == wareh_id) {
					throw new RuntimeException("新ERP现货订单【配货中】时，发现新ERP没有设置"+vo.getWarehId()+"仓库，或者此仓库没有启用。");
				}
				map.put("wareh_id", wareh_id);
				map.put("sf_dgn_code", "");
				map.put("data_source_alloc", IDT_ALLOC_DTL_PROGRESS.DP);
				map.put("data_source", sfIdtVo.getDataSource());
				sfIdtAllocDtlMapper.saveIdtAllocDtl(map);
			}else {
				map.put("id", dgnId);
//				int i = sfIdtAllocDtlMapper.updateAllocDtlByDgn(map);
//				if (i <= 0) {
					//根据交货单添加分配明细
					int j = sfIdtAllocDtlMapper.saveIdtAllocDtlByDgn(map);
					logger.warn("-----生成分配明细------："+j+"参数：交货单id："+dgnId+" sfIdtId："+sfIdtVo.getId());
//				}
			}
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP现货订单状态更新为【配货中】时发生异常：订单编号："+sfIdtVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(sfIdtVo.getDocState(),NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 新ERP现货订单已配货 （状态更新：AG 配货中 --> AD 已配货)
	 */
	@Override
	public MsgVo orderAD(Long sfIdtId,String code,Long dgnId,AdnVo vo)  {
		//查询新ERP现货订单
		SfIdtVo sfIdtVo = new SfIdtVo();
		if (SoaBillUtils.isNotBlank(code)) {
			sfIdtVo = sfIdtMapper.selectSfIdtByCode(code);
		}else {
			sfIdtVo = sfIdtMapper.selectSfIdtById(sfIdtId);
		}
		MsgVo msg = validatePrimaryParam(sfIdtVo, O2OMsgConstant.BIZTYPE.ORDERAD);
		if (IDT_PROGRESS.AG_NEW == sfIdtVo.getDocState() || IDT_PROGRESS.FI_NEW == sfIdtVo.getDocState()) {
			Map<String,Object> map = new HashMap<String,Object>();
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(IDT_PROGRESS.AG_NEW);
			docStateList.add(IDT_PROGRESS.FI_NEW);
			map.put("sf_idt_id", sfIdtVo.getId());
			map.put("doc_state", IDT_PROGRESS.AD_NEW);
			map.put("docStateList", docStateList);
			//更新现货单进度
			sfIdtMapper.updateIdtDocState(map);
			if (null != vo) {
				//根据计划配货单更新订单分配明细进度为已配货、配货数量、配货日期
				for(AdnDtlVo adnDtlVo : vo.getAdnDtlVos()){
					map.put("alloc_qty", adnDtlVo.getAdmQty());
					Long prod_id = newERPCommonMapper.getProdIdByProdNum(adnDtlVo.getProdId());
					map.put("prod_id", prod_id);
					sfIdtAllocDtlMapper.updateAllocDtlByAdnToAD(map);
				}
			}else {
				//根据交货单更新订单分配明细进度为已配货、配货数量、配货日期
				map.put("id", dgnId);
				sfIdtAllocDtlMapper.updateAllocDtlByDgnToAD(map);
			}
			//更新现货单明细数量
			sfIdtMapper.updateSfIdtDtlQty(map);
			//更新现货单总单数量和金额
			sfIdtMapper.updateSfIdtQtyVal(map);
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP现货订单状态更新为【已配货】时发生异常：订单编号："+sfIdtVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(sfIdtVo.getDocState(),NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 现货订单发货中 （状态更新：AD 已配货 --> DG 发货中)
	 */
	@Override
	public MsgVo orderDG(Long sfIdtId,String code,Long sfGdrnId)  {
		//查询新ERP现货订单
		SfIdtVo sfIdtVo = new SfIdtVo();
		if (SoaBillUtils.isNotBlank(code)) {
			sfIdtVo = sfIdtMapper.selectSfIdtByCode(code);
		}else {
			sfIdtVo = sfIdtMapper.selectSfIdtById(sfIdtId);
		}
		MsgVo msg = validatePrimaryParam(sfIdtVo, O2OMsgConstant.BIZTYPE.ORDERDG);
		if (IDT_PROGRESS.AD_NEW == sfIdtVo.getDocState() || IDT_PROGRESS.FI_NEW == sfIdtVo.getDocState()) {
			Map<String,Object> map = new HashMap<String,Object>();
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(IDT_PROGRESS.AD_NEW);
			docStateList.add(IDT_PROGRESS.FI_NEW);
			map.put("sf_idt_id", sfIdtVo.getId());
			map.put("doc_state", IDT_PROGRESS.DG_NEW);
			map.put("actQty", 0.0); //发货数量
			map.put("docStateList", docStateList);
			//更新现货单进度
			sfIdtMapper.updateIdtDocState(map);
			//更新现货单明细的发货数量
			sfIdtMapper.updateIdtDtlQty(map);
			//更新现货单分配明细为发货中
			if (null != sfGdrnId ) {
				SfGdnVo sfGdnVo = sfGdnMapper.selectGdnById(sfGdrnId);
				map.put("sf_dgn_code", sfGdnVo.getSrcDocCode());
				sfIdtAllocDtlMapper.updateAllocDtlToDG(map);
			}else {
				map.put("sf_dgn_code", "");
				sfIdtAllocDtlMapper.updateAllocDtlToDG(map);
			}
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP现货订单状态更新为【发货中】时发生异常：订单编号："+sfIdtVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(sfIdtVo.getDocState(),NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 现货订单已发货 （状态更新：DG 发货中 --> DD 已发货)
	 */
	@Override
	public MsgVo orderDD(Long sfIdtId,String code,Long sfGdrnId,GdnVo vo)  {
		//查询新ERP现货订单
		SfIdtVo sfIdtVo = new SfIdtVo();
		if (SoaBillUtils.isNotBlank(code)) {
			sfIdtVo = sfIdtMapper.selectSfIdtByCode(code);
		}else {
			sfIdtVo = sfIdtMapper.selectSfIdtById(sfIdtId);
		}
		MsgVo msg = validatePrimaryParam(sfIdtVo, O2OMsgConstant.BIZTYPE.ORDERDD);
		if (IDT_PROGRESS.DG_NEW == sfIdtVo.getDocState() || IDT_PROGRESS.FI_NEW == sfIdtVo.getDocState()) {
			Map<String,Object> map = new HashMap<String,Object>();
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(IDT_PROGRESS.DG_NEW);
			docStateList.add(IDT_PROGRESS.FI_NEW);
			map.put("sf_idt_id", sfIdtVo.getId());
			map.put("doc_state", IDT_PROGRESS.DD_NEW);
			map.put("docStateList",docStateList);
			//更新现货单进度
			sfIdtMapper.updateIdtDocState(map);
			//更新交货单进度
			SfDgnVo sfDgnVo = new SfDgnVo();
			sfDgnVo.setId(sfGdrnId);
			sfDgnVo.setDocState(DGN_PROGRESS.HAVE_OUT_WAREH);
			sfDgnVo.setProgress(NEW_PROGRESS.SC);
			sfDgnMapper.updateSfDgn(sfDgnVo);
			//更新现货单分配明细为已发货
			if (null != vo) {
				for(GdnDtlVo gdnDtlVo : vo.getGdnDtlVos()){
					map.put("act_qty", gdnDtlVo.getQuantity());
					Long prod_id = newERPCommonMapper.getProdIdByProdNum(gdnDtlVo.getProdId());
					map.put("prod_id", prod_id);
					map.put("gdn_code", vo.getGdnNum());
					map.put("adn_code", vo.getSrcDocNum());
					sfIdtAllocDtlMapper.updateAllocDtlByGdnToDD(map);
				}
			}else {
				SfGdnVo sfGdnVo = sfGdnMapper.selectGdnById(sfGdrnId);
				map.put("sf_dgn_code", sfGdnVo.getSrcDocCode());
				map.put("sf_gdn_id", sfGdnVo.getId());
				sfIdtAllocDtlMapper.updateAllocDtlToDD(map);
			}
			//更新现货单明细数量
			sfIdtMapper.updateSfIdtDtlQty(map);
			//更新现货单总单数量和金额
			sfIdtMapper.updateSfIdtQtyVal(map);
			
			//判断交货单是否增加过已分配库存
//			WarehCommitedTranVo warehCommitedTranVo = warehMapper.searchCommitTranInfo(orderSearchBean);
//			if (warehCommitedTranVo != null) {
//				//调用释放已分配库存接口
//				WarehBean warehBean = new WarehBean();
//				warehBean.setUnitId(adn.getVenderId());
//				warehBean.setAdnNum(adn.getAdnNum());
//				warehBean.setWarehId(adn.getWarehId()); //仓库编码
//				msg = warehService.reduceCommitQtyByADN(warehBean);
//			}
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP现货订单状态更新为【已发货】时发生异常：订单编号："+sfIdtVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(sfIdtVo.getDocState(),NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 现货订单收货中 （状态更新：DD 已发货 --> RG 收货中)
	 */
	@Override
	public MsgVo orderRG(Long sfIdtId,String code,Long sfGdrnId,GrnVo vo)  {
		//查询新ERP现货订单
		SfIdtVo sfIdtVo = new SfIdtVo();
		if (SoaBillUtils.isNotBlank(code)) {
			sfIdtVo = sfIdtMapper.selectSfIdtByCode(code);
		}else {
			sfIdtVo = sfIdtMapper.selectSfIdtById(sfIdtId);
		}
		MsgVo msg = validatePrimaryParam(sfIdtVo, O2OMsgConstant.BIZTYPE.ORDERRG);
		if (IDT_PROGRESS.DD_NEW == sfIdtVo.getDocState() || IDT_PROGRESS.FI_NEW == sfIdtVo.getDocState()) {
			Map<String,Object> map = new HashMap<String,Object>();
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(IDT_PROGRESS.DD_NEW);
			docStateList.add(IDT_PROGRESS.FI_NEW);
			map.put("docStateList", docStateList);
			map.put("sf_idt_id", sfIdtVo.getId());
			map.put("doc_state", IDT_PROGRESS.RG_NEW);
			map.put("rcvQty", 0.0); //收货数量
			//更新现货单进度
			sfIdtMapper.updateIdtDocState(map);
			//更新现货单明细的收货数量
			sfIdtMapper.updateIdtDtlQty(map);
			//查询到货通知单
//			SfGrnVo sfGrnVo = sfGrnMapper.selectGrnById(sfGdrnId);
//			map.put("code", sfGrnVo.getSrcDocNum());
			SfRvdVo sfRvdVo = sfRvdMapper.selectSfRvdById(sfGdrnId);
			//根据到货通知单获取出库单CODE （根据发货仓库，源头单据相同及已发货）
			String sf_gdn_code = "";
			if(NewBillType.GDN.name().equals(sfRvdVo.getSrcDocType())){
				sf_gdn_code = sfRvdVo.getSrcDocNum();
			}else {
				map.put("rvd_id", sfRvdVo.getId());
				sf_gdn_code = sfRvdMapper.getGdnCodeByRvd(map);
			}
			//更新现货单分配明细为收货中
			map.put("sf_gdn_code", sf_gdn_code);
			sfIdtAllocDtlMapper.updateAllocDtlToRG(map);
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP现货订单状态更新为【收货中】时发生异常：订单编号："+sfIdtVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(sfIdtVo.getDocState(),NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 现货订单已收货 （状态更新：RG 收货中 --> RD 已收货)
	 */
	@Override
	public MsgVo orderRD(Long sfIdtId,String code,Long sfGdrnId,GrnVo vo)  {
		//查询新ERP现货订单
		SfIdtVo sfIdtVo = new SfIdtVo();
		if (SoaBillUtils.isNotBlank(code)) {
			sfIdtVo = sfIdtMapper.selectSfIdtByCode(code);
		}else {
			sfIdtVo = sfIdtMapper.selectSfIdtById(sfIdtId);
		}
		MsgVo msg = validatePrimaryParam(sfIdtVo, O2OMsgConstant.BIZTYPE.ORDERRD);
		if (IDT_PROGRESS.RG_NEW == sfIdtVo.getDocState() || IDT_PROGRESS.FI_NEW == sfIdtVo.getDocState()) {
			Map<String,Object> map = new HashMap<String,Object>();
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(IDT_PROGRESS.RG_NEW);
			docStateList.add(IDT_PROGRESS.FI_NEW);
			map.put("docStateList", docStateList);
			map.put("sf_idt_id", sfIdtVo.getId());
			map.put("doc_state", IDT_PROGRESS.RD_NEW);
			//更新现货单进度
			sfIdtMapper.updateIdtDocState(map);
			//查询到货通知单
			SfGrnVo sfGrnVo = sfGrnMapper.selectGrnById(sfGdrnId);
			map.put("code", sfGrnVo.getSrcDocNum());
			SfRvdVo sfRvdVo = sfRvdMapper.selectSfRvd(map);
			//更新现货单分配明细为已收货
			map.put("sf_grn_id", sfGrnVo.getId());
			map.put("sf_gdn_code", sfRvdVo.getSrcDocNum());
			sfIdtAllocDtlMapper.updateAllocDtlToRD(map);
			//更新现货单明细数量
			sfIdtMapper.updateSfIdtDtlQty(map);
			//更新现货单总单数量和金额
			sfIdtMapper.updateSfIdtQtyVal(map);
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP现货订单状态更新为【已收货】时发生异常：订单编号："+sfIdtVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(sfIdtVo.getDocState(),NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 新ERP现货订单已配货 
	 */
//	public MsgVo orderADnew(SfIdtVo sfIdtVo,Long dispWarehId,String oldAdnCode)  {
//		MsgVo msg = validatePrimaryParam(sfIdtVo, O2OMsgConstant.BIZTYPE.ORDERAD);
//		try {
//			//更新计划分配单(交货单)号，修改现货订单分配明细进度为已配货
//			Map<Object,Object> map = new HashMap<Object,Object>();
//			map.put("wareh_id", dispWarehId);
//			map.put("sf_dgn_code", oldAdnCode);
//			map.put("doc_state", O2OBillConstant.IDT_ALLOC_DTL_PROGRESS.DC);
//			map.put("data_source", APPROVED.OLDERP);
//			map.put("sf_idt_id", sfIdtVo.getId());
//			int i = sfIdtAllocDtlMapper.saveIdtAllocDtl(map);
//			logger.info("新增现货订单分配明细已配货 "+i+" 条");
//			//更新现货订单明细信息的配货数量
//			int j = sfIdtMapper.updateSfIdtDtlAllocQty(map);
//			logger.info("对现货订单明细信息的配货数量修改 "+j+" 条");
//			//更新现货订单进度 - 已配货
//			map.put("doc_state", NEW_DOC_STATE.AD_NEW);
//			int k = sfIdtMapper.updateIdtDocState(map);
//			logger.info("对现货订单进度进行修改-已配货 "+k+" 条");
//			//保存单据流
//			saveSfDocFlow(sfIdtVo,DOC_FLOW_STATE_PROGRESS.DP,NewBillType.DGN.toString(),oldAdnCode);
//			saveSfDocFlow(sfIdtVo,DOC_FLOW_STATE_PROGRESS.DC,NewBillType.DGN.toString(),oldAdnCode);
//		} catch (Exception e) {
//			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
//			msg.setMsg("新ERP现货订单已配货异常，购货方："+sfIdtVo.getBfOrgVendeeId()+"，编号："+sfIdtVo.getIdtType());
//			logger.error(msg.getMsg());
//			throw new RuntimeException(msg.getMsg()+e.getMessage());
//		}
//		return msg;
//	}
	
	/**
	 * 新ERP现货订单已发货 
	 */
//	public MsgVo orderDDnew(SfIdtVo sfIdtVo,SfRvdVo sfRvdVo,String oldGdnCode)  {
//		MsgVo msg = validatePrimaryParam(sfIdtVo, O2OMsgConstant.BIZTYPE.ORDERDD);
//		try {
//			//更新订单分配明细（更新出库单号、出库数量和修改进度为收货中 RP）
//			Map<Object,Object> map = new HashMap<Object,Object>();
//			map.put("doc_state", O2OBillConstant.IDT_ALLOC_DTL_PROGRESS.RP);
//			map.put("sf_idt_id", sfIdtVo.getId());
//			map.put("sf_rvd_id", sfRvdVo.getId());
//			int i = sfIdtAllocDtlMapper.updateAllocDtlRP(map);
//			logger.info("更新现货订单分配明细RP-收货中  "+i+" 条");
//			//更新现货订单明细信息的配货数量
//			int j = sfIdtMapper.updateSfIdtDtlAllocQty(map);
//			logger.info("对现货订单明细信息的发货数量修改 "+j+" 条");
//			//更新现货订单进度 - 收货中
//			map.put("doc_state", NEW_DOC_STATE.RG_NEW_P);
//			int k = sfIdtMapper.updateIdtDocState(map);
//			logger.info("对现货订单进度进行修改-收货中 "+k+" 条");
//			//保存单据流
//			saveSfDocFlow(sfIdtVo,DOC_FLOW_STATE_PROGRESS.SR,NewBillType.GDN.toString(),oldGdnCode);
//			saveSfDocFlow(sfIdtVo,DOC_FLOW_STATE_PROGRESS.NOA,NewBillType.RVD.toString(),sfRvdVo.getCode());
//		} catch (Exception e) {
//			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
//			msg.setMsg("新ERP现货订单已发货异常，购货方："+sfIdtVo.getBfOrgVendeeId()+"，编号："+sfIdtVo.getIdtType());
//			logger.error(msg.getMsg());
//			throw new RuntimeException(msg.getMsg()+e.getMessage());
//		}
//		return msg;
//	}
	
	/**
	 * 保存单据流
	 * @param sfIdtVo
	 * @param docFlowState
	 * @param docType
	 * @param oldOrderCode
	 */
//	private void saveSfDocFlow(SfIdtVo sfIdtVo,int docFlowState,String docType, String oldOrderCode){
//		// 源头单据类型、源头单据编码、单据执行状态、单据组织编码、单据类型（出库单、交货单、入库单、到货通知单）、单据编码、操作人、数据来源
//		try {
//			SfDocFlowVo sfDocFlowVo = new SfDocFlowVo();
//			sfDocFlowVo.setDocFlowState(docFlowState);
//			sfDocFlowVo.setSrcDocCode(sfIdtVo.getCode());
//			sfDocFlowVo.setSrcDocType(NewBillType.IDT.name());
//			sfDocFlowVo.setDocType(docType);
//			sfDocFlowVo.setUnitCode(sfIdtVo.getVenderCode());
//			sfDocFlowVo.setDocCode(oldOrderCode);
//			sfDocFlowVo.setOperUser("INTERFACE");
//			sfDocFlowVo.setDataSource(APPROVED.OLDERP);
//			sfDocFlowMapper.saveSfDocFlow(sfDocFlowVo);
//		} catch (Exception e) {
//			throw new RuntimeException("保存单据流异常！" +e.getMessage());
//		}
//	}
	
	/**
	 * 验证参数是否为空
	 * @param vo
	 * @return
	 */
	private MsgVo validatePrimaryParam(SfIdtVo sfIdtVo,String bizType){
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,bizType);
		if (null == sfIdtVo.getBfOrgVenderId() || null == sfIdtVo.getBfOrgVendeeId()) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP现货订单进度修改异常，购货方编码："+sfIdtVo.getBfOrgVendeeId()+"，订单编号："+sfIdtVo.getCode());
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
}
