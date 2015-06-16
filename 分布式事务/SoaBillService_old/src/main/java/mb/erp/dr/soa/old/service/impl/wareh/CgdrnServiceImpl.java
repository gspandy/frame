package mb.erp.dr.soa.old.service.impl.wareh;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.bean.OrderSearchBean;
import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.AllocType;
import mb.erp.dr.soa.constant.O2OBillConstant.BASE_EXTRA;
import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OBillConstant.RcptMode;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.drools.utils.KieSessionFactory;
import mb.erp.dr.soa.old.dao.AdnMapper;
import mb.erp.dr.soa.old.dao.BgrMapper;
import mb.erp.dr.soa.old.dao.GdnMapper;
import mb.erp.dr.soa.old.dao.GrnMapper;
import mb.erp.dr.soa.old.dao.IdtMapper;
import mb.erp.dr.soa.old.dao.ScnMapper;
import mb.erp.dr.soa.old.dao.TbnMapper;
import mb.erp.dr.soa.old.dao.WarehMapper;
import mb.erp.dr.soa.old.service.balance.BalanceService;
import mb.erp.dr.soa.old.service.bill.BillService;
import mb.erp.dr.soa.old.service.bill.CommonService;
import mb.erp.dr.soa.old.service.price.SettlementPriceRateService;
import mb.erp.dr.soa.old.service.wareh.CgdrnService;
import mb.erp.dr.soa.old.service.wareh.WarehService;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.BgrVo;
import mb.erp.dr.soa.old.vo.GdnDtlVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.old.vo.IdtVo;
import mb.erp.dr.soa.old.vo.ScnVo;
import mb.erp.dr.soa.old.vo.TbnVo;
import mb.erp.dr.soa.old.vo.WarehTranVo;
import mb.erp.dr.soa.old.vo.Warehouse;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 出入库服务
 * @author cyyu
 *
 */
@Service
public class CgdrnServiceImpl implements CgdrnService {

	private final Logger logger = LoggerFactory.getLogger(CgdrnServiceImpl.class);
	
	@Resource
	private WarehMapper warehMapper;
	@Resource
	private WarehService warehService;
    @Resource
    private BalanceService balanceService; // 资金接口
    @Resource
    private SettlementPriceRateService settlementPriceRateService;
	@Resource
	private BillService<TbnVo> tbnService;
    @Resource
    private CommonService commonService;
    @Resource
    private GdnMapper gdnMapper;
    @Resource
    private GrnMapper grnMapper;
	@Resource
	private IdtMapper idtMapper;
    @Resource
    private TbnMapper tbnMapper;
    @Resource
    private AdnMapper adnMapper;
    @Resource
    private BgrMapper bgrMapper;
    @Resource
    private ScnMapper scnMapper;
    @Resource
    private BillService<IdtVo> idtService;
    @Resource
    private BillService<GdnVo> gdnService;
    @Resource
    private BillService<GrnVo> grnService;
    @Value("${closed.account}")
    private String closedAccount;
    final static BillType BILLTYPE_TBN = BillType.TBN;
    final static BillType BILLTYPE_AAD = BillType.AAD;
    final static BillType BILLTYPE_SBG = BillType.SBG;
    final static BillType BILLTYPE_SSC = BillType.SSC;
    /**
	 * 单据出库
	 * @ 
	 */
	public MsgVo billGdn(GdnVo vo, BillType billType)  {
		MsgVo msg = validatePrimaryParam(vo,O2OMsgConstant.BIZTYPE.BILLGDN);
		boolean flg = commonService.isClosedAccount(vo.getUnitId(), vo.getGdnNum(), BillType.GDN);
		if (!flg) {
			//出库成本核算
			updateCostGroup(vo);
//			vo.setCostChg(O2OBillConstant.TORF.FALSE); //成本核算标志
//			vo.setCost(0.0); //成本
//			//更新出库单 成本核算标志位F 成本为0
//			gdnMapper.updateCost(vo);
//			//更新出库单明细 单位成本为0
//			gdnMapper.updateUnitCost(vo);
			//调用相应订单类型的已发货接口
			searchOrderProgress(vo, billType);
			Double amount = 0d;
			if (BillType.AAD.equals(billType)) {
				// 查询计划配货单明细，用于规则里释放冻结金额
				AdnVo adnSearchBean = new AdnVo();
				adnSearchBean.setVenderId(vo.getSrcUnitId());
				adnSearchBean.setAdnNum(vo.getSrcDocNum());
				AdnVo adnResult = adnMapper.select(adnSearchBean);
				amount = adnResult.getAdmVal();
			}
			logger.warn("--------------------------CgdrnServiceImpl->adn_num:"+vo.getSrcDocNum()+" , amount:"+SoaBillUtils.formatPricePrecisionTwo(amount));
			//调用出库单出库规则 //TODO
			if(billType.equals(BillType.SBG)){
				vo.setControlStatus(commonService.selectDirectoryCode("GDN","TD_OUT"));//"0000100"
			} else {
				vo.setControlStatus(commonService.selectDirectoryCode("GDN","OUT"));//  0101111
			}
//			vo.setControlStatus("0100111"); // TODO "0100111"为了测试专项资金，需调用正数扣减余额
			KieSession kieSession = KieSessionFactory.getKieSession("gdn-rules-out");
			kieSession.insert(vo);
			kieSession.insert(warehService);
			kieSession.insert(warehMapper);
			kieSession.insert(settlementPriceRateService);
			kieSession.insert(balanceService);
			kieSession.insert(gdnService);
			kieSession.insert(new SoaBillUtils());
			kieSession.insert(SoaBillUtils.formatPricePrecisionTwo(amount));
			kieSession.fireAllRules();
			kieSession.dispose();
			//更新总金额
			gdnMapper.updateTtlVal(vo);
			//更新单价和折率
			for (GdnDtlVo dtlVo : vo.getGdnDtlVos()){
				gdnMapper.updatePriceAndDiscRate(dtlVo);
			}
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			Calendar cal = Calendar.getInstance();
			cal.setTime(vo.getDocDate());
			msg.setMsg(MessageFormat.format(closedAccount, cal.get(Calendar.MONTH)+1, "入库"));
		}
		return msg;
	}

	/**
	 * 单据入库
	 * @ 
	 */
	public MsgVo billGrn(GrnVo vo, BillType billType)  {
		MsgVo msg = validatePrimaryParam(vo,O2OMsgConstant.BIZTYPE.BILLGRN);
		//判断账期是否已封账
		boolean flg = commonService.isClosedAccount(vo.getUnitId(), vo.getGrnNum(), BillType.GRN);
		if (!flg) {
//			GrnVo grnVo4Update = new GrnVo();// 修改参数内vo的属性，会影响后面流程的执行
//			grnVo4Update.setUnitId(vo.getUnitId());
//			grnVo4Update.setGrnNum(vo.getGrnNum());
//			if (BILLTYPE_AAD.equals(billType)) {
//				grnVo4Update.setRemark("IDT"); // 用remark代替billtype，是因为mybatis判断枚举类型有偏差
//				grnVo4Update.setCostChg(O2OBillConstant.TORF.TRUE); //成本核算标志
//				//入库单明细 单位成本 设置取数方式(更新入库单明细 单位成本)
//				grnMapper.updateGrnUnitCost(grnVo4Update);
//				//根据明细修改总单成本  和 成本核算标志
//				grnMapper.updateGrnCost(grnVo4Update);
//			}else if (BILLTYPE_TBN.equals(billType)) {
//				grnVo4Update.setRemark("TBN");
//				grnVo4Update.setCostChg(O2OBillConstant.TORF.FALSE); //成本核算标志
//				grnVo4Update.setCost(0.0); //成本
//				//更新入库单成本核算标志位F 成本为0
//				grnMapper.updateGrnCost(grnVo4Update);
//				//更新入库单明细 单位成本为0
//				grnMapper.updateGrnUnitCost(grnVo4Update); 
//			}
			//入库成本核算
			updateCostGroup(vo);
			//调用相应订单类型的已收货接口
			searchOrderProgress(vo, billType);
			//调用入库单入库规则
			if(billType.equals(BillType.SSC)){
				vo.setControlStatus(commonService.selectDirectoryCode("GRN","TD_IN"));//"00100"
			} else {
				vo.setControlStatus(commonService.selectDirectoryCode("GRN","IN"));//"01111"
			}
			KieSession kieSession = KieSessionFactory.getKieSession("grn-rules-in");
	    	kieSession.insert(warehService);
	    	kieSession.insert(balanceService);
			kieSession.insert(grnService);
			kieSession.insert(warehMapper);
	    	kieSession.insert(vo);
			kieSession.fireAllRules();
			kieSession.dispose();
		}else {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			Calendar cal = Calendar.getInstance();
			cal.setTime(vo.getDocDate());
			msg.setMsg(MessageFormat.format(closedAccount, cal.get(Calendar.MONTH)+1, "入库"));
		}
		return msg;
	}

	public MsgVo billGddl(GdnVo vo, BillType billType)  {
		MsgVo msg = validatePrimaryParam(vo,O2OMsgConstant.BIZTYPE.BILLGDDL);
		// 根据单据类型，调用不同的方法
		if (BILLTYPE_TBN.equals(billType)) {
			// do ...
		}else if(BILLTYPE_AAD.equals(billType)){
			// do ....
		}
		OrderSearchBean searchBean = new OrderSearchBean();
		searchBean.setUnitId(vo.getUnitId());
		searchBean.setDocType(BillType.GDN);
		searchBean.setDocNum(vo.getGdnNum());
		WarehTranVo co = warehMapper.searchWarehTranInfo(searchBean); 
		// 查询是否扣减过库存
		if (co != null  && co.getTranQty()<0) {
			Warehouse warehouse = warehMapper.searchWarehouseInfo(vo.getWarehId());
			if (warehouse == null) {
				msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
				msg.setMsg("货位库存不存在，仓库编码:"+vo.getWarehId());
				throw new RuntimeException(msg.getMsg());
			}
			String flag = warehouse.getLocAdopted(); // 是否启用货位
			if (!O2OBillConstant.TORF.TRUE.equals(flag)) {
				// 调用货位库存增加接口
				warehService.increaseLocQtyByGDN(vo);
			}
			// 调用仓库库存增加接口
			warehService.increaseQtyByGDN(vo);
		}
		// 出库冲单规则
//		KieSession kieSession = KieSessionFactory.getKieSession("gddl-rules");
//		kieSession.insert(warehService);
//		kieSession.insert(vo);
//		kieSession.fireAllRules();
//		kieSession.dispose();
		// 根据单据类型，调用不同的规则
		if (BillType.TBN.equals(billType)) {
			// do ....
		}else if(BillType.IDT.equals(billType)){
			// do ....
		}
		return msg;
	}
	
	public MsgVo billGdrv(GrnVo vo, BillType billType)  {
		MsgVo msg = validatePrimaryParam(vo,O2OMsgConstant.BIZTYPE.BILLGDRV);
		// 根据单据类型，调用不同的方法
		if (BILLTYPE_TBN.equals(billType)) {
			// do ...
		}else if(BILLTYPE_AAD.equals(billType)){
			// do ....
		}
		OrderSearchBean searchBean = new OrderSearchBean();
		searchBean.setUnitId(vo.getUnitId());
		searchBean.setDocType(BillType.GRN);
		searchBean.setDocNum(vo.getGrnNum());
		WarehTranVo co = warehMapper.searchWarehTranInfo(searchBean); 
		// 查询是否增加过库存
		if (co != null && co.getTranQty()>0) {
			Warehouse warehouse = warehMapper.searchWarehouseInfo(vo.getWarehId());
			if (warehouse == null) {
				msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
				msg.setMsg("货位库存不存在，仓库编码:"+vo.getWarehId());
				throw new RuntimeException(msg.getMsg());
			}
			String flag = warehouse.getLocAdopted(); // 是否启用货位
			if (!O2OBillConstant.TORF.TRUE.equals(flag)) {
				// 调用货位库存扣减接口
				warehService.reduceLocQtyByGRN(vo);
			}
			// 调用仓库库存扣减接口
			warehService.reduceQtyByGRN(vo);
		}
		// 入库冲单规则
//		KieSession kieSession = KieSessionFactory.getKieSession("gdrv-rules");
//		kieSession.insert(warehService);
//		kieSession.insert(vo);
//		kieSession.fireAllRules();
//		kieSession.dispose();
		return msg;
	}
	
	public void searchOrderProgress(Object vo, BillType billType) {
		if (vo instanceof GdnVo) {
			if (BILLTYPE_AAD.equals(billType)) {
				if (AllocType.XXUZ.equals(((GdnVo) vo).getAllocType())) { // 上层计划配货单
					AdnVo adnVo = new AdnVo();
					adnVo.setAdnNum(((GdnVo) vo).getSrcDocNum());
					adnVo.setVenderId(((GdnVo) vo).getSrcUnitId());
					//更新计划配货单的进度为已发货DD 发货数量为出库单出库数量，发货金额为出库单出库金额
					adnMapper.updateUpAdnByGdn(adnVo);
					//更新计划配货单明细上的发货数量为出库单出库数量
					adnMapper.updateUpAdnDtlByGdn(adnVo);
				}else {
					IdtVo idtVo = idtMapper.selectIdtByGdn((GdnVo)vo);
					idtService.orderDD(idtVo);
				}
			}else if (BILLTYPE_TBN.equals(billType)) {
				TbnVo tbnVo = tbnMapper.selectTbnByGdn((GdnVo)vo);
				tbnService.orderDD(tbnVo);
			}else if (BILLTYPE_SBG.equals(billType)) {
				//更新退货申请单和明细的发货数量
				updateBgrByGdn((GdnVo)vo);
			}
			gdnService.orderDD((GdnVo) vo);
		}else if (vo instanceof GrnVo) {
			if (BILLTYPE_AAD.equals(billType)) {
				if (AllocType.XXUZ.equals(((GrnVo) vo).getAllocType())) {
					AdnVo adnVo = new AdnVo();
					adnVo.setAdnNum(((GrnVo) vo).getSrcDocNum());
					adnVo.setVenderId(((GrnVo) vo).getSrcUnitId());
					//更新计划配货单进度为已收货RD,发货数量为入库单入库数量，发货金额为入库单入库金额
					adnMapper.updateUpAdnByGrn(adnVo);
					//更新计划配货单明细上的发货数量为入库单入库数量
					adnMapper.updateUpAdnDtlByGrn(adnVo);
				}else {
					IdtVo idtVo = idtMapper.selectIdtByGrn((GrnVo)vo);
					idtService.orderRD(idtVo);
				}
			}else if (BILLTYPE_TBN.equals(billType)) {
				TbnVo tbnVo = tbnMapper.selectTbnByGrn((GrnVo)vo);
				tbnService.orderRD(tbnVo);
			}else if (BILLTYPE_SSC.equals(billType)) {
				//更新退货申请单和明细的入库数量
				updateBgrByGrn((GrnVo)vo);
				//更新退货单和明细收货数量
				updateScnByGrn((GrnVo)vo);
			}
			grnService.orderRD((GrnVo) vo);
		}
	}
	
	/**
	 * 更新退货申请单的收货数量
	 * @param vo
	 */
	private void updateBgrByGrn(GrnVo grnvo) {
		BgrVo bgrVo = new BgrVo();
		bgrVo.setVendeeId(grnvo.getDispUnitId());
		bgrVo.setBgrNum((String) grnvo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_NUM));
		
		//System.out.println("updateBgrByGrn---"+grnvo.getDispUnitId()+" "+bgrVo.getBgrNum());
		//更新退货申请单收货数量、金额、时间
		bgrMapper.updateBgrByGrn(bgrVo);
		//更新退货申请单明细收货数量
		bgrMapper.updateBgrDtlByGrn(bgrVo);
	}

	/**
	 * 更新退货单和明细的收货数量
	 * @param vo
	 */
	private void updateScnByGrn(GrnVo grnVo) {
		ScnVo scnVo = new ScnVo();
		scnVo.setVendeeId(grnVo.getDispUnitId());
		scnVo.setScnNum(grnVo.getSrcDocNum());
		scnVo.setVenderId(grnVo.getUnitId());
		//更新退货单收货数量、金额 、时间
		scnMapper.updateScnByGrn(scnVo);
		//更新退货单明细收货数量
		scnMapper.updateScnDtlByGrn(scnVo);
	}

	/**
	 * 更新退货申请单和明细的发货数量
	 * @param gdnVo
	 */
	private void updateBgrByGdn(GdnVo gdnVo) {
		BgrVo bgrVo = new BgrVo();
		bgrVo.setVendeeId(gdnVo.getUnitId());
		bgrVo.setBgrNum(gdnVo.getSrcDocNum());
		//更新退货申请单发货数量、金额、时间
		bgrMapper.updateBgrByGdn(bgrVo);
		//更新退货申请单明细发货数量
		bgrMapper.updateBgrDtlByGdn(bgrVo);
	}

	/**
	 * 验证参数主键是否为空
	 * @param vo
	 * @return
	 */
	private MsgVo validatePrimaryParam(Object vo,String bizType){
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",bizType,null);
		String unitId=null,docNum =null;
		if (vo instanceof GdnVo) {
			unitId = ((GdnVo) vo).getUnitId();
			docNum = ((GdnVo) vo).getGdnNum();
		}else if (vo instanceof GrnVo) {
			unitId = ((GrnVo) vo).getUnitId();
			docNum = ((GrnVo) vo).getGrnNum();
		}
		if (SoaBillUtils.isBlank(unitId) 
				|| SoaBillUtils.isBlank(docNum)) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("处理出入库服务时参数异常，组织编码："+unitId+"，编号："+docNum);
			logger.error(msg.getMsg());
		}
		return msg;
	}
	
	/**
	 * 
	 * 出入库成本核算
	 * @param vo
	 */
	private void updateCostGroup(Object vo) {
		
		if(vo instanceof GdnVo) {
			GdnVo gdnVo = (GdnVo) vo;
			boolean flag =  isExistsCostGroup(gdnVo.getUnitId(),gdnVo.getGdnNum(),BillType.GDN);
			updateCostGroupByGdn(gdnVo.getUnitId(),gdnVo.getGdnNum(),flag);
		}
		if(vo instanceof GrnVo) {
			GrnVo grnVo = (GrnVo) vo;
			boolean flag =  isExistsCostGroup(grnVo.getUnitId(),grnVo.getGrnNum(),BillType.GRN);
			updateCostGroupByGrn(grnVo.getUnitId(),grnVo.getGrnNum(),flag);
		}
	}

	/**
	 * 更新出库单成本
	 * @param unitId
	 * @param gdnNum
	 * @param flag true:成本核算标志T false:成本核算标志F
	 */
	private void updateCostGroupByGdn(String unitId, String gdnNum, boolean flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("unitId", unitId);
		map.put("gdnNum", gdnNum);
		map.put("isCost", flag?1:0);
		//更新出库单明细成本
		gdnMapper.updateUnitCost(map);
		//更新出库单成本
		gdnMapper.updateCost(map);
		
	}
	
	/**
	 * 更新入库单成本
	 * @param unitId
	 * @param grnNum
	 * @param flag true:成本核算标志T false:成本核算标志F
	 */
	private void updateCostGroupByGrn(String unitId, String gdnNum, boolean flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("unitId", unitId);
		map.put("grnNum", gdnNum);
		map.put("isCost", flag?1:0);
		//更新入库单明细成本
		grnMapper.updateUnitCost(map);
		//更新入库单成本
		grnMapper.updateCost(map);
	}
	
	/**
	 * 查看成本组是否活动
	 * @param unitId
	 * @param docNum
	 * @param billType
	 */
	private boolean isExistsCostGroup(String unitId, String docNum, BillType billType) {
		//判断出库单成本组
		if (BillType.GDN.equals(billType)) {
			Integer mode = checkCostGrpByGdn(unitId,docNum);
			if (mode != null && mode > 0) {
				return true;
			} else {
				logger.warn(MessageFormat.format("出库单{0}成本组未设置，不做成本计算", docNum));
				return false;
			}
		//判断入库单成本组
		} else if (BillType.GRN.equals(billType)) {
			Integer mode = checkCostGrpByGrn(unitId,docNum);
			if (mode != null && mode > 0) {
				return true;
			} else {
				String rcptMode = selectRcptModeByGrn(unitId,docNum);
				//入库方式在AGOR、PURC、COMM、BUBK之中返回true
				if (judgeRcptMode(rcptMode)) {
					return true;
				}
				logger.warn(MessageFormat.format("入库单{0}成本组未设置，不做成本计算", docNum));
				return false;
			}
		} else {
			throw new RuntimeException(MessageFormat.format("出入库单成本计算传入的单据类型错误,当前单据类型{0}",billType.name()));
		}
	}
	
	/**
	 * 判断入库方式
	 * @param rcptMode
	 * @return boolean
	 */
	private boolean judgeRcptMode(String rcptMode) {
		if (SoaBillUtils.isBlank(rcptMode)) {
			return false;
		} else if (rcptMode.equals(RcptMode.AGOR.name()) 
				|| rcptMode.equals(RcptMode.PURC.name())
				|| rcptMode.equals(RcptMode.COMM.name())
				|| rcptMode.equals(RcptMode.BUBK.name())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查询出库单成本组
	 * @param unitId
	 * @param docNum
	 * @return Integer
	 */
	private Integer checkCostGrpByGdn(String unitId, String docNum) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("unitId", unitId);
		map.put("gdnNum", docNum);
		return gdnMapper.checkCostGrpByGdn(map);
	}
	
	/**
	 * 查询入库单成本组
	 * @param unitId
	 * @param docNum
	 * @return Integer
	 */
	private Integer checkCostGrpByGrn(String unitId, String docNum) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("unitId", unitId);
		map.put("grnNum", docNum);
		return grnMapper.checkCostGrpByGrn(map);
	}
	
	/**
	 * 查询入库方式
	 * @param unitId
	 * @param docNum
	 * @return String
	 */
	private String selectRcptModeByGrn(String unitId,String docNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("unitId", unitId);
		map.put("grnNum", docNum);
		return grnMapper.selectRcptMode(map);
	}

}
