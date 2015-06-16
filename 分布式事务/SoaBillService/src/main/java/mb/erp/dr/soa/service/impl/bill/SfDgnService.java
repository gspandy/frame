package mb.erp.dr.soa.service.impl.bill;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.DGN_PROGRESS;
import mb.erp.dr.soa.constant.O2OBillConstant.DelivMode;
import mb.erp.dr.soa.constant.O2OBillConstant.NEW_DOC_STATE;
import mb.erp.dr.soa.constant.O2OBillConstant.NEW_PROGRESS;
import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;
import mb.erp.dr.soa.constant.O2OBillConstant.PROGRESS;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.dao.SfDgnMapper;
import mb.erp.dr.soa.dao.SfGdnMapper;
import mb.erp.dr.soa.drools.utils.KieSessionFactory;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.service.bill.NewBillService;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.service.wareh.NewWarehService;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.utils.StatusChangeUtils;
import mb.erp.dr.soa.vo.SfDgnDtlVo;
import mb.erp.dr.soa.vo.SfDgnVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 新ERP交货单生成
 * 包括生成交货单，确认交货单，审批交货单和撤销交货单相关操作
 * @author     郭明帅
 * @version    1.0, 2014-11-19
 * @see         SfDgnService
 * @since       全流通改造
 */
@Service("sfDgnService")
public class SfDgnService extends NewBillService<SfDgnVo>{
	private final Logger logger = LoggerFactory.getLogger(SfDgnService.class);
    @Resource
    private NewERPCommonService newERPCommonService;
    @Resource
    private SfIdtService sfIdtService;
    @Resource
    private DrTbnService sfTbnService;
    @Resource
    private NewWarehService newWarehService;
    @Resource
    private SfDgnMapper sfDgnMapper;
    @Resource
    private SfGdnMapper sfGdnMapper;
    @Value("${save.param.defValue}")
    private String saveParamDefValue; //提示：订单参数{0}默认值错误，请核实
    @Value("${save.param.null}")
    private String saveParamNull; //提示：生成{0}所需的{1}不能为空，请核实
	final static NewBillType NEW_BILL_TYPE = NewBillType.DGN;
	
	/**
	 * 生成交货单
	 * @param args
	 */
	public MsgVo save(SfDgnVo sfDgnVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.SAVE);
		//默认值验证
 		String defValueProperty = defValueValidate(sfDgnVo);
    	if (defValueProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(defValueProperty);
			throw new RuntimeException(defValueProperty);
		}
		
		// 验证参数是否为空
    	String nullProperty = nullValidate(sfDgnVo);
    	if (nullProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(nullProperty);
			throw new RuntimeException(nullProperty);
		}
    	
    	//获取主键ID
    	Long id = newERPCommonService.getPrimaryIdNew("SF_DGN",1);
    	sfDgnVo.setId(id);
    	//获取订单CODE
    	String code = newERPCommonService.getPrimaryCode(NewBillType.DGN.toString(), 1);
    	sfDgnVo.setCode(code);
    	logger.warn("保存新ERP交货单,id:{},code:{}",id,code);
    	msg.setNewBillId(id);
    	msg.setCode(code);
    	sfDgnMapper.saveSfDgn(sfDgnVo);
		for(SfDgnDtlVo sfDgnDtlVo : sfDgnVo.getSfDgnDtlVos()){
			Long dtlId = newERPCommonService.getPrimaryIdNew("SF_DGN_DTL",1);
			sfDgnDtlVo.setId(dtlId);
			sfDgnDtlVo.setSfDgnId(id);
			sfDgnMapper.saveSfDgnDtl(sfDgnDtlVo);
		}
		//调用原始单据配货中接口
		if (NewBillType.IDT.name().equals(sfDgnVo.getSrcDocType())) {
			sfIdtService.orderAG(null, sfDgnVo.getSrcDocCode(), sfDgnVo.getId(),null);
		}else if (NewBillType.TBN.name().equals(sfDgnVo.getSrcDocType())) {
			sfTbnService.orderAG(null, sfDgnVo.getSrcDocCode(), sfDgnVo.getId(),null);
		}
		return msg;
	}
	
	/**
	 * 确认交货单
	 * 修改现货订单的进度为CN，必须是PG 0 ->CN 1
	 * @param args
	 */
	public MsgVo confirm(SfDgnVo sfDgnVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.CONFIRM);
		int docState = sfDgnMapper.selectDocState(sfDgnVo);
		if(NEW_DOC_STATE.PG_NEW != docState){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP交货单状态更新为【已确认】时发生异常：订单编号："+sfDgnVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(docState,NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			sfDgnVo.setDocState(NEW_DOC_STATE.CN_NEW);
			sfDgnVo.setProgress(O2OBillConstant.PROGRESS.CN);
			sfDgnVo.setLastModifiedDate(new Date());
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(NEW_DOC_STATE.PG_NEW);
			sfDgnVo.setDocStateList(docStateList);
			sfDgnMapper.updateSfDgn(sfDgnVo);
		}
		return msg;
	}
	
	/**
	 * 审批交货单
	 * 修改现货订单的进度为AP,必须是CN 1->AP 2
	 * @param args
	 */
	public MsgVo audit(SfDgnVo sfDgnVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.AUDIT);
		int docState = sfDgnMapper.selectDocState(sfDgnVo);
		if(NEW_DOC_STATE.CN_NEW != docState){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP交货单状态更新为【已审批】时发生异常：订单编号："+sfDgnVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(docState,NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			//调用原始单据已配货接口
			if (NewBillType.IDT.name().equals(sfDgnVo.getSrcDocType())) {
				sfIdtService.orderAD(null, sfDgnVo.getSrcDocCode(), sfDgnVo.getId(),null);
			}else if (NewBillType.TBN.name().equals(sfDgnVo.getSrcDocType())) {
				sfTbnService.orderAD(null, sfDgnVo.getSrcDocCode(), sfDgnVo.getId(),null);
			}
			
	    	//业务规则校验 测试
			//String controlCode = "11";
			sfDgnVo.setControlStatus(newERPCommonService.selectDirectoryCode("SFDGN", "AUDIT"));
		    KieSession kSession = KieSessionFactory.getKieSession("sfDgn-rules-audit");
		    kSession.insert(sfDgnVo);
		    kSession.insert(newWarehService);
	        kSession.fireAllRules();
	        kSession.dispose();
	        sfDgnVo.setDocState(NEW_DOC_STATE.AP_NEW);
	        sfDgnVo.setProgress(O2OBillConstant.PROGRESS.AP);
	        sfDgnVo.setLastModifiedDate(new Date());
	        List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(NEW_DOC_STATE.CN_NEW);
			sfDgnVo.setDocStateList(docStateList);
	        sfDgnMapper.updateSfDgn(sfDgnVo);
		}
		return msg;
	}
	
	/**
	 * 撤销交货单
	 * 进度在录入中 PG、已确认 CN 才可以撤销
	 * @param args
	 */
	public MsgVo cancel(SfDgnVo sfDgnVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.CANCEL);
		int docState = sfDgnMapper.selectDocState(sfDgnVo);
		if(O2OBillConstant.NEW_DOC_STATE.PG_NEW != docState
				|| O2OBillConstant.NEW_DOC_STATE.CN_NEW != docState
				|| O2OBillConstant.NEW_DOC_STATE.AP_NEW != docState){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP交货单状态更新为【已撤销】时发生异常：订单编号："+sfDgnVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(docState,NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			sfDgnVo.setDocState(NEW_DOC_STATE.CC_NEW);
			sfDgnVo.setLastModifiedDate(new Date());
			sfDgnMapper.updateSfDgn(sfDgnVo);
		}
		return msg;
	}
	
	/**
	 * 验证保存的交货单默认值是否匹配
	 * @param tbnVo
	 * @return
	 */
	private String defValueValidate(SfDgnVo sfDgnVo){
		String property = null;
		String result = null;
		if (O2OBillConstant.NEW_DOC_STATE.PG_NEW != sfDgnVo.getDocState()) {
			property = "DOC_STATE(单据初始状态要为0)";
		}
		if (null == sfDgnVo.getDataSource()) {
			property = "DATA_SOURCE(数据来源不能为空)";
		}
		if (!PROGRESS.PG.equals(sfDgnVo.getProgress())) {
			property = "PROGRESS(单据初始进度要为PG)";
		}
		if (property != null) {
			result = MessageFormat.format(saveParamDefValue, property);
		}
		return result;
	}
	
	/**
	 * 验证需要保存的交货单必要参数是否为空
	 * @param tbnVo
	 * @return
	 */
	private String nullValidate(SfDgnVo sfDgnVo){
		String property = null;
		String result = null;
		if (SoaBillUtils.isBlank(sfDgnVo.getVenderCode())) {
			property = "发货方";
		}else if (SoaBillUtils.isBlank(sfDgnVo.getDispWarehCode())) {
			property = "发货仓库";
		}else if (SoaBillUtils.isBlank(sfDgnVo.getBrandCode())) {
			property = "品牌组编码";
		}else if (SoaBillUtils.isBlank(sfDgnVo.getSrcDocType())) {
			if (!DelivMode.OTHR.name().equals(sfDgnVo.getDelivMode())) {
				property = "原始单据类型";
			}
		}else if (SoaBillUtils.isBlank(sfDgnVo.getDelivMode())) {
			property = "出库方式";
		}else if (NewBillType.TFO.name().equals(sfDgnVo.getSrcDocType())
				|| NewBillType.TBN.name().equals(sfDgnVo.getSrcDocType())
				|| NewBillType.FON.name().equals(sfDgnVo.getSrcDocType())
				|| NewBillType.AAD.name().equals(sfDgnVo.getSrcDocType())) {
			 if (SoaBillUtils.isBlank(sfDgnVo.getVendeeCode())) {
				property = "接收组织";
			}else if (SoaBillUtils.isBlank(sfDgnVo.getRcvWarehCode())) {
				property = "接收仓库";
			}
		}
		if (SoaBillUtils.isNotBlank(property)) {
			result = MessageFormat.format(saveParamNull, "交货单", property);
		}
		return result;
	}
	
	/**
	 * 交货单单发货中 （状态更新：AP 已审批或分拣完成 --> DG 发货中)
	 */
	@Override
	public MsgVo orderDG(Long sfDgnId,String code,Long sfGdrnId)  {
		//查询新ERP交货单
		SfDgnVo sfDgnVo = new SfDgnVo();
		if (SoaBillUtils.isNotBlank(code)) {
			sfDgnVo = sfDgnMapper.selectDgnByCode(code);
		}else {
			sfDgnVo = sfDgnMapper.selectDgnById(sfDgnId);
		}
		MsgVo msg = validatePrimaryParam(sfDgnVo, O2OMsgConstant.BIZTYPE.ORDERDG);
		if (NEW_DOC_STATE.AP_NEW == sfDgnVo.getDocState() || DGN_PROGRESS.COMPLETE_PKN == sfDgnVo.getDocState()) {
			//更新交货单进度到发货中
			sfDgnVo.setDocState(DGN_PROGRESS.DELIVING);
			sfDgnVo.setProgress(NEW_PROGRESS.SP);
			sfDgnVo.setLastModifiedDate(new Date());
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(NEW_DOC_STATE.AP_NEW);
			docStateList.add(DGN_PROGRESS.COMPLETE_PKN);
			sfDgnVo.setDocStateList(docStateList);
			sfDgnMapper.updateSfDgn(sfDgnVo);
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP交货单状态更新为【发货中】时发生异常：订单编号："+sfDgnVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(sfDgnVo.getDocState(),NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 交货单已发货 （状态更新：DG 发货中 --> DD 已发货)
	 */
	@Override
	public MsgVo orderDD(Long sfDgnId,String code,Long sfGdrnId,GdnVo vo)  {
		//查询新ERP交货单
		SfDgnVo sfDgnVo = new SfDgnVo();
		if (SoaBillUtils.isNotBlank(code)) {
			sfDgnVo = sfDgnMapper.selectDgnByCode(code);
		}else {
			sfDgnVo = sfDgnMapper.selectDgnById(sfDgnId);
		}
		MsgVo msg = validatePrimaryParam(sfDgnVo, O2OMsgConstant.BIZTYPE.ORDERDD);
		if (DGN_PROGRESS.DELIVING == sfDgnVo.getDocState()) {
			//释放交货单明细上已分配库存 TODO
			//更新交货单进度到已发货
			sfDgnVo.setDocState(DGN_PROGRESS.HAVE_OUT_WAREH);
			sfDgnVo.setProgress(NEW_PROGRESS.SC);
			sfDgnVo.setLastModifiedDate(new Date());
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(DGN_PROGRESS.DELIVING);
			sfDgnVo.setDocStateList(docStateList);
			sfDgnMapper.updateSfDgn(sfDgnVo);
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP交货单状态更新为【已发货】时发生异常：订单编号："+sfDgnVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(sfDgnVo.getDocState(),NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 验证参数是否为空
	 * @param vo
	 * @return
	 */
	private MsgVo validatePrimaryParam(SfDgnVo sfDgnVo,String bizType){
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,bizType);
		if (null == sfDgnVo.getBfOrgUnitId() || null == sfDgnVo.getWarehId()) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP交货单进度修改异常，发货方编码："+sfDgnVo.getBfOrgUnitId()+"，ID："+sfDgnVo.getId());
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
}
