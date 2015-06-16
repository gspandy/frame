package mb.erp.dr.soa.service.impl.bill;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant.NEW_DOC_STATE;
import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;
import mb.erp.dr.soa.constant.O2OBillConstant.GRN_PROGRESS;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.dao.SfGrnMapper;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.service.bill.NewBillService;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.utils.StatusChangeUtils;
import mb.erp.dr.soa.vo.SfGrnDtlVo;
import mb.erp.dr.soa.vo.SfGrnVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 新ERP入库单服务
 * 包含接口：保存，确认，撤销
 * @author     余从玉
 * @version    1.0, 2014-12-08
 * @see         SfGrnService
 * @since       全流通改造
 */
@Service("sfGrnService")
public  class SfGrnService extends NewBillService<SfGrnVo>{
	private final Logger logger = LoggerFactory.getLogger(SfGrnService.class);
	
    @Resource
    private SfGrnMapper sfGrnMapper;
    @Resource
    private NewERPCommonService newERPCommonService;
    @Value("${save.param.null}")
    private String saveParamNull;
    
    final static NewBillType NEW_BILL_TYPE = NewBillType.GRN;
    
    /**
     * 保存入库单
     * @param grn
     * @return
     * @
     */
    public MsgVo save(SfGrnVo grn)  {
    	MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.SAVE);
    	
    	// 验证参数是否为空
    	String nullProperty = nullValidate(grn);
    	if (nullProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(nullProperty);
			throw new RuntimeException(nullProperty);
		}
    	
    	// 得到grnNum的值
        Long sfGrnId = newERPCommonService.getPrimaryIdNew("SF_GRN",1);
        String code = newERPCommonService.getPrimaryCode(NewBillType.GRN.toString(), 1);
    	grn.setId(sfGrnId);
    	grn.setGrnNum(code);
		
        Integer ssid = sfGrnMapper.save(grn);
        StringBuffer sb = new StringBuffer();
        sb.append("sfGrnId:"+sfGrnId+"  "+ssid);
        for (SfGrnDtlVo grnDtlVo : grn.getDtlVos()) {
        	grnDtlVo.setSfGrnId(sfGrnId);
        	sb.append(sfGrnMapper.saveDtl(grnDtlVo));
		}
        
        msg.setMsg(sb.toString());
        logger.info("<<<<<<<<<<<<<<<<<->ssid:{}",sb.toString());
        logger.warn("保存新ERP入库单,id:{}",sfGrnId);
        return msg;
    }

    /**
	 * 确认
	 * 修改入库单的进度为CN，必须是PG->CN
	 */
	public MsgVo confirm(SfGrnVo grn){
		logger.info("确认入库单,组织编码:{}，单据号：{}",grn.getUnitId(),grn.getGrnNum());
		MsgVo msg = validatePrimaryParam(grn, O2OMsgConstant.BIZTYPE.CONFIRM);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		
		// 查询入库单当前状态
		Integer processNow = sfGrnMapper.selectProcess(grn);
		if (!(NEW_DOC_STATE.PG_NEW == processNow)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP入库单状态更新为【已确认】时发生异常：订单编号："+grn.getGrnNum()+" 当前订单状态："+StatusChangeUtils.getStatus(processNow,NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			grn.setDocState(NEW_DOC_STATE.CN_NEW); // 确认
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(NEW_DOC_STATE.PG_NEW);
			grn.setDocStateList(docStateList);
			sfGrnMapper.updateProcess(grn);
		}
		return msg;
	}
	
	/**
	 * 撤销	
	 * 可撤销入库单，进度在录入中、已确认、已审核才可以撤销
	 */
	public MsgVo cancel(SfGrnVo grn){
		logger.info("撤销入库单,组织编码:{}，单据号：{}",grn.getUnitId(),grn.getGrnNum());
		MsgVo msg = validatePrimaryParam(grn, O2OMsgConstant.BIZTYPE.CANCEL);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		// 查询入库单当前状态
		Integer processNow = sfGrnMapper.selectProcess(grn);
		if (!((NEW_DOC_STATE.PG_NEW == processNow)
				|| (NEW_DOC_STATE.CN_NEW == processNow))) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP入库单状态更新为【已撤销】时发生异常：订单编号："+grn.getGrnNum()+" 当前订单状态："+StatusChangeUtils.getStatus(processNow,NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			grn.setDocState(NEW_DOC_STATE.CC_NEW); // 撤销
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(NEW_DOC_STATE.PG_NEW);
			docStateList.add(NEW_DOC_STATE.CN_NEW);
			grn.setDocStateList(docStateList);
			sfGrnMapper.updateProcess(grn);
		}
		return msg;
	}
	
	/**
	 * 验证参数主键是否为空
	 * @param vo
	 * @return
	 */
	private MsgVo validatePrimaryParam(SfGrnVo vo,String bizType){
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,bizType);
		if (null == vo.getId()) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("处理入库单参数异常，单据编号："+vo.getId());
			logger.error(msg.getMsg());
			return msg;
		}
		return msg;
	}
	/**
	 * 验证需要保存的入库单
	 * @param grnVo
	 * @return
	 */
	private String nullValidate(SfGrnVo grnVo){
		String property = null;
		String result = null;
		if (null == grnVo.getUnitId()) {
			property = "接收方编码";
		}else if (null == grnVo.getWarehId()) {
			property = "接收仓库";
		}else if (null == grnVo.getBrandId()) {
			property = "品牌编码";
		}else if (null ==  grnVo.getSrcDocType()) {
			property = "订单类型";
		}else if (SoaBillUtils.isBlank(grnVo.getRcptMode())) {
			property = "入库方式";
		}
		if (NewBillType.TFO.equals(grnVo.getSrcDocType())
				|| NewBillType.TBN.equals(grnVo.getSrcDocType())
				|| NewBillType.FON.equals(grnVo.getSrcDocType())
				|| NewBillType.AAD.equals(grnVo.getSrcDocType())) {
			 if (null == grnVo.getDispUnitId()) {
				property = "发货组织";
			}else if (null == grnVo.getDelivWarehId()) {
				property = "发货仓库";
			}
		}
		if (property != null) {
			result = MessageFormat.format(saveParamNull, "入库单", property);
		}
		return result;
		
	}
    

	
	@Override
	public MsgVo orderRD(Long grnId,String code,Long sfGdrnId,GrnVo vo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.ORDERRD);
		SfGrnVo	sfGrnVo = sfGrnMapper.selectGrnById(grnId);
		sfGrnVo.setDocState(GRN_PROGRESS.RECEIVED);
		List<Integer> docStateList = new ArrayList<Integer>();
		docStateList.add(NEW_DOC_STATE.CN_NEW);
		sfGrnVo.setDocStateList(docStateList);
		sfGrnMapper.updateProcess(sfGrnVo);
		return msg;
	}
}
