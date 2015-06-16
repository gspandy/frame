package mb.erp.dr.soa.service.impl.bill;

import java.text.MessageFormat;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.AllocType;
import mb.erp.dr.soa.constant.O2OBillConstant.DelivMode;
import mb.erp.dr.soa.constant.O2OBillConstant.NEW_DOC_STATE;
import mb.erp.dr.soa.constant.O2OBillConstant.NEW_PROGRESS;
import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;
import mb.erp.dr.soa.constant.O2OBillConstant.PROGRESS;
import mb.erp.dr.soa.constant.O2OBillConstant.RVD_PROGRESS;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.dao.SfRvdMapper;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.service.bill.NewBillService;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.utils.StatusChangeUtils;
import mb.erp.dr.soa.vo.SfRvdDtlVo;
import mb.erp.dr.soa.vo.SfRvdVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 新ERP到货通知单
 * 包括生成到货通知单，确认到货通知单，审批到货通知单和撤销到货通知单相关操作
 * @author     郭明帅
 * @version    1.0, 2014-11-19
 * @see         SfRvdService
 * @since       全流通改造
 */
@Service("sfRvdService")
public class SfRvdService extends NewBillService<SfRvdVo>{
	private final Logger logger = LoggerFactory.getLogger(SfRvdService.class);
    @Resource
    private NewERPCommonService newERPCommonService;
    @Resource
    private SfIdtService sfIdtService;
    @Resource
    private DrTbnService sfTbnService;
    @Resource
    private SfRvdMapper sfRvdMapper;
    @Value("${save.param.defValue}")
    private String saveParamDefValue; //提示：订单参数{0}默认值错误，请核实
    @Value("${save.param.null}")
    private String saveParamNull; //提示：生成{0}所需的{1}不能为空，请核实
	final static NewBillType NEW_BILL_TYPE = NewBillType.RVD;
	
	/**
	 * 生成到货通知单
	 * @param args
	 */
	public MsgVo save(SfRvdVo sfRvdVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.SAVE);
		//默认值验证
		String defValueProperty = defValueValidate(sfRvdVo);
    	if (defValueProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(defValueProperty);
			throw new RuntimeException(defValueProperty);
		}
		
		// 验证参数是否为空
    	String nullProperty = nullValidate(sfRvdVo);
    	if (nullProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(nullProperty);
			throw new RuntimeException(nullProperty);
		}
    	
    	//获取主键ID
    	Long id = newERPCommonService.getPrimaryIdNew("SF_RVD",1);
    	sfRvdVo.setId(id);
    	//获取订单CODE
    	String code = newERPCommonService.getPrimaryCode(NewBillType.RVD.toString(), 1);
    	sfRvdVo.setCode(code);
    	msg.setNewBillId(id);
    	msg.setCode(code);
    	sfRvdMapper.saveSfRvd(sfRvdVo);
		for(SfRvdDtlVo sfRvdDtlVo : sfRvdVo.getSfRvdDtlVos()){
			Long dtlId = newERPCommonService.getPrimaryIdNew("SF_DGN_DTL",1);
			sfRvdDtlVo.setId(dtlId);
			sfRvdDtlVo.setSfRvdId(id);
			sfRvdMapper.saveSfRvdDtl(sfRvdDtlVo);
		}
		//调用源头单据收货中接口
		if (NewBillType.IDT.name().equals(sfRvdVo.getOriginDocType())  && SoaBillUtils.isNotBlank(sfRvdVo.getOriginDocNum())) {
			if (!AllocType.XXUZ.equals(sfRvdVo.getAllocType())) {
				sfIdtService.orderRG(null, sfRvdVo.getOriginDocNum(), sfRvdVo.getId(),null);
			}
		}else if (NewBillType.TBN.name().equals(sfRvdVo.getOriginDocType()) && SoaBillUtils.isNotBlank(sfRvdVo.getOriginDocNum())) {
			sfTbnService.orderRG(null, sfRvdVo.getOriginDocNum(), sfRvdVo.getId(),null);
		}
		logger.warn("保存新ERP到货通知单,id:{},code:{}",id,code);
		return msg;
	}
	
	/**
	 * 确认到货通知单
	 * 修改现货订单的进度为CN，必须是PG 0 ->CN 1
	 * @param args
	 */
	public MsgVo confirm(SfRvdVo sfRvdVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.CONFIRM);
		int docState = sfRvdMapper.selectDocState(sfRvdVo);
		if(O2OBillConstant.NEW_DOC_STATE.PG_NEW != docState){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP到货通知单状态更新为【已确认】时发生异常：订单编号："+sfRvdVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(docState,NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			sfRvdVo.setDocState(NEW_DOC_STATE.CN_NEW);
			sfRvdVo.setProgress(O2OBillConstant.PROGRESS.CN);
			sfRvdMapper.updateSfRvd(sfRvdVo);
		}
		return msg;
	}
	
	/**
	 * 审批到货通知单
	 * 修改现货订单的进度为AP,必须是CN 1->AP 2
	 * @param args
	 */
	public MsgVo audit(SfRvdVo sfRvdVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.AUDIT);
		int docState = sfRvdMapper.selectDocState(sfRvdVo);
		if(O2OBillConstant.NEW_DOC_STATE.CN_NEW != docState){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP到货通知单状态更新为【已审批】时发生异常：订单编号："+sfRvdVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(docState,NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			sfRvdVo.setDocState(NEW_DOC_STATE.AP_NEW);
			sfRvdVo.setProgress(O2OBillConstant.PROGRESS.AP);
	        sfRvdMapper.updateSfRvd(sfRvdVo);
		}
		return msg;
	}
	
	/**
	 * 撤销到货通知单
	 * 进度在录入中 PG、已确认 CN 才可以撤销
	 * @param args
	 */
	public MsgVo cancel(SfRvdVo sfRvdVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.CANCEL);
		int docState = sfRvdMapper.selectDocState(sfRvdVo);
		if(O2OBillConstant.NEW_DOC_STATE.PG_NEW != docState
				|| O2OBillConstant.NEW_DOC_STATE.CN_NEW != docState
				|| O2OBillConstant.NEW_DOC_STATE.AP_NEW != docState){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP到货通知单状态更新为【已撤销】时发生异常：订单编号："+sfRvdVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(docState,NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			sfRvdVo.setDocState(NEW_DOC_STATE.CC_NEW);
			sfRvdMapper.updateSfRvd(sfRvdVo);
		}
		return msg;
	}
	
	/**
	 * 验证保存的计划配货单默认值是否匹配
	 * @param tbnVo
	 * @return
	 */
	private String defValueValidate(SfRvdVo sfRvdVo){
		String property = null;
		String result = null;
		if (O2OBillConstant.NEW_DOC_STATE.PG_NEW != sfRvdVo.getDocState()) {
			property = "DOC_STATE(单据初始状态要为0)";
		}
		if (null == sfRvdVo.getDataSource()) {
			property = "DATA_SOURCE(数据来源不能为空)";
		}
		if (!PROGRESS.PG.equals(sfRvdVo.getProgress())) {
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
	private String nullValidate(SfRvdVo sfRvdVo){
		String property = null;
		String result = null;
		if (SoaBillUtils.isBlank(sfRvdVo.getVendeeCode())) {
			property = "收货方";
		}else if (SoaBillUtils.isBlank(sfRvdVo.getRcvWarehCode())) {
			property = "收货仓库";
		}else if (SoaBillUtils.isBlank(sfRvdVo.getBrandCode())) {
			property = "品牌组编码";
		}else if (SoaBillUtils.isBlank(sfRvdVo.getSrcDocType())) {
			if (!DelivMode.OTHR.name().equals(sfRvdVo.getRcptMode())) {
				property = "原始单据类型";
			}
		}else if (SoaBillUtils.isBlank(sfRvdVo.getRcptMode())) {
			property = "入库方式";
		}else if (NewBillType.TFO.name().equals(sfRvdVo.getSrcDocType())
				|| NewBillType.TBN.name().equals(sfRvdVo.getSrcDocType())
				|| NewBillType.FON.name().equals(sfRvdVo.getSrcDocType())
				|| NewBillType.AAD.name().equals(sfRvdVo.getSrcDocType())
				|| NewBillType.SAD.name().equals(sfRvdVo.getSrcDocType())) {
			 if (SoaBillUtils.isBlank(sfRvdVo.getVenderCode())) {
				property = "发货组织";
			}else if (SoaBillUtils.isBlank(sfRvdVo.getDispWarehCode())) {
				property = "发货仓库";
			}
		}
		if (property != null) {
			result = MessageFormat.format(saveParamNull, "到货通知单", property);
		}
		return result;
	}
	
	/**
	 * 到货通知单收货中 （状态更新：DD 已发货 --> RG 收货中)
	 */
	@Override
	public MsgVo orderRG(Long sfRvdId,String code,Long sfGdrnId,GrnVo vo)  {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.ORDERRG);
		//查询新ERP到货通知单
		SfRvdVo sfRvdVo = new SfRvdVo();
		if (SoaBillUtils.isNotBlank(code)) {
			sfRvdVo = sfRvdMapper.selectSfRvdByCode(code);
		}else {
			sfRvdVo = sfRvdMapper.selectSfRvdById(sfRvdId);
		}
		if (sfRvdVo.getDocState() != NEW_DOC_STATE.AP_NEW) 
			throw new RuntimeException("新ERP到货通知单状态更新为【收货中】时发生异常：订单编号："+sfRvdVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(sfRvdVo.getDocState(),NEW_BILL_TYPE));
		//更新至收货中
		sfRvdVo.setDocState(RVD_PROGRESS.WAREHING);
		sfRvdVo.setProgress(NEW_PROGRESS.RP);
		Integer isSuccess = sfRvdMapper.updateSfRvd(sfRvdVo);
		if (isSuccess <= 0) throw new RuntimeException("新ERP到货通知单，原始单据更新至收货中时异常:"+sfRvdId);
		return msg;
	}
	
	/**
	 * 到货通知单已收货 （状态更新：RG 收货中 --> RD 已收货)
	 */
	@Override
	public MsgVo orderRD(Long sfRvdId,String code,Long sfGdrnId,GrnVo vo)  {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.ORDERRG);
		//查询新ERP到货通知单
		SfRvdVo sfRvdVo = new SfRvdVo();
		if (SoaBillUtils.isNotBlank(code)) {
			sfRvdVo = sfRvdMapper.selectSfRvdByCode(code);
		}else {
			sfRvdVo = sfRvdMapper.selectSfRvdById(sfRvdId);
		}
		//更新至已收货
		sfRvdVo.setDocState(RVD_PROGRESS.RECEIPTGOODS);
		sfRvdVo.setProgress(NEW_PROGRESS.RC);
		Integer isSuccess = sfRvdMapper.updateSfRvd(sfRvdVo);
		if (isSuccess <= 0) throw new RuntimeException("新ERP到货通知单，原始单据更新至已收货时异常:"+sfRvdId);
		return msg;
	}
	
}
