package mb.erp.dr.soa.old.service.impl.bill;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.APPROVED;
import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.drools.utils.KieSessionFactory;
import mb.erp.dr.soa.old.dao.AdnMapper;
import mb.erp.dr.soa.old.dao.GdnMapper;
import mb.erp.dr.soa.old.dao.IdtMapper;
import mb.erp.dr.soa.old.dao.SysParameterMapper;
import mb.erp.dr.soa.old.dao.WarehMapper;
import mb.erp.dr.soa.old.dao.common.CommonMapper;
import mb.erp.dr.soa.old.service.balance.BalanceService;
import mb.erp.dr.soa.old.service.bill.BillService;
import mb.erp.dr.soa.old.service.bill.CommonService;
import mb.erp.dr.soa.old.service.price.SettlementPriceRateService;
import mb.erp.dr.soa.old.service.wareh.WarehService;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.IdtDtlVo;
import mb.erp.dr.soa.old.vo.IdtVo;
import mb.erp.dr.soa.utils.DateUtil;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;

/**
 * 现货订单生成
 * 包括生成现货订单，确认现货订单，审批现货订单和撤销现货订单相关操作
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         IdtService
 * @since       全流通改造
 */
@Service("idtService")
public class IdtService extends BillService<IdtVo>{
	private final Logger logger = LoggerFactory.getLogger(IdtService.class);
	@Resource
	private IdtMapper idtMapper;
	@Resource
	private AdnMapper adnMapper;
    @Resource
    private GdnMapper gdnMapper;
    @Resource
    private WarehMapper warehMapper;
	@Resource
	private SysParameterMapper sysParameterMapper;
	@Resource
	private CommonMapper commonMapper;
    @Resource
    private CommonService commonService;
    @Resource
    private SettlementPriceRateService settlementPriceRateService;
    @Resource
    private WarehService warehService;
    @Resource
    private BalanceService balanceService;
    @Resource
    private IdtService idtService;
    @Value("${save.param.null}")
    private String saveParamNull; //提示：生成{0}所需的{1}不能为空，请核实
    @Value("${save.param.defValue}")
    private String saveParamDefValue; //提示：订单参数{0}默认值错误，请核实
    @Value("${settle.date.error}")
    private String settleDateError; //提示：代理商{0}的下属门店{1}尚未在规定期限内结账
    final static BillType BILL_TYPE = BillType.IDT;
	/**
	 * 生成现货订单
	 * @param args
	 */
	public MsgVo save(IdtVo idtVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.SAVE,BILL_TYPE);
		//默认值验证
		String defValueProperty = defValueValidate(idtVo);
    	if (defValueProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(defValueProperty);
			throw new RuntimeException(defValueProperty);
		}
    	
    	//默认值赋值
    	idtVo = defAssign(idtVo);
		
		// 验证参数是否为空
    	String nullProperty = nullValidate(idtVo);
    	if (nullProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(nullProperty);
			throw new RuntimeException(nullProperty);
		}
		
    	// 得到idtNum的值
        String idtNum = commonService.getPrimaryKey(idtVo.getVendeeId(),"IDT_NUM");
    	msg.setBillNum(idtNum);
    	idtVo.setIdtNum(idtNum);
    	
    	//非线上订单执行业务规则校验
    	if (!O2OBillConstant.IS_OOS.M.equals(idtVo.getIsOos())) {
    		idtVo.setControlStatus(commonService.selectDirectoryCode("IDT","SAVE"));
    		KieSession kSession = KieSessionFactory.getKieSession("idt-rules-save");
    		kSession.insert(idtVo);
    		kSession.insert(new SoaBillUtils());
    		kSession.insert(settlementPriceRateService);
    		kSession.fireAllRules();
    		kSession.dispose();
		}
        idtMapper.save(idtVo);
		for(IdtDtlVo idtDtlVo : idtVo.getIdtDtlVos()){
			idtDtlVo.setIdtNum(idtNum);
			idtMapper.saveDtl(idtDtlVo);
		}
		logger.warn("保存现货订单,购货方:{},idtNum:{}",idtVo.getVendeeId(),idtNum);
		return msg;
	}
	
	/**
	 * 确认现货订单
	 * 修改现货订单的进度为CN，必须是PG->CN
	 * @param args
	 */
	public MsgVo confirm(IdtVo idtVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.CONFIRM,BILL_TYPE);
		String progress = idtMapper.selectProgress(idtVo);
		if(!O2OBillConstant.PROGRESS.PG.equals(progress)){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("确认现货订单异常，购货方："+idtVo.getVendeeId()+"，订单编号："+idtVo.getIdtNum()+"，当前状态为"+progress);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			idtVo.setProgress(O2OBillConstant.PROGRESS.CN);
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.PG);
			idtVo.setProgressList(progressList);
			idtMapper.update(idtVo);
		}
		return msg;
	}
	
	/**
	 * 审批现货订单
	 * 修改现货订单的进度为AP,必须是CN->AP
	 * @param args
	 */
	public MsgVo audit(IdtVo idtVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.AUDIT,BILL_TYPE);
		String progress = idtMapper.selectProgress(idtVo);
		if(!O2OBillConstant.PROGRESS.CN.equals(progress)){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("审批现货订单异常，购货方："+idtVo.getVendeeId()+"，订单编号："+idtVo.getIdtNum()+"，当前状态为"+progress);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			//业务规则校验 测试
			String control =commonService.selectDirectoryCode("IDT","AUDIT");//1011 
//			String control = "1010";// TODO 为了测试专项资金，需调用正数冻结
			idtVo.setControlStatus(control);
			KieSession kSession = KieSessionFactory.getKieSession("idt-rules-audit");
			kSession.insert(idtVo);
			kSession.insert(settlementPriceRateService);
			kSession.insert(balanceService);
			kSession.insert(idtService);
			kSession.insert(new SoaBillUtils());
			kSession.fireAllRules();
			kSession.dispose();
			if (control.matches("[0-1]1*")) {// 如果重算单价，更新明细
				for(IdtDtlVo idtDtlVo : idtVo.getIdtDtlVos()){
					idtDtlVo.setIdtNum(idtVo.getIdtNum());
					idtMapper.updateDtl(idtDtlVo);
				}
			}
			idtVo.setOrdAt(DateUtil.now());
			idtVo.setProgress(O2OBillConstant.PROGRESS.AP);
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.CN);
			idtVo.setProgressList(progressList);
			idtMapper.update(idtVo);
		}
		return msg;
	}
	
	/**
	 * 撤销现货订单
	 * 进度在录入中 PG、已确认 CN、已审核 AP才可以撤销
	 * @param args
	 */
	public MsgVo cancel(IdtVo idtVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.CANCEL,BILL_TYPE);
		String progress = idtMapper.selectProgress(idtVo);
		if(!(O2OBillConstant.PROGRESS.PG.equals(progress)
				|| O2OBillConstant.PROGRESS.CN.equals(progress)
				|| O2OBillConstant.PROGRESS.AP.equals(progress))){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("撤销现货订单异常，购货方："+idtVo.getVendeeId()+"，订单编号："+idtVo.getIdtNum()+"，当前状态为"+progress);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			idtVo.setCancelled("T");
			idtMapper.update(idtVo);
		}
		return msg;
	}
	
	/**
	 * 验证需要保存的现货订单默认值是否匹配
	 * @param tbnVo
	 * @return
	 */
	private String defValueValidate(IdtVo idtVo){
		StringBuffer property = new StringBuffer();
		String result = null;
		if (!O2OBillConstant.PROGRESS.PG.equals(idtVo.getProgress())) {
			property.append("处理进度  ");
		}
		if (!"F".equals(idtVo.getSuspended())) {
			property.append("是否挂起  ");
		}
		if (!"F".equals(idtVo.getCancelled())) {
			property.append("是否撤销  ");
		}
		if (property.length() > 0) {
			result = MessageFormat.format(saveParamDefValue, property);
		}
		return result;
		
	}
	
	/**
	 * 现货订单参数赋默认值
	 * @param tbnVo
	 * @return
	 */
	private IdtVo defAssign(IdtVo idtVo){
		idtVo.setLowIdtFlag(SoaBillUtils.isBlank(idtVo.getLowIdtFlag()) ? "F" : idtVo.getLowIdtFlag());
		idtVo.setIsDispReq(SoaBillUtils.isBlank(idtVo.getIsDispReq()) ? "F" : idtVo.getIsDispReq());
		idtVo.setIsPicked(SoaBillUtils.isBlank(idtVo.getIsPicked()) ? "F" : idtVo.getIsPicked());
		idtVo.setIsDftDisp(SoaBillUtils.isBlank(idtVo.getIsDftDisp()) ? "F" : idtVo.getIsDftDisp());
		idtVo.setIsNeed(SoaBillUtils.isBlank(idtVo.getIsNeed()) ? "F" : idtVo.getIsNeed());
		idtVo.setIsStockDisp(SoaBillUtils.isBlank(idtVo.getIsStockDisp()) ? "F" : idtVo.getIsStockDisp());
		idtVo.setDataSource(SoaBillUtils.isBlank(idtVo.getDataSource()) ? APPROVED.OLDERP : idtVo.getDataSource());
		return idtVo;
		
	}
	
	/**
	 * 验证需要保存的现货订单的必要参数是否为空
	 * @param tbnVo
	 * @return
	 */
	private String nullValidate(IdtVo idtVo){
		StringBuffer property = new StringBuffer();
		String result = null;
		if (StringUtils.isEmpty(idtVo.getVendeeId())) {
			property.append("购货方编码  ");
		}
		if (StringUtils.isEmpty(idtVo.getVenderId())) {
			property.append("供货方编码  ");
		}
		if (StringUtils.isEmpty(idtVo.getRcvWarehId())) {
			property.append("接收仓库编码  ");
		}
		if (StringUtils.isEmpty(idtVo.getShopId())) {
			property.append("订货门店  ");
		}
		if (StringUtils.isEmpty(idtVo.getBrandId())) {
			property.append("品牌编码  ");
		}
		if (StringUtils.isEmpty(idtVo.getIdtType())) {
			property.append("订单类型  ");
		}
		if (property.length() > 0) {
			result = MessageFormat.format(saveParamNull, "现货订单", property);
		}
		return result;
		
	}
	
	/**
	 * 现货订单配货中 （状态更新：AP 已审核 --> AG 配货中)
	 */
	public MsgVo orderAG(IdtVo idt)  {
		//查询现货订单
		idt = idtMapper.select(idt);
		MsgVo msg = validatePrimaryParam(idt, O2OMsgConstant.BIZTYPE.ORDERAG);
		if (O2OBillConstant.PROGRESS.AP.equals(idt.getProgress()) || O2OBillConstant.PROGRESS.FI.equals(idt.getProgress())) {
			idt.setProgress(O2OBillConstant.PROGRESS.AG);
			idt.setAdmQty(0.0); //配货数量
			idt.setAdmVal(0.0); //配货金额
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.AP);
			progressList.add(O2OBillConstant.PROGRESS.FI);
			idt.setProgressList(progressList);
			idtMapper.update(idt);
			//更新现货订单详情上的配货数量为0
			IdtDtlVo idtDtlVo = new IdtDtlVo();
			idtDtlVo.setVendeeId(idt.getVendeeId());
			idtDtlVo.setIdtNum(idt.getIdtNum());
			idtDtlVo.setAdmQty(0.0);
			idtMapper.updateDtl(idtDtlVo);
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("现货订单状态更新为【配货中】时异常，购货方："+idt.getVendeeId()+"，订单编号："+idt.getIdtNum()+"，当前状态为"+idt.getProgress());
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 现货订单已配货 （状态更新：AG 配货中 --> AD 已配货)
	 */
	public MsgVo orderAD(IdtVo idt)  {
		//查询现货订单
		idt = idtMapper.select(idt);
		MsgVo msg = validatePrimaryParam(idt, O2OMsgConstant.BIZTYPE.ORDERAD);
		if (O2OBillConstant.PROGRESS.AG.equals(idt.getProgress()) || O2OBillConstant.PROGRESS.FI.equals(idt.getProgress())) {
			idt.setProgress(O2OBillConstant.PROGRESS.AD);
			//更新现货订单的配货数量和配货金额为计划配货单的对应值
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.AG);
			progressList.add(O2OBillConstant.PROGRESS.FI);
			idt.setProgressList(progressList);
			idtMapper.updateByAdn(idt);
			//更新现货订单详情的配货数量为计划配货单详情的配货数量
			idtMapper.updateDtlByAdn(idt);
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("现货订单状态更新为【已配货】时异常，购货方："+idt.getVendeeId()+"，订单编号："+idt.getIdtNum()+"，当前状态为"+idt.getProgress());
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 现货订单发货中 （状态更新：AD 已配货 --> DG 发货中)
	 */
	public MsgVo orderDG(IdtVo idt)  {
		//查询现货订单
		idt = idtMapper.select(idt);
		MsgVo msg = validatePrimaryParam(idt, O2OMsgConstant.BIZTYPE.ORDERDG);
		if (O2OBillConstant.PROGRESS.AD.equals(idt.getProgress()) || O2OBillConstant.PROGRESS.FI.equals(idt.getProgress())) {
			idt.setProgress(O2OBillConstant.PROGRESS.DG);
			idt.setDelivQty(0.0); //发货数量
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.AD);
			progressList.add(O2OBillConstant.PROGRESS.FI);
			idt.setProgressList(progressList);
			idtMapper.update(idt);
			//更新现货订单明细上的发货数量为0
			IdtDtlVo idtDtlVo = new IdtDtlVo();
			idtDtlVo.setVendeeId(idt.getVendeeId());
			idtDtlVo.setIdtNum(idt.getIdtNum());
			idtDtlVo.setDelivQty(0.0);
			idtMapper.updateDtl(idtDtlVo);
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("现货订单状态更新为【发货中】时异常，购货方："+idt.getVendeeId()+"，订单编号："+idt.getIdtNum()+"，当前状态为"+idt.getProgress());
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 现货订单已发货 （状态更新：DG 发货中 --> DD 已发货)
	 */
	public MsgVo orderDD(IdtVo idt)  {
		//查询现货订单
		idt = idtMapper.select(idt);
		MsgVo msg = validatePrimaryParam(idt, O2OMsgConstant.BIZTYPE.ORDERDD);
		if (O2OBillConstant.PROGRESS.DG.equals(idt.getProgress()) || O2OBillConstant.PROGRESS.FI.equals(idt.getProgress())) {
			idt.setProgress(O2OBillConstant.PROGRESS.DD);
			//更新现货订单的进度为已发货DD 发货数量为出库单出库数量，发货金额为出库单出库金额
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.DG);
			progressList.add(O2OBillConstant.PROGRESS.FI);
			idt.setProgressList(progressList);
			
			idtMapper.updateIdtByGdn(idt);
			
			//更新计划配货单的进度为已发货DD 发货数量为出库单出库数量，发货金额为出库单出库金额
			//TODO
			adnMapper.updateAdnByGdn(idt);
			
			//更新现货订单明细上的发货数量为出库单出库数量
			idtMapper.updateIdtDtlByGdn(idt);
			
			//更新计划配货单明细上的发货数量为出库单出库数量
			adnMapper.updateAdnDtlByGdn(idt);
			
//			//查询现货订单对应的计划配货单
//			AdnVo adnVo = new AdnVo();
//			adnVo.setIdtNum(idt.getIdtNum());
//			adnVo.setVendeeId(idt.getVendeeId());
//			adnVo.setVenderId(idt.getVenderId());
//			AdnVo adn = adnMapper.select(adnVo);
//			
//			OrderSearchBean orderSearchBean = new OrderSearchBean();
//			orderSearchBean.setWarehId(adn.getWarehId());
//			orderSearchBean.setDocNum(adn.getAdnNum());
//			orderSearchBean.setDocType(BillType.ADN);
//			
//			//判断计划配货单是否增加过已分配库存
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
			msg.setMsg("现货订单状态更新为【已发货】时异常，购货方："+idt.getVendeeId()+"，订单编号："+idt.getIdtNum()+"，当前状态为"+idt.getProgress());
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 现货订单收货中 （状态更新：DD 已发货 --> RG 收货中)
	 */
	public MsgVo orderRG(IdtVo idt)  {
		//查询现货订单
		idt = idtMapper.select(idt);
		MsgVo msg = validatePrimaryParam(idt, O2OMsgConstant.BIZTYPE.ORDERRG);
		if (O2OBillConstant.PROGRESS.DD.equals(idt.getProgress()) || O2OBillConstant.PROGRESS.FI.equals(idt.getProgress())) {
			idt.setProgress(O2OBillConstant.PROGRESS.RG);
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.DD);
			progressList.add(O2OBillConstant.PROGRESS.FI);
			idt.setProgressList(progressList);
			idtMapper.update(idt);
			AdnVo adnVo = new AdnVo();
			adnVo.setRcvQty(0.0); //收货数量
			//根据VENDEE_ID IDT_NUM 更新计划配货单明细中收货数量为0
			adnVo.setVendeeId(idt.getVendeeId());
			adnVo.setVenderId(idt.getVenderId());
			adnVo.setIdtNum(idt.getIdtNum());
			//TODO
			adnMapper.update(adnVo);
			adnMapper.updateAdnDtlByIdt(adnVo);
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("现货订单更新为【收货中】时异常，购货方："+idt.getVendeeId()+"，订单编号："+idt.getIdtNum()+"，当前状态为"+idt.getProgress());
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 现货订单已收货 （状态更新：RG 收货中 --> RD 已收货)
	 */
	public MsgVo orderRD(IdtVo idt)  {
		//查询现货订单
		idt = idtMapper.select(idt);
		MsgVo msg = validatePrimaryParam(idt, O2OMsgConstant.BIZTYPE.ORDERRD);
		if (O2OBillConstant.PROGRESS.RG.equals(idt.getProgress()) || O2OBillConstant.PROGRESS.FI.equals(idt.getProgress())) {
			idt.setProgress(O2OBillConstant.PROGRESS.RD);
			//更新现货订单进度为已收货RD,发货数量为入库单入库数量，发货金额为入库单入库金额
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.RG);
			progressList.add(O2OBillConstant.PROGRESS.FI);
			idt.setProgressList(progressList);
			idtMapper.updateIdtByGrn(idt);
			
			//更新计划配货单进度为已收货RD,发货数量为入库单入库数量，发货金额为入库单入库金额
			//TODO
			adnMapper.updateAdnByGrn(idt);
			
			//更新现货订单明细上的发货数量为入库单入库数量
			idtMapper.updateIdtDtlByGrn(idt);
			
			//更新计划配货单明细上的发货数量为入库单入库数量
			adnMapper.updateAdnDtlByGrn(idt);
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("现货订单状态更新为【已收货】时异常，购货方："+idt.getVendeeId()+"，订单编号："+idt.getIdtNum()+"，当前状态为"+idt.getProgress());
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 验证参数主键是否为空
	 * @param vo
	 * @return
	 */
	private MsgVo validatePrimaryParam(IdtVo vo,String bizType){
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",bizType,BILL_TYPE);
		if (SoaBillUtils.isBlank(vo.getVenderId()) 
				|| SoaBillUtils.isBlank(vo.getIdtNum()) || SoaBillUtils.isBlank(vo.getVendeeId())) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("验证计划配货单参数异常，购货方编码："+vo.getVendeeId()+"，订单编号："+vo.getIdtNum()+"，进度："+vo.getProgress());
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 判断购货方下的所有门店是否已结转（结转日期往前推）
	 * @param owner_id  购货方
	 */
	public void isSettle(String ownerId) {
	 	List<String> shopSettles = commonMapper.isSettle(ownerId);
	 	if (shopSettles.size() > 0) {
	 		throw new RuntimeException(MessageFormat.format(settleDateError,ownerId,JSONArray.toJSON(shopSettles).toString()));
		}
	}
	
}
