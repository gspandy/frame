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
import mb.erp.dr.soa.old.dao.GdnMapper;
import mb.erp.dr.soa.old.service.bill.BillService;
import mb.erp.dr.soa.old.service.bill.CommonService;
import mb.erp.dr.soa.old.vo.GdnDtlVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 出库单服务
 * 包含接口：保存，确认，撤销
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         GdnService
 * @since       全流通改造
 */
@Service("gdnService")
public  class GdnService extends BillService<GdnVo> {
	private final Logger logger = LoggerFactory.getLogger(GdnService.class);
	
    @Resource
    private GdnMapper gdnMapper;
    @Resource
    private CommonService commonService;
    @Value("${save.param.null}")
    private String saveParamNull;
    
    final static BillType BILL_TYPE = BillType.GDN;
    
    public MsgVo save(GdnVo gdn)  {
    	MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.SAVE,BILL_TYPE);
    	
    	// 验证参数是否为空
    	String nullProperty = nullValidate(gdn);
    	if (nullProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(nullProperty);
			throw new RuntimeException(nullProperty);
		}
    	
    	// 得到gdnNum的值
        String gdnNum = commonService.getPrimaryKey(gdn.getUnitId(),"GDN_NUM");
    	
    	gdn.setGdnNum(gdnNum);
		
        Integer ssid = gdnMapper.save(gdn);
        StringBuffer sb = new StringBuffer();
        sb.append("gdnNum:"+gdnNum+"  "+ssid);
        for (GdnDtlVo gdnDtlVo : gdn.getGdnDtlVos()) {
        	gdnDtlVo.setGdnNum(gdnNum);
        	sb.append(gdnMapper.saveDtl(gdnDtlVo));
		}
        msg.setMsg(sb.toString());
        msg.setBillNum(gdnNum);
        logger.warn("保存出库单,组织编码:{},出库单编号:{}",gdn.getUnitId(),gdnNum);
        logger.info("<<<<<<<<<<<<<<<<<->ssid:{}",sb.toString());
        return msg;
    }

    /**
	 * 确认
	 * 修改出库单的进度为CN，必须是PG->CN
	 */
	public MsgVo confirm(GdnVo gdn){
		logger.debug("确认出库单,组织编码:{},出库单编号:{}",gdn.getUnitId(),gdn.getGdnNum());
		MsgVo msg = validatePrimaryParam(gdn, O2OMsgConstant.BIZTYPE.CONFIRM);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		// 查询出库单当前状态
		String processNow = gdnMapper.selectProcess(gdn);
		if (!O2OBillConstant.PROGRESS.PG.equals(processNow)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("确认出库单异常，组织编码："+gdn.getUnitId()+"，订单编号："+gdn.getGdnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			gdn.setProgress(O2OBillConstant.PROGRESS.CN);
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.PG);
			gdn.setProgressList(progressList);
			gdnMapper.updateProcess(gdn);
		}
		return msg;
	}
	
	/**
	 * 撤销	
	 * 可撤销出库单，进度在录入中、已确认、已审核才可以撤销
	 */
	public MsgVo cancel(GdnVo gdn){
		logger.debug("撤销出库单,组织编码:{},出库单编号:{}",gdn.getUnitId(),gdn.getGdnNum());
		MsgVo msg = validatePrimaryParam(gdn, O2OMsgConstant.BIZTYPE.CANCEL);
		if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
			throw new RuntimeException(msg.getMsg());
		}
		
		// 查询出库单当前状态
		String processNow = gdnMapper.selectProcess(gdn);
		if (!(O2OBillConstant.PROGRESS.PG.equals(processNow)
				|| O2OBillConstant.PROGRESS.CN.equals(processNow))) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("撤销出库单异常，组织编码："+gdn.getUnitId()+"，订单编号："+gdn.getGdnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			gdn.setCancelled(O2OBillConstant.TORF.TRUE); // 撤销
			gdnMapper.cancel(gdn);
		}
		return msg;
	}
	
	/**
	 * 验证参数主键是否为空
	 * @param vo
	 * @return
	 */
	private MsgVo validatePrimaryParam(GdnVo vo,String bizType){
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",bizType,BILL_TYPE);
		if (SoaBillUtils.isBlank(vo.getUnitId()) 
				|| SoaBillUtils.isBlank(vo.getGdnNum())) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("验证出库单参数异常，组织编码："+vo.getUnitId()+"，订单编号："+vo.getGdnNum());
			logger.error(msg.getMsg());
			return msg;
		}
		return msg;
	}
	/**
	 * 验证需要保存的出库单
	 * @param gdnVo
	 * @return
	 */
	private String nullValidate(GdnVo gdnVo){
		String property = null;
		String result = null;
		if (SoaBillUtils.isBlank( gdnVo.getUnitId())) {
			property = "发货方编码";
		}else if (SoaBillUtils.isBlank( gdnVo.getWarehId())) {
			property = "发货仓库";
		}else if (SoaBillUtils.isBlank( gdnVo.getBrandId())) {
			property = "品牌编码";
		}else if (null ==  gdnVo.getSrcDocType()) {
			property = "订单类型";
		}else if (SoaBillUtils.isBlank( gdnVo.getDelivMode())) {
			property = "出库方式";
		}
		if (O2OBillConstant.BillType.TFO.equals(gdnVo.getSrcDocType())
				|| O2OBillConstant.BillType.TBN.equals(gdnVo.getSrcDocType())
				|| O2OBillConstant.BillType.FON.equals(gdnVo.getSrcDocType())
				|| O2OBillConstant.BillType.AAD.equals(gdnVo.getSrcDocType())) {
			 if (SoaBillUtils.isBlank( gdnVo.getRcvUnitId())) {
				property = "接收组织";
			}else if (SoaBillUtils.isBlank( gdnVo.getRcvWarehId())) {
				property = "接收仓库";
			}
		}
		if (property != null) {
			result = MessageFormat.format(saveParamNull, "出库单", property);
		}
		return result;
		
	}
	
	/**
	 * 过账中
	 */
	@Override
	public MsgVo orderFI(GdnVo gdn) {
		logger.debug("出库单过账中,组织编码:{},出库单编号:{}",gdn.getUnitId(),gdn.getGdnNum());
		MsgVo msg = validatePrimaryParam(gdn, O2OMsgConstant.BIZTYPE.ORDERFI);
		String processNow = gdnMapper.selectProcess(gdn);
		if (O2OBillConstant.PROGRESS.CN.equals(processNow)
				|| PROGRESS.DD.equals(processNow)) {
			gdn.setProgress(O2OBillConstant.PROGRESS.FI);
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.CN);
			progressList.add(O2OBillConstant.PROGRESS.DD);
			gdn.setProgressList(progressList);
			gdnMapper.updateProcess(gdn);
		} else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("出库单过账中异常，组织编码："+gdn.getUnitId()+"，订单编号："+gdn.getGdnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
    
	/**
	 * 已出库
	 */
	@Override
	public MsgVo orderDD(GdnVo gdn) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.ORDERDD,BILL_TYPE);
		String processNow = gdnMapper.selectProcess(gdn);
		if (O2OBillConstant.PROGRESS.CN.equals(processNow)
				|| PROGRESS.FI.equals(processNow)) {
			gdn.setProgress(O2OBillConstant.PROGRESS.DD);
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.FI);
			progressList.add(O2OBillConstant.PROGRESS.CN);
			gdn.setProgressList(progressList);
			gdnMapper.updateProcess(gdn);
		} else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("出库单已出库异常，组织编码："+gdn.getUnitId()+"，订单编号："+gdn.getGdnNum()+"，当前状态为"+processNow);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	
	public void sapControl(GdnVo vo) {
		if (vo == null) {
			throw new RuntimeException("出库单不存在，sap传输控制失败");
		}
		// 出库原因为空或者
		if (!validateRcvState(vo.getRcvState())) {
			return;
		} else {
			if (validateDelivMode(vo.getDelivMode())) {
				gdnMapper.updateSapFlagOne(vo);
				gdnMapper.updateSapFlagTwo(vo);
			} else {
				gdnMapper.updateSapFlagTwo(vo);
			}
			gdnMapper.updateSapFlagForH(vo);
		}

	}

	private boolean validateRcvState(String rcvState) {
		if (rcvState == null || "".equals(rcvState)) {
			return true;
		} else if (!(rcvState.equals(O2OBillConstant.RCV_STATE.AN))
				&& !(rcvState.equals(O2OBillConstant.RCV_STATE.K))
				&& !(rcvState.equals(O2OBillConstant.RCV_STATE.AM))
				&& !(rcvState.equals(O2OBillConstant.RCV_STATE.AT))) {
			return true;
		} else {
			return false;
		}

	}

	private boolean validateDelivMode(String delivMode) {
		if (delivMode == null || "".equals(delivMode)) {
			return false;
		} else if (!(GdnMode.SHOR.name().equals(delivMode))
				&& !(GdnMode.SHCR.name().equals(delivMode))
				&& !(GdnMode.TRAN.name().equals(delivMode))) {
			return false;
		} else {
			return true;
		}

	}
	
	public void newFlagControl(GdnVo gdnVo) {
		if (gdnVo == null) {
			throw new RuntimeException("出库单不存在，新ERP传输控制失败");
		}
		gdnMapper.updateNewFlag(gdnVo);
		gdnMapper.transNewFlag(gdnVo);
	}
}
