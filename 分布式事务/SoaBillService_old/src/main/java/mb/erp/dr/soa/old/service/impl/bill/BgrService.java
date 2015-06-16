package mb.erp.dr.soa.old.service.impl.bill;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OBillConstant.PROGRESS;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.old.dao.BgrMapper;
import mb.erp.dr.soa.old.service.bill.BillService;
import mb.erp.dr.soa.old.service.bill.CommonService;
import mb.erp.dr.soa.old.vo.BgrDtlVo;
import mb.erp.dr.soa.old.vo.BgrVo;
import mb.erp.dr.soa.utils.DateUtil;
import mb.erp.dr.soa.vo.common.MsgVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 退货申请单生成
 * 包括生成退货申请单，确认退货申请单，审批退货申请单和撤销退货申请单相关操作
 * @author     陈志杰
 * @version    1.0, 2015-03-19
 * @see         BgrService
 * @since       全流通改造
 */
@Service("bgrService")
public class BgrService extends BillService<BgrVo>{
	private final Logger logger = LoggerFactory.getLogger(BgrService.class);
	@Resource
	private BgrMapper bgrMapper;
    @Resource
    private CommonService commonService;
    @Value("${save.param.null}")
    private String saveParamNull; //提示：生成{0}所需的{1}不能为空，请核实
    @Value("${save.param.defValue}")
    private String saveParamDefValue; //提示：订单参数{0}默认值错误，请核实
    final static BillType BILL_TYPE = BillType.SBG;
	/**
	 * 生成退货申请单
	 * @param args
	 */
	public MsgVo save(BgrVo bgrVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.SAVE,BILL_TYPE);
		//默认值验证
		String defValueProperty = defValueValidate(bgrVo);
    	if (defValueProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(defValueProperty);
			throw new RuntimeException(defValueProperty);
		}
    	
    	//默认值赋值
    	bgrVo = defAssign(bgrVo);
		
		// 验证参数是否为空
    	String nullProperty = nullValidate(bgrVo);
    	if (nullProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(nullProperty);
			throw new RuntimeException(nullProperty);
		}
		
    	// 得到bgrNum的值
        String bgrNum = commonService.getPrimaryKey(bgrVo.getVendeeId(),"BGR_NUM");
    	msg.setBillNum(bgrNum);
    	bgrVo.setBgrNum(bgrNum);;
    	
        bgrMapper.save(bgrVo);
		for(BgrDtlVo bgrDtlVo : bgrVo.getBgrDtlVos()){
			bgrDtlVo.setBgrNum(bgrNum);
		}
		saveDtlForBatch(bgrVo.getBgrDtlVos());
		logger.warn("保存退货申请单,退货方:{},bgrNum:{}",bgrVo.getVendeeId(),bgrNum);
		return msg;
	}
	
	/**
	 * 批量保存退货申请单明细
	 * @param bgrDtlVos
	 */
	private void saveDtlForBatch(List<BgrDtlVo> bgrDtlVos) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("bgrDtlList", bgrDtlVos);
		bgrMapper.saveDtlForBatch(map);
	}

	/**
	 * 确认退货申请单
	 * 修改退货申请单的进度为CN，必须是PG->CN
	 * @param bgrVo
	 */
	public MsgVo confirm(BgrVo bgrVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.CONFIRM,BILL_TYPE);
		String progress = bgrMapper.selectProgress(bgrVo);
		if(!O2OBillConstant.PROGRESS.PG.equals(progress)){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("确认退货申请单异常，退货方："+bgrVo.getVendeeId()+"，订单编号："+bgrVo.getBgrNum()+"，当前状态为"+progress);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			bgrVo.setProgress(O2OBillConstant.PROGRESS.CN);
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.PG);
			bgrVo.setProgressList(progressList);
			bgrMapper.update(bgrVo);
		}
		return msg;
	}
	
	/**
	 * 审批退货申请单
	 * 修改退货申请单的进度为AP,必须是CN->AP
	 * @param bgrVo
	 */
	public MsgVo audit(BgrVo bgrVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.AUDIT,BILL_TYPE);
		String progress = bgrMapper.selectProgress(bgrVo);
		if(!O2OBillConstant.PROGRESS.CN.equals(progress)){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("审批退货申请单异常，退货方："+bgrVo.getVendeeId()+"，订单编号："+bgrVo.getBgrNum()+"，当前状态为"+progress);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			bgrVo.setInvsgTime(DateUtil.now());
			bgrVo.setProgress(O2OBillConstant.PROGRESS.AP);
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.CN);
			bgrVo.setProgressList(progressList);
			bgrMapper.update(bgrVo);
		}
		return msg;
	}
	
	/**
	 * 撤销退货申请单
	 * 进度在录入中 PG、已确认 CN、已审核 AP才可以撤销
	 * @param bgrVo
	 */
	public MsgVo cancel(BgrVo bgrVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.CANCEL,BILL_TYPE);
		String progress = bgrMapper.selectProgress(bgrVo);
		if(!(O2OBillConstant.PROGRESS.PG.equals(progress)
				|| O2OBillConstant.PROGRESS.CN.equals(progress)
				|| O2OBillConstant.PROGRESS.AP.equals(progress))){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("撤销退货申请单异常，退货方："+bgrVo.getVendeeId()+"，订单编号："+bgrVo.getBgrNum()+"，当前状态为"+progress);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			bgrVo.setCancelled("T");
			bgrMapper.update(bgrVo);
		}
		return msg;
	}
	
	/**
	 * 验证需要保存的退货申请单默认值是否匹配
	 * @param bgrVo
	 * @return
	 */
	private String defValueValidate(BgrVo bgrVo){
		StringBuffer property = new StringBuffer();
		String result = null;
		if (!O2OBillConstant.PROGRESS.PG.equals(bgrVo.getProgress())) {
			property.append("处理进度  ");
		}
		if (!"F".equals(bgrVo.getSuspended())) {
			property.append("是否挂起  ");
		}
		if (!"F".equals(bgrVo.getCancelled())) {
			property.append("是否撤销  ");
		}
		if (property.length() > 0) {
			result = MessageFormat.format(saveParamDefValue, property.toString());
		}
		return result;
		
	}
	
	/**
	 * 退货申请单参数赋默认值
	 * @param bgrVo
	 * @return
	 */
	private BgrVo defAssign(BgrVo bgrVo){
//		idtVo.setLowIdtFlag(SoaBillUtils.isBlank(idtVo.getLowIdtFlag()) ? "F" : idtVo.getLowIdtFlag());
//		idtVo.setIsDispReq(SoaBillUtils.isBlank(idtVo.getIsDispReq()) ? "F" : idtVo.getIsDispReq());
//		idtVo.setIsPicked(SoaBillUtils.isBlank(idtVo.getIsPicked()) ? "F" : idtVo.getIsPicked());
//		idtVo.setIsDftDisp(SoaBillUtils.isBlank(idtVo.getIsDftDisp()) ? "F" : idtVo.getIsDftDisp());
//		idtVo.setIsNeed(SoaBillUtils.isBlank(idtVo.getIsNeed()) ? "F" : idtVo.getIsNeed());
//		idtVo.setIsStockDisp(SoaBillUtils.isBlank(idtVo.getIsStockDisp()) ? "F" : idtVo.getIsStockDisp());
//		idtVo.setDataSource(SoaBillUtils.isBlank(idtVo.getDataSource()) ? APPROVED.OLDERP : idtVo.getDataSource());
//		return idtVo;
		return bgrVo;
		
	}
	
	/**
	 * 验证需要保存的退货申请单的必要参数是否为空
	 * @param bgrVo
	 * @return
	 */
	private String nullValidate(BgrVo bgrVo){
		StringBuffer property = new StringBuffer();
		String result = null;
		if (StringUtils.isEmpty(bgrVo.getVendeeId())) {
			property.append("退货方编码  ");
		}
		if (StringUtils.isEmpty(bgrVo.getVenderId())) {
			property.append("收货方编码  ");
		}
		if (StringUtils.isEmpty(bgrVo.getRcvWarehId())) {
			property.append("接收仓库编码  ");
		}
		if (StringUtils.isEmpty(bgrVo.getDispWarehId())) {
			property.append("接收仓库编码   ");
		}
		if (StringUtils.isEmpty(bgrVo.getBrandId())) {
			property.append("品牌编码  ");
		}
		if (property.length() > 0) {
			result = MessageFormat.format(saveParamNull, "退货申请单", property.toString());
		}
		return result;
		
	}
	/**
	 * 退货申请单过账中 （状态更新：AP 已审核 --> FI 过账中)
	 */
	public MsgVo orderFI(BgrVo bgrVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.ORDERFI,BILL_TYPE);
		String progress = bgrMapper.selectProgress(bgrVo);
		if(!O2OBillConstant.PROGRESS.AP.equals(progress)){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("退货申请单异常，退货方："+bgrVo.getVendeeId()+"，订单编号："+bgrVo.getBgrNum()+"，当前状态为"+progress);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			bgrVo.setProgress(O2OBillConstant.PROGRESS.FI);
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.AP);
			bgrVo.setProgressList(progressList);
			bgrMapper.update(bgrVo);
		}
		return msg;
	}
	
	/**
	 * 退货申请单发货中 （状态更新： AP-FI --> DG 发货中)
	 * @param 
	 */
	public MsgVo orderDG(BgrVo bgrVo)  {
		//查询退货申请单
		bgrVo = bgrMapper.select(bgrVo);
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.ORDERDG,BILL_TYPE);
		if (O2OBillConstant.PROGRESS.FI.equals(bgrVo.getProgress())
				|| PROGRESS.AP.equals(bgrVo.getProgress())) {
			bgrVo.setProgress(PROGRESS.DG);
			bgrVo.setDispTime(DateUtil.now());
			bgrVo.setDelivQty(0.0); //发货数量
			List<String> progressList = new ArrayList<String>();
			progressList.add(PROGRESS.FI);
			progressList.add(PROGRESS.AP);
			bgrVo.setProgressList(progressList);
			bgrMapper.update(bgrVo);
			//更新退货申请单明细上的发货数量为0
			BgrDtlVo bgrDtlVo = new BgrDtlVo();
			bgrDtlVo.setVendeeId(bgrVo.getVendeeId());
			bgrDtlVo.setBgrNum(bgrVo.getBgrNum());
			bgrDtlVo.setDelivQty(0.0);
			bgrMapper.updateDtl(bgrDtlVo);
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("退货申请单状态更新为【发货中】时异常，退货方："+bgrVo.getVendeeId()+"，订单编号："+bgrVo.getBgrNum()+"，当前状态为"+bgrVo.getProgress());
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 退货申请单已发货 （状态更新：DG 发货中 FI 过账中 --> DD 已发货)
	 */
	public MsgVo orderDD(BgrVo bgrVo)  {
		//查询退货申请单
		bgrVo = bgrMapper.select(bgrVo);
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.ORDERFI,BILL_TYPE);
		if (PROGRESS.DG.equals(bgrVo.getProgress())
				|| PROGRESS.FI.equals(bgrVo.getProgress())) {
			bgrVo.setProgress(PROGRESS.DD);
			//更新退货申请单的进度为已发货DD 发货数量为出库单出库数量，发货金额为出库单出库金额
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.DG);
			progressList.add(O2OBillConstant.PROGRESS.FI);
			bgrVo.setProgressList(progressList);
			bgrMapper.update(bgrVo);
			
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("退货申请单状态更新为【已发货】时异常，购货方："+bgrVo.getVendeeId()+"，订单编号："+bgrVo.getBgrNum()+"，当前状态为"+bgrVo.getProgress());
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
}
