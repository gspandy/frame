package mb.erp.dr.soa.old.service.impl.bill;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.old.dao.ScnMapper;
import mb.erp.dr.soa.old.service.bill.BillService;
import mb.erp.dr.soa.old.service.bill.CommonService;
import mb.erp.dr.soa.old.vo.ScnDtlVo;
import mb.erp.dr.soa.old.vo.ScnVo;
import mb.erp.dr.soa.utils.DateUtil;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 退货单生成
 * 包括生成退货单，确认退货单，审批退货单和撤销退货单相关操作
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         ScnService
 * @since       全流通改造
 */
@Service("scnService")
public class ScnService extends BillService<ScnVo>{
	private final Logger logger = LoggerFactory.getLogger(ScnService.class);
	@Resource
	private ScnMapper scnMapper;
    @Resource
    private CommonService commonService;
    @Value("${save.param.null}")
    private String saveParamNull; //提示：生成{0}所需的{1}不能为空，请核实
    @Value("${save.param.defValue}")
    private String saveParamDefValue; //提示：订单参数{0}默认值错误，请核实
    
    final static BillType BILL_TYPE = BillType.SSC;
	/**
	 * 生成退货单
	 * @param args
	 */
	public MsgVo save(ScnVo scnVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.SAVE,BILL_TYPE);
		//默认值验证
		String defValueProperty = defValueValidate(scnVo);
    	if (defValueProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(defValueProperty);
			throw new RuntimeException(defValueProperty);
		}
    	//默认值赋值
    	scnVo = defAssign(scnVo);
		
		// 验证参数是否为空
    	String nullProperty = nullValidate(scnVo);
    	if (nullProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(nullProperty);
			throw new RuntimeException(nullProperty);
		}
    	// 得到scnNum的值
        String scnNum = commonService.getPrimaryKey(scnVo.getVendeeId(),"SCN_NUM");
    	msg.setBillNum(scnNum);
    	scnVo.setScnNum(scnNum);;
    	
        scnMapper.save(scnVo);
		for(ScnDtlVo scnDtlVo : scnVo.getScnDtlVos()){
			scnDtlVo.setScnNum(scnNum);
		}
		saveDtlForBatch(scnVo.getScnDtlVos());
		logger.warn("保存退货单,退货方:{},scnNum:{}",scnVo.getVendeeId(),scnNum);
		return msg;
	}
	
	/**
	 * 批量保存退货单明细
	 * @param scnDtlVos
	 */
	private void saveDtlForBatch(List<ScnDtlVo> scnDtlVos) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("scnDtlList", scnDtlVos);
		scnMapper.saveDtlForBatch(map);
	}

	/**
	 * 确认退货单
	 * 修改退货单的进度为CN，必须是PG->CN
	 * @param args
	 */
	public MsgVo confirm(ScnVo scnVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.CONFIRM,BILL_TYPE);
		String progress = scnMapper.selectProgress(scnVo);
		if(!O2OBillConstant.PROGRESS.PG.equals(progress)){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("确认退货单异常，退货方："+scnVo.getVendeeId()+"，订单编号："+scnVo.getBgrNum()+"，当前状态为"+progress);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			scnVo.setProgress(O2OBillConstant.PROGRESS.CN);
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.PG);
			scnVo.setProgressList(progressList);
			scnMapper.update(scnVo);
		}
		return msg;
	}
	
	/**
	 * 审批退货单
	 * 修改退货单的进度为AP,必须是CN->AP
	 * @param scnVo
	 */
	public MsgVo audit(ScnVo scnVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.AUDIT,BILL_TYPE);
		String progress = scnMapper.selectProgress(scnVo);
		if(!O2OBillConstant.PROGRESS.CN.equals(progress)){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("审批退货单异常，退货方："+scnVo.getVendeeId()+"，订单编号："+scnVo.getScnNum()+"，当前状态为"+progress);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			//审批时间
			scnVo.setInvsgTime(DateUtil.now());
			scnVo.setProgress(O2OBillConstant.PROGRESS.AP);
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.CN);
			scnVo.setProgressList(progressList);
			scnMapper.update(scnVo);
		}
		return msg;
	}
	
	/**
	 * 撤销退货单
	 * 进度在录入中 PG、已确认 CN、已审核 AP才可以撤销
	 * @param args
	 */
	public MsgVo cancel(ScnVo scnVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.CANCEL,BILL_TYPE);
		String progress = scnMapper.selectProgress(scnVo);
		if(!(O2OBillConstant.PROGRESS.PG.equals(progress)
				|| O2OBillConstant.PROGRESS.CN.equals(progress)
				|| O2OBillConstant.PROGRESS.AP.equals(progress))){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("撤销退货单异常，退货方："+scnVo.getVendeeId()+"，订单编号："+scnVo.getScnNum()+"，当前状态为"+progress);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			scnVo.setCancelled("T");
			scnMapper.update(scnVo);
		}
		return msg;
	}
	
	/**
	 * 验证需要保存的退货单默认值是否匹配
	 * @param scnVo
	 * @return
	 */
	private String defValueValidate(ScnVo scnVo){
		StringBuffer property = new StringBuffer();
		String result = null;
		if (!O2OBillConstant.PROGRESS.PG.equals(scnVo.getProgress())) {
			property.append("处理进度  ");
		}
		if (!"F".equals(scnVo.getSuspended())) {
			property.append("是否挂起  ");
		}
		if (!"F".equals(scnVo.getCancelled())) {
			property.append("是否撤销  ");
		}
		if (property.length() > 0) {
			result = MessageFormat.format(saveParamDefValue, property.toString());
		}
		return result;
		
	}
	
	/**
	 * 退货单参数赋默认值
	 * @param scnVo
	 * @return
	 */
	private ScnVo defAssign(ScnVo scnVo){
		scnVo.setCrType(SoaBillUtils.isBlank(scnVo.getCrType()) ? "G" : scnVo.getCrType());
		return scnVo;
	}
	
	/**
	 * 验证需要保存的退货单的必要参数是否为空
	 * @param scnVo
	 * @return
	 */
	private String nullValidate(ScnVo scnVo){
		StringBuffer property = new StringBuffer();
		String result = null;
		if (StringUtils.isEmpty(scnVo.getVendeeId())) {
			property.append("退货方编码  ");
		}
		if (StringUtils.isEmpty(scnVo.getVenderId())) {
			property.append("收货方编码  ");
		}
		if (StringUtils.isEmpty(scnVo.getRcvWarehId())) {
			property.append("接收仓库编码  ");
		}
		if (StringUtils.isEmpty(scnVo.getDispWarehId())) {
			property.append("退货仓库编码 ");
		}
		if (StringUtils.isEmpty(scnVo.getBrandId())) {
			property.append("品牌编码  ");
		}
		if (property.length() > 0) {
			result = MessageFormat.format(saveParamNull, "退货单", property.toString());
		}
		return result;
		
	}
	
	/**
	 * 退货单过账中 （状态更新：AP 已审核 --> FI 过账中)
	 */
	public MsgVo orderFI(ScnVo scnVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.ORDERFI,BILL_TYPE);
		String progress = scnMapper.selectProgress(scnVo);
		if(!O2OBillConstant.PROGRESS.AP.equals(progress)){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("退货申请单异常，退货方："+scnVo.getVendeeId()+"，订单编号："+scnVo.getScnNum()+"，当前状态为"+progress);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			scnVo.setProgress(O2OBillConstant.PROGRESS.FI);
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.AP);
			scnVo.setProgressList(progressList);
			scnMapper.update(scnVo);
		}
		return msg;
	}
	
	
	/**
	 * 退货单收货中 （状态更新：DD 已发货 --> RG 收货中)
	 */
	public MsgVo orderRG(ScnVo scnVo)  {
		//查询退货单
		scnVo = scnMapper.select(scnVo);
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.ORDERFI,BILL_TYPE);
		if (O2OBillConstant.PROGRESS.AP.equals(scnVo.getProgress()) || O2OBillConstant.PROGRESS.FI.equals(scnVo.getProgress())) {
			scnVo.setProgress(O2OBillConstant.PROGRESS.RG);
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.AP);
			progressList.add(O2OBillConstant.PROGRESS.FI);
			scnVo.setProgressList(progressList);
			scnVo.setRcvQty(0.0);
			scnMapper.update(scnVo);
			//更新退货单明细 收货数量为0
			ScnDtlVo dtlVo = new ScnDtlVo();
			dtlVo.setVendeeId(scnVo.getVendeeId());
			dtlVo.setScnNum(scnVo.getScnNum());
			dtlVo.setRcvQty(0.0);
			scnMapper.updateDtl(dtlVo);
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("退货单更新为【收货中】时异常，退货方："+scnVo.getVendeeId()+"，订单编号："+scnVo.getScnNum()+"，当前状态为"+scnVo.getProgress());
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 退货单已收货 （状态更新：RG 收货中 FI --> RD 已收货)
	 */
	public MsgVo orderRD(ScnVo scnVo)  {
		//查询退货单
		scnVo = scnMapper.select(scnVo);
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.ORDERFI,BILL_TYPE);
		if (O2OBillConstant.PROGRESS.RG.equals(scnVo.getProgress()) || O2OBillConstant.PROGRESS.FI.equals(scnVo.getProgress())) {
			scnVo.setProgress(O2OBillConstant.PROGRESS.RD);
			//更新退货单进度为已收货RD,发货数量为入库单入库数量，发货金额为入库单入库金额
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.RG);
			progressList.add(O2OBillConstant.PROGRESS.FI);
			scnVo.setProgressList(progressList);
			scnMapper.update(scnVo);
			//根据入库单的入库数量更新退货单的入库数量
			//idtMapper.updateIdtByGrn(idt);
			scnMapper.updateScnByGrn(scnVo);
			//根据入库单明细的入库数量更新退货单明细的入库数量
//			adnMapper.updateAdnDtlByGrn(idt);
			scnMapper.updateScnDtlByGrn(scnVo);
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("退货单状态更新为【已收货】时异常，收货方："+scnVo.getVendeeId()+"，订单编号："+scnVo.getScnNum()+"，当前状态为"+scnVo.getProgress());
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
}
