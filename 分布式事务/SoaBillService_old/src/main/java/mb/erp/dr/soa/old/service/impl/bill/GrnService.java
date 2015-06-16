package mb.erp.dr.soa.old.service.impl.bill;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OBillConstant.GdnMode;
import mb.erp.dr.soa.constant.O2OBillConstant.PROGRESS;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.old.dao.GrnMapper;
import mb.erp.dr.soa.old.service.bill.BillService;
import mb.erp.dr.soa.old.service.bill.CommonService;
import mb.erp.dr.soa.old.vo.GrnDtlVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 入库单服务
 * 包含接口：保存，确认，撤销
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         GrnService
 * @since       全流通改造
 */
@Service("grnService")
public  class GrnService extends BillService<GrnVo>{
	private final Logger logger = LoggerFactory.getLogger(GrnService.class);
	
    @Resource
    private GrnMapper grnMapper;
    @Resource
    private CommonService commonService;
    @Value("${save.param.null}")
    private String saveParamNull;
    
    final static BillType BILL_TYPE = BillType.GRN;
    
    /**
     * 保存入库单
     * @param grn
     * @return
     * @
     */
    public MsgVo save(GrnVo grn)  {
    	MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.SAVE,BILL_TYPE);
    	
    	// 验证参数是否为空
    	String nullProperty = nullValidate(grn);
    	if (nullProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(nullProperty);
			throw new RuntimeException(nullProperty);
		}
    	
    	// 得到grnNum的值
        String grnNum = commonService.getPrimaryKey(grn.getUnitId(),"GRN_NUM");
    	
    	grn.setGrnNum(grnNum);
		
        Integer ssid = grnMapper.save(grn);
        StringBuffer sb = new StringBuffer();
        sb.append("grnNum:"+grnNum+"  "+ssid);
        for (GrnDtlVo grnDtlVo : grn.getGrnDtlVos()) {
        	grnDtlVo.setGrnNum(grnNum);
        	sb.append(grnMapper.saveDtl(grnDtlVo));
		}
        
        msg.setMsg(sb.toString());
        msg.setBillNum(grnNum);
        logger.warn("保存入库单,组织编码:{},grnNum:{}",grn.getUnitId(),grnNum);
        logger.info("<<<<<<<<<<<<<<<<<->ssid:{}",sb.toString());
        return msg;
    }

    /**
	 * 确认
	 * 修改入库单的进度为CN，必须是PG->CN
	 */
	public MsgVo confirm(GrnVo grn){
		logger.info("确认入库单,组织编码:{}，单据号：{}",grn.getUnitId(),grn.getGrnNum());
		MsgVo msg = validatePrimaryParam(grn, O2OMsgConstant.BIZTYPE.CONFIRM);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		
		// 查询入库单当前状态
		String processNow = grnMapper.selectProcess(grn);
		if (!O2OBillConstant.PROGRESS.PG.equals(processNow)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("确认入库单异常，组织编码："+grn.getUnitId()+"，订单编号："+grn.getGrnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			grn.setProgress(O2OBillConstant.PROGRESS.CN);

			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.PG);
			grn.setProgressList(progressList);
			
			grnMapper.updateProcess(grn);
		}
		return msg;
	}
	
	/**
	 * 撤销	
	 * 可撤销入库单，进度在录入中、已确认、已审核才可以撤销
	 */
	public MsgVo cancel(GrnVo grn){
		logger.info("撤销入库单,组织编码:{}，单据号：{}",grn.getUnitId(),grn.getGrnNum());
		MsgVo msg = validatePrimaryParam(grn, O2OMsgConstant.BIZTYPE.CANCEL);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		// 查询入库单当前状态
		String processNow = grnMapper.selectProcess(grn);
		if (!(O2OBillConstant.PROGRESS.PG.equals(processNow)
				|| O2OBillConstant.PROGRESS.CN.equals(processNow))) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("撤销入库单异常，组织编码："+grn.getUnitId()+"，订单编号："+grn.getGrnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			grn.setCancelled(O2OBillConstant.TORF.TRUE); // 撤销
			grnMapper.cancel(grn);
		}
		return msg;
	}
	
	/**
	 * 验证参数主键是否为空
	 * @param vo
	 * @return
	 */
	private MsgVo validatePrimaryParam(GrnVo vo,String bizType){
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",bizType,BILL_TYPE);
		if (SoaBillUtils.isBlank(vo.getUnitId()) 
				|| SoaBillUtils.isBlank(vo.getGrnNum())) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("验证入库单参数异常，组织编码："+vo.getUnitId()+"，订单编号："+vo.getGrnNum());
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
	private String nullValidate(GrnVo grnVo){
		String property = null;
		String result = null;
		if (SoaBillUtils.isBlank( grnVo.getUnitId())) {
			property = "接收方编码";
		}else if (SoaBillUtils.isBlank( grnVo.getWarehId())) {
			property = "接收仓库";
		}else if (SoaBillUtils.isBlank( grnVo.getBrandId())) {
			property = "品牌编码";
		}else if (null ==  grnVo.getSrcDocType()) {
			property = "订单类型";
		}else if (SoaBillUtils.isBlank(grnVo.getRcptMode())) {
			property = "入库方式";
		}
		if (O2OBillConstant.BillType.TFO.equals(grnVo.getSrcDocType())
				|| O2OBillConstant.BillType.TBN.equals(grnVo.getSrcDocType())
				|| O2OBillConstant.BillType.FON.equals(grnVo.getSrcDocType())
				|| O2OBillConstant.BillType.AAD.equals(grnVo.getSrcDocType())) {
			 if (SoaBillUtils.isBlank( grnVo.getDispUnitId())) {
				property = "发货组织";
			}else if (SoaBillUtils.isBlank( grnVo.getDelivWarehId())) {
				property = "发货仓库";
			}
		}
		if (property != null) {
			result = MessageFormat.format(saveParamNull, "入库单", property);
		}
		return result;
		
	}
	
	/**
	 * 过账中
	 */
	@Override
	public MsgVo orderFI(GrnVo grn) {
		logger.debug("出库单过账中,组织编码:{},出库单编号:{}",grn.getUnitId(),grn.getGdnNum());
		MsgVo msg = validatePrimaryParam(grn, O2OMsgConstant.BIZTYPE.ORDERFI);
		String processNow = grnMapper.selectProcess(grn);
		if (O2OBillConstant.PROGRESS.CN.equals(processNow)) {
			grn.setProgress(O2OBillConstant.PROGRESS.FI);
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.CN);
			grn.setProgressList(progressList);
			grnMapper.updateProcess(grn);
		} else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("出库单过账中异常，组织编码："+grn.getUnitId()+"，订单编号："+grn.getGrnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
    
	/**
	 * 收货中
	 */
	@Override
	public MsgVo orderRD(GrnVo  grn) {
		logger.debug("入库单收货中,组织编码:{},出库单编号:{}",grn.getUnitId(),grn.getGdnNum());
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.ORDERRD,BILL_TYPE);
		String processNow = grnMapper.selectProcess(grn);
		if (PROGRESS.CN.equals(processNow) || PROGRESS.FI.equals(processNow)) {
			grn.setProgress(PROGRESS.RD);
			List<String> progressList = new ArrayList<String>();
			progressList.add(PROGRESS.CN);
			progressList.add(PROGRESS.FI);
			grn.setProgressList(progressList);
			grnMapper.updateProcess(grn);
		} else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("入库单收货中异常，组织编码："+grn.getUnitId()+"，订单编号："+grn.getGrnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	public void sapControl(GrnVo vo) {
		if (null == vo) {
			throw new RuntimeException("入库单不存在,传输sap失败");
		}
		if (validateRcptMode(vo.getRcptMode())) {
			grnMapper.updateRcvState(vo);
			grnMapper.updateSapFlagTwo(vo);
		} 
		grnMapper.updateSapFlag(vo);
		grnMapper.updateSapFlagForH(vo);
	}

	private boolean validateRcptMode(String rcptMode) {
		if (rcptMode == null || "".equals(rcptMode)) {
			return false;
		} else if (!(GdnMode.SHOR.name().equals(rcptMode))
				&& !(GdnMode.SHCR.name().equals(rcptMode))
				&& !(GdnMode.TRAN.name().equals(rcptMode))) {
			return false;
		} else {
			return true;
		}
	}
	
	public void newFlagControl(GrnVo vo) {
		if (null == vo) {
			throw new RuntimeException("入库单不存在，新ERP传输失败");
		}
		grnMapper.updateNewFlag(vo);
	}
}
