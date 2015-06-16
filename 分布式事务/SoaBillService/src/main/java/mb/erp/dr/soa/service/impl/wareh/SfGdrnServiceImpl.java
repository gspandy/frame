package mb.erp.dr.soa.service.impl.wareh;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.bean.EnMarginProdNumBean;
import mb.erp.dr.soa.constant.O2OBillConstant.AllocType;
import mb.erp.dr.soa.constant.O2OBillConstant.CalType;
import mb.erp.dr.soa.constant.O2OBillConstant.DGN_PROGRESS;
import mb.erp.dr.soa.constant.O2OBillConstant.GDN_PROGRESS;
import mb.erp.dr.soa.constant.O2OBillConstant.GRN_PROGRESS;
import mb.erp.dr.soa.constant.O2OBillConstant.NEW_PROGRESS;
import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;
import mb.erp.dr.soa.constant.O2OBillConstant.TORF;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.dao.RpAgentUnitCostMapper;
import mb.erp.dr.soa.dao.SfDgnMapper;
import mb.erp.dr.soa.dao.SfGdnMapper;
import mb.erp.dr.soa.dao.SfGrnMapper;
import mb.erp.dr.soa.dao.SfIdtMapper;
import mb.erp.dr.soa.dao.SfRvdMapper;
import mb.erp.dr.soa.dao.SfWarehTranMapper;
import mb.erp.dr.soa.drools.utils.KieSessionFactory;
import mb.erp.dr.soa.service.balance.NewBalanceService;
import mb.erp.dr.soa.service.bill.NewBillService;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.service.price.NewCostPriceRateService;
import mb.erp.dr.soa.service.price.NewSettlementPriceRateService;
import mb.erp.dr.soa.service.wareh.NewWarehService;
import mb.erp.dr.soa.service.wareh.SfGdrnService;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.DrTbnVo;
import mb.erp.dr.soa.vo.SfDgnVo;
import mb.erp.dr.soa.vo.SfGdnVo;
import mb.erp.dr.soa.vo.SfGrnVo;
import mb.erp.dr.soa.vo.SfIdtVo;
import mb.erp.dr.soa.vo.SfRvdVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 出入库服务
 * 
 * @author cyyu
 * 
 */
@Service
public class SfGdrnServiceImpl implements SfGdrnService {

	private final Logger logger = LoggerFactory
			.getLogger(SfGdrnServiceImpl.class);
	@Resource
	private NewWarehService newWarehService;
	@Resource
	private NewBalanceService newBalanceService; // 资金接口
	@Resource
	private NewSettlementPriceRateService newSettlementPriceRateService;
	@Resource
	private NewCostPriceRateService newCostPriceRateService;
	@Resource
	private SfDgnMapper sfDgnMapper;
	@Resource
	private SfGdnMapper sfGdnMapper;
	@Resource
	private SfGrnMapper sfGrnMapper;
	@Resource
	private SfIdtMapper sfIdtMapper;
	@Resource
	private SfRvdMapper sfRvdMapper;
	@Resource
	private SfWarehTranMapper sfWarehTranMapper;
	@Resource
	private RpAgentUnitCostMapper rpAgentUnitCostMapper;
	@Resource
	private NewBillService<SfIdtVo> sfIdtService;
	@Resource
	private NewBillService<DrTbnVo> sfTbnService;
	@Resource
	private NewBillService<SfGdnVo> sfGdnService;
	@Resource
	private NewBillService<SfGrnVo> sfGrnService;
	@Resource
	private NewBillService<SfRvdVo> sfRvdService;
	@Resource
	private NewERPCommonService newERPCommonService;
	@Value("${closed.account}")
	private String closedAccount;

	/**
	 * 单据出库 @
	 */
	public MsgVo billGdn(SfGdnVo vo, String newBillType) {
		MsgVo msg = validatePrimaryParam(vo, O2OMsgConstant.BIZTYPE.BILLGDN);
		// 检查会计期间
		newERPCommonService.checkFiFslcMonth(vo.getBfOrgWarehId(),
				vo.getDelivMode());
		
		if(!calculationByGdn(vo.getId())){
			throw new RuntimeException("出库单成本核算失败,单据号:"+vo.getCode());
		}

//		// 出库单成本核算
//		vo.setCostChg(O2OBillConstant.TORF.FALSE); // 成本核算标志
//		vo.setCost(0.0); // 成本
//		vo.setEfficient(O2OBillConstant.TORF.TRUE);
//		// 更新出库单 成本核算标志位F 成本为0
//		sfGdnMapper.updateSfGdn(vo);
//		// 更新出库单明细 单位成本为0
//		sfGdnMapper.updateUnitCost(vo);

		// 调用相应订单类型的已发货接口
		searchOrderProgress(vo, newBillType);

		// 调用出库单出库规则
		KieSession kieSession = KieSessionFactory
				.getKieSession("sfGdn-rules-out");
		vo.setControlStatus(newERPCommonService.selectDirectoryCode("SFGDN","OUT"));
		kieSession.insert(vo);
		kieSession.insert(newWarehService);
		kieSession.insert(newCostPriceRateService);
		kieSession.insert(newSettlementPriceRateService);
		kieSession.insert(sfDgnMapper);
		kieSession.insert(sfWarehTranMapper);
		kieSession.insert(new SoaBillUtils());
		kieSession.fireAllRules();
		kieSession.dispose();
		return msg;
	}

	/**
	 * 单据入库
	 * 
	 */
	public MsgVo billGrn(SfGrnVo vo, String newBillType) {
		MsgVo msg = validatePrimaryParam(vo, O2OMsgConstant.BIZTYPE.BILLGRN);
		// 检查会计期间
		newERPCommonService.checkFiFslcMonth(vo.getWarehId(), vo.getRcptMode());
//		if (NewBillType.IDT.name().equals(newBillType)) {
//			vo.setRemark("IDT"); // 用remark代替billtype，是因为mybatis判断枚举类型有偏差
//			vo.setCostChg(O2OBillConstant.TORF.TRUE); // 成本核算标志
//			// 入库单明细 单位成本 设置取数方式(更新入库单明细 单位成本)
//			sfGrnMapper.updateGrnUnitCost(vo);
//			// 根据明细修改总单成本 和 成本核算标志
//			sfGrnMapper.updateGrnCost(vo);
//		} else if (NewBillType.TBN.name().equals(newBillType)) {
//			vo.setRemark("TBN");
//			vo.setCostChg(O2OBillConstant.TORF.FALSE); // 成本核算标志
//			vo.setCost(0.0); // 成本
//			// 更新入库单成本核算标志位F 成本为0
//			sfGrnMapper.updateGrnCost(vo);
//			// 更新入库单明细 单位成本为0
//			sfGrnMapper.updateGrnUnitCost(vo);
//		}
		if(!calculationByGrn(vo.getId())){
			throw new RuntimeException("成本核算失败,入库单单据号:"+vo.getGrnNum());
		}

		// 记录单位成本
		// SfGrnVo sfGrnVo = sfGrnMapper.selectGrnById(vo.getId());
		// if (sfGrnVo == null) throw new
		// RuntimeException("新ERP单据入库单不存在:"+vo.getId());
		// //是否代理商订货入库
		// if (!RcptMode.AGOF.toString().equals(sfGrnVo.getRcptMode()) &&
		// !RcptMode.AGOR.toString().equals(sfGrnVo.getRcptMode()))
		// throw new
		// RuntimeException("新ERP单据入库异常:此入库单对应的入库方式为代理商订货入库"+((SfGrnVo)
		// vo).getId());
		// // 更新单位成本记录
		// int upt = rpAgentUnitCostMapper.updateUnitCost(vo.getId());
		// // 更新单位成本记录
		// int ist = rpAgentUnitCostMapper.insertUnitCost(vo.getId());

		// 调用相应订单类型的已收货接口
		searchOrderProgress(vo, newBillType);

		// 调用入库单入库规则
		KieSession kieSession = KieSessionFactory
				.getKieSession("sfGrn-rules-in");
		// test
		if (AllocType.XXUZ.equals(vo.getAllocType())) {
			vo.setSfDgnCode(vo.getSfDgnCodeUPZ());
		}
		vo.setControlStatus(newERPCommonService.selectDirectoryCode("SFGDN","IN"));
		kieSession.insert(newWarehService);
		kieSession.insert(sfDgnMapper);
		kieSession.insert(sfWarehTranMapper);
		// kieSession.insert(newBalanceService);
		kieSession.insert(vo);
		kieSession.fireAllRules();
		kieSession.dispose();
		return msg;
	}

	public void searchOrderProgress(Object vo, String newBillType) {
		if (vo instanceof SfGdnVo) {
			if (NewBillType.IDT.name().equals(newBillType)) {
				if (AllocType.XXUZ.equals(((SfGdnVo) vo).getAllocType())) {
					SfDgnVo sfDgnVo = new SfDgnVo();
					sfDgnVo.setCode(((SfGdnVo) vo).getSrcDocCode());
					sfDgnVo.setDocState(DGN_PROGRESS.HAVE_OUT_WAREH);
					sfDgnVo.setProgress(NEW_PROGRESS.SC);
					sfDgnMapper.updateSfDgn(sfDgnVo);
				}else {
					SfIdtVo sfIdtVo = new SfIdtVo();
					sfIdtVo.setCode(((SfGdnVo) vo).getOrigDocCode());
					sfIdtVo = sfIdtMapper.selectSfIdt(sfIdtVo);
					sfIdtService.orderDD(sfIdtVo.getId(),null,((SfGdnVo) vo).getId(),null);
				}
			}else if (NewBillType.TBN.name().equals(newBillType)) {
				sfTbnService.orderDD(null,((SfGdnVo) vo).getOrigDocCode(),((SfGdnVo) vo).getId(),null);
			}
			sfGdnService.orderDD(((SfGdnVo) vo).getId(),((SfGdnVo) vo).getCode(),null,null);
		}else if (vo instanceof SfGrnVo) {
			SfRvdVo sfRvdVo = sfRvdMapper.selectSfRvdByCode(((SfGrnVo) vo).getSfRvdCode());
			if (NewBillType.IDT.name().equals(newBillType)) {
				if (!AllocType.XXUZ.equals(((SfGrnVo) vo).getAllocType())) {
					sfIdtService.orderRD(null, sfRvdVo.getOriginDocNum(), ((SfGrnVo) vo).getId(),null);
				}
			}else if (NewBillType.TBN.name().equals(newBillType)) {
				sfTbnService.orderRD(null,sfRvdVo.getOriginDocNum(),((SfGrnVo) vo).getId(),null);
			}
			sfRvdService.orderRD(null, ((SfGrnVo) vo).getSfRvdCode(), ((SfGrnVo) vo).getId(),null);
			sfGrnService.orderRD(((SfGrnVo) vo).getId(),null,null,null);
		}
	}

	/**
	 * 验证参数主键是否为空
	 * 
	 * @param vo
	 * @return
	 */
	private MsgVo validatePrimaryParam(Object vo, String bizType) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS, "", bizType,
				null);
		Long bfOrgUnitId = null, id = null;
		if (vo instanceof SfGdnVo) {
			bfOrgUnitId = ((SfGdnVo) vo).getBfOrgUnitId();
			id = ((SfGdnVo) vo).getId();
		} else if (vo instanceof SfGrnVo) {
			bfOrgUnitId = ((SfGrnVo) vo).getUnitId();
			id = ((SfGrnVo) vo).getId();
		}
		if (null == bfOrgUnitId || null == id) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP处理出入库服务时参数异常，组织编码：" + bfOrgUnitId + "，编号：" + id);
			logger.error(msg.getMsg());
		}
		return msg;
	}

	/**
	 * 出库成本计算
	 * 
	 * @param id
	 */
	private boolean calculationByGdn(Long id) {
		// 成本组是否活动
		boolean result = checkCostGrpByGdn(id);
		if (!result) {
			updateCostChgByGDN(TORF.FALSE, id);
			return true;
		}
		// 获取成本核算方式
		Integer calType = sfGdnMapper.getCalType(id);

		switch (calType) {
		// 月末加权
		case CalType.CAL_TYPE_0:
			gdnCalType_0(id);
			break;
		// 末次进价法
		case CalType.CAL_TYPE_1:
			gdnCalType_1(id);
			break;
		// 移动加权平均
		case CalType.CAL_TYPE_2:
			gdnCalType_2(id);
			break;
		default:
			updateCostChgByGDN(TORF.FALSE, id);
			break;
		}
		return true;
	}
	
	
	

	/**
	 * 月末加权方式核算成本
	 * 
	 * @param id
	 */
	private void gdnCalType_0(Long id) {
		// 判断出库仓库的成本组对应的出库方式是否参与成本核算并且是月末加权平均法核算
		boolean result = isCostAcountByGdn(id, CalType.CAL_TYPE_0);
		logger.warn("gdnCalType_0-------月末加权法---是否参与成本核算"+result);
		if (result) {
			// 更新核算标记为T
			updateCostChgByGDN(TORF.TRUE, id);
			// 单价精度
			int pricePrecision = getPricePrecision();
			int costPrecision = getCostPrecision();
			// 更新详细与总单成本
			updateDetailUnitCostByGDN(id, pricePrecision);
			updateCostByGDN(id, pricePrecision, costPrecision);
		} else {
			updateCostChgByGDN(TORF.FALSE, id);
		}
	}

	/**
	 * 末次进价方式核算成本
	 * 
	 * @param id
	 */
	private void gdnCalType_1(Long id) {
		// 判断出库仓库的成本组对应的出库方式是否参与成本核算并且是末次进价法
		boolean result = isCostAcountByGdn(id, CalType.CAL_TYPE_1);
		logger.warn("gdnCalType_0-------末次进价方式---是否参与成本核算"+result);
		if (result) {
			// 更新核算标记为T
			updateCostChgByGDN(TORF.TRUE, id);
			// 单价精度
			int pricePrecision = getPricePrecision();
			int costPrecision = getCostPrecision();
			// 更新详细与总单成本
			updateDetailUnitCostByGDN(id, pricePrecision);
			updateCostByGDN(id, pricePrecision, costPrecision);

			SfGdnVo gdnVo = sfGdnMapper.selectGdnById(id);
			if (gdnVo != null && gdnVo.getDocState() != null
					&& gdnVo.getDocState() != GDN_PROGRESS.KILL) {
				// 更新FiGrpCost
				sfGdnMapper.updateFiGrpCostByGdn(id);
				sfGdnMapper.insertFiGrpCostByGdn(id);
			}
		} else {
			gdnNotCalcCost(id);
		}
	}

	/**
	 * 移动加 权平均
	 * @param id
	 */
	private void gdnCalType_2(Long id) {
		// 判断出库仓库的成本组对应的出库方式是否参与成本核算并且是移动加权平均法
		boolean result = isCostAcountByGdn(id, CalType.CAL_TYPE_2);
		logger.warn("gdnCalType_0-------移动加 权平均---是否参与成本核算"+result);
		if(result){
			// 更新核算标记为T
			updateCostChgByGDN(TORF.TRUE, id);
			Long costGrpId = sfGdnMapper.getCostGrpIdByGdn(id);
			// 单价精度
			int pricePrecision = getPricePrecision();
			int costPrecision = getCostPrecision();
			// 更新详细与总单成本
			updateDetailUnitCostByGDN(id, pricePrecision);
			updateCostByGDN(id, pricePrecision, costPrecision);
			//添加库存成本 一切都为0
			sfGdnMapper.insertFiGrpCostInitByGdn(id);
			
			//核算成本  只修改成本
//			margin(id,CalType.CAL_TYPE_2,true);
			//更新数量及移动成本
//			sfGdnMapper.updateGrpCostStockByGdn(id);
			updateGrpCostStockByGdn(id,costGrpId);
		} else {
			gdnNotCalcCost(id);
		}
	}
	
	/**
	 * 入库成本计算
	 * 
	 * @param id
	 */
	private boolean calculationByGrn(Long id) {
		// 成本组是否活动
		boolean result = checkCostGrpByGrn(id);
		if (!result) {
			updateCostChgByGRN(TORF.FALSE, id);
			return true;
		}
		// 获取成本核算方式
		Integer calType = sfGrnMapper.getCalType(id);

		switch (calType) {
		// 月末加权
		case CalType.CAL_TYPE_0:
			grnCalType_0(id);
			break;
		// 末次进价法
		case CalType.CAL_TYPE_1:
			grnCalType_1(id);
			break;
		// 移动加权平均
		case CalType.CAL_TYPE_2:
			grnCalType_2(id);
			break;
		default:
			updateCostChgByGRN(TORF.FALSE, id);
			break;
		}
		return true;
	}
	
	/**
	 * 月末加权下平均法
	 * @param id
	 */
	private void grnCalType_0(Long id) {
        //月末加权下平均法及参与成本核算
        boolean  result = isCostAcountByGrn(id, CalType.CAL_TYPE_0);
        logger.warn("grnCalType_0-------月末加权平均法---是否参与成本核算"+result);
        if (result) {
            //更新核算标记为T
         	updateCostChgByGRN(TORF.TRUE, id);
         	// 单价精度
         	int pricePrecision = getPricePrecision();
         	int costPrecision = getCostPrecision();
            //更新详细与总单成本
        	updateDetailUnitCostByGRN(id, pricePrecision);
			updateCostByGRN(id, pricePrecision, costPrecision);
        }
        else {
        	updateCostChgByGRN(TORF.FALSE, id);
        }
		
	}

	/**
	 * 末次进价法
	 * @param id
	 */
	private void grnCalType_1(Long id) {
		 //末次进价法及参与成本核算
        boolean  result = isCostAcountByGrn(id, CalType.CAL_TYPE_1);
        logger.warn("grnCalType_1-------末次进价法---是否参与成本核算"+result);
        if (result) {
        	 //更新核算标记为T
         	updateCostChgByGRN(TORF.TRUE, id);
         	// 单价精度
         	int pricePrecision = getPricePrecision();
         	int costPrecision = getCostPrecision();
            //更新详细与总单成本
        	updateDetailUnitCostByGRN(id, pricePrecision);
			updateCostByGRN(id, pricePrecision, costPrecision);
			
			SfGrnVo grnVo = sfGrnMapper.selectGrnById(id);
			if (grnVo != null && grnVo.getDocState() != null
					&& grnVo.getDocState() != GRN_PROGRESS.OFFSET) {
				// 更新FiGrpCost
				sfGrnMapper.updateFiGrpCostByGrn(id);
				sfGrnMapper.insertFiGrpCostByGrn(id);
			}
        } else {
        	grnNotCalcCost(id);
        }
		
	}

	/**
	 * 移动加权平均法
	 * @param id
	 */
	private void grnCalType_2(Long id) {
		 //移动加权平均法及参与成本核算
        boolean  result = isCostAcountByGrn(id, CalType.CAL_TYPE_2);
        logger.warn("grnCalType_2-------移动加权平均法---是否参与成本核算"+result);
        if(result) {
        	Long costGrpId = sfGrnMapper.getCostGrpIdByGrn(id);
        	 //更新核算标记为T
            updateCostChgByGRN(TORF.TRUE, id);
            // 单价精度
         	int pricePrecision = getPricePrecision();
         	int costPrecision = getCostPrecision();
         	 //更新详细与总单成本
        	updateDetailUnitCostByGRN(id, pricePrecision);
			updateCostByGRN(id, pricePrecision, costPrecision);
			//添加库存成本 一切都为0
			sfGrnMapper.insertFiGrpCostInitByGrn(id);
			//核算成本  只修改成本
//			margin(id,CalType.CAL_TYPE_2,false);
			//更新数量及移动成本
			//sfGrnMapper.updateGrpCostStockByGrn(id);
			updateGrpCostStockByGrn(id,costGrpId);
        }else {
        	grnNotCalcCost(id);
        }
	}

	/**
	 * 成本核算
	 * @param id  出入库单ID
	 * @param calType 核算方式
	 * @param isGdn 出入库标志位
	 */
	private void margin(Long id, int calType, boolean isGdn) {
		logger.warn("margin-----------calType:"+calType+"----isGdn:"+isGdn);
		List<EnMarginProdNumBean> list = isGdn? sfGdnMapper.getMarginProdNum(id):sfGrnMapper.getMarginProdNum(id);
		if (list == null || list.size() <= 0) {
			return;
		}
		List<EnMarginProdNumBean> finalList = new ArrayList<EnMarginProdNumBean>();
		if(calType == CalType.CAL_TYPE_0){
			StringBuilder msg = new StringBuilder();
			for (EnMarginProdNumBean bean : list) {
				msg.append(MessageFormat.format("{0},{1},{2},{3};", 
						bean.getProdNum(),String.valueOf(bean.getCostGrpId()),bean.getYearMonth(),String.valueOf(bean.getBillUnitCost())));
			}
			String tmp = msg.toString();
			
            List<EnMarginProdNumBean> gdnList = getCurrentMonthTotal(tmp,isGdn);
            List<EnMarginProdNumBean> grnList = getCurrentMonthTotal(tmp,!isGdn);
		
            judgeEnMarginProdNumList(finalList,gdnList,grnList);
            
		}
		if(calType == CalType.CAL_TYPE_2){
			finalList.addAll(list);
		}
		
		if(!SoaBillUtils.isListBlank(finalList)){
			StringBuilder msg = new StringBuilder();
			for (EnMarginProdNumBean bean : list) {
				msg.append(MessageFormat.format("{0},{1},{2},{3},{4};", 
						bean.getProdNum(),String.valueOf(bean.getQuantity()),String.valueOf(bean.getCostGrpId()),String.valueOf(bean.getCost()),String.valueOf(bean.getBillUnitCost())));
			}
			String tmp = msg.toString();
			logger.warn("margin-----------msg:"+tmp);
			updateUnitCostByGrnGdn(tmp,calType,finalList.get(0).getCostGrpId());
		}
		
	}

	/**
	 * 根据出入库进行成本核算
	 * @param tmp
	 * @param calType
	 * @param costGrpId
	 */
	private void updateUnitCostByGrnGdn(String tmp, int calType, Long costGrpId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sourceStr", tmp);
		map.put("calType", calType);
		map.put("costGrpId", costGrpId);
		sfGdnMapper.updateUnitCostByGrnGdn(map);
	}

	private void judgeEnMarginProdNumList(List<EnMarginProdNumBean> finalList,
			List<EnMarginProdNumBean> gdnList, List<EnMarginProdNumBean> grnList) {
		if(SoaBillUtils.isListBlank(gdnList) && SoaBillUtils.isListBlank(grnList)) {
			return;
		}
	    
		if(!SoaBillUtils.isListBlank(gdnList) && SoaBillUtils.isListBlank(grnList)){
			finalList.addAll(gdnList);
			return;
		}
		
		if(!SoaBillUtils.isListBlank(grnList) && SoaBillUtils.isListBlank(gdnList)){
			finalList.addAll(grnList);
			return;
		}
		
		if(!SoaBillUtils.isListBlank(gdnList) && !SoaBillUtils.isListBlank(grnList)){
			for(int i=0;i<grnList.size();i++){
				EnMarginProdNumBean bean = grnList.get(i);
				boolean flag = true;
				for(int j=0;j<gdnList.size();j++){
					EnMarginProdNumBean tempBean = gdnList.get(j);
					if(bean.getProdNum().equals(tempBean.getProdNum())
					&& bean.getCostGrpId() == tempBean.getCostGrpId()){
						gdnList.get(j).setQuantity(bean.getQuantity()+tempBean.getQuantity());	
						gdnList.get(j).setCost(bean.getCost()+tempBean.getCost());
						flag = false;
					}
				}
				if(flag) {
					finalList.add(bean);
				}
			}
			finalList.addAll(gdnList);
		}
	}

	private List<EnMarginProdNumBean> getCurrentMonthTotal(String tmp, boolean isGdn) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sourceStr", tmp);
		if (isGdn) {
			return sfGdnMapper.getCurrentMonthTotal(map);
		}
		
		return sfGrnMapper.getCurrentMonthTotal(map);
	}

	/**
	 * 不支持成本核算操作
	 * @param id
	 */
	private void grnNotCalcCost(Long id) {
		logger.warn("grnNotCalcCost---------入库单据id:"+id);
		updateCostChgByGRN(TORF.FALSE, id);
		List<Long> prodIdList = sfGrnMapper.getProdNoCost(id);
		if (prodIdList != null && prodIdList.size() > 0) {
			StringBuilder msg = new StringBuilder();
			msg.append("商品编码：");
			for(Long prodId : prodIdList) {
				String prodNum = newERPCommonService.getProdNumByProdId(prodId);
				msg.append(prodNum+" ");
			}
			throw new RuntimeException(MessageFormat.format("入库单成本计算部分商品没有成本：{0}", msg.toString()));
		} else {
			SfGrnVo grnVo = sfGrnMapper.selectGrnById(id);
			if (grnVo != null && grnVo.getDocState() != null
					&& grnVo.getDocState() != GDN_PROGRESS.KILL) {
				// 单价精度
				int pricePrecision = getPricePrecision();
				int costPrecision = getCostPrecision();
				// 更新详细与总单成本
				updateDetailUnitCostByGRN_1(id, pricePrecision);
				updateCostByGRN(id, pricePrecision, costPrecision);
			}
			//更新成本表总数量和总成本
			sfGrnMapper.updateGrpCostStockByGrn(id);
		}
	}
	
	/**
	 * 不支持成本核算操作 
	 * @param id
	 */
	private void gdnNotCalcCost(Long id) {
		logger.warn("gdnNotCalcCost---------出库单据id:"+id);
		updateCostChgByGDN(TORF.FALSE, id);
		List<Long> prodIdList = sfGdnMapper.getProdNoCost(id);
		if (prodIdList != null && prodIdList.size() > 0) {
			StringBuilder msg = new StringBuilder();
			msg.append("商品编码：");
			for(Long prodId : prodIdList) {
				String prodNum = newERPCommonService.getProdNumByProdId(prodId);
				msg.append(prodNum+" ");
			}
			throw new RuntimeException(MessageFormat.format("出库单成本计算部分商品没有成本：{0}", msg.toString()));
		} else {
			SfGdnVo gdnVo = sfGdnMapper.selectGdnById(id);
			if (gdnVo != null && gdnVo.getDocState() != null
					&& gdnVo.getDocState() != GDN_PROGRESS.KILL) {
				// 单价精度
				int pricePrecision = getPricePrecision();
				int costPrecision = getCostPrecision();
				// 更新详细与总单成本
				updateDetailUnitCostByGDN_1(id, pricePrecision);
				updateCostByGDN(id, pricePrecision, costPrecision);
			}
			//更新成本表总数量和总成本
			sfGdnMapper.updateGrpCostStockByGdn(id);
		}
	}

	/**
	 * 判断出库仓库的成本组对应的出库方式是否参与成本核算
	 * @param id
	 * @param calType
	 * @return
	 */
	private boolean isCostAcountByGdn(Long id, int calType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("calType", calType);
		Integer result = sfGdnMapper.isCostAcount(map);
		if (result != null && result == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断入库仓库的成本组对应的出库方式是否参与成本核算
	 * @param id
	 * @param calType
	 * @return
	 */
	private boolean isCostAcountByGrn(Long id, int calType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("calType", calType);
		Integer result = sfGrnMapper.isCostAcount(map);
		if (result != null && result == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 更新出库单成本核算标志位
	 * 
	 * @param cost
	 * @param id
	 */
	private void updateCostChgByGDN(String cost, Long gdnId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("costChg", cost);
		map.put("id", gdnId);
		sfGdnMapper.updateCostCHG(map);
	}

	/**
	 * 更新入库单成本核算标志位
	 * 
	 * @param cost
	 * @param id
	 */
	private void updateCostChgByGRN(String cost, Long grnId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("costChg", cost);
		map.put("id", grnId);
		sfGrnMapper.updateCostCHG(map);
		
	}
	
	/**
	 * 判断出库单的成本组是否活动
	 * 
	 * @param id
	 * @return boolean
	 */
	private boolean checkCostGrpByGdn(Long id) {
		try {
			String mode = sfGdnMapper.getOpModeByGdn(id);
			if (SoaBillUtils.isNotBlank(mode) && mode.equals("1")) {
				return true;
			} else if (SoaBillUtils.isNotBlank(mode) && mode.equals("-1")) {
				logger.warn(MessageFormat.format("出库单{0}成本组末设置，不做成本计算", id));
				return false;
			} else {
				logger.warn(MessageFormat.format("出库单{0}成本组状态不活动，无法计算成本", id));
				return false;
			}
		} catch (Exception e) {
			throw new RuntimeException(MessageFormat.format("出库单{0}成本核算发生错误",
					id));
		}

	}
	
	/**
	 * 判断入库库单的成本组是否活动
	 * 
	 * @param id
	 * @return boolean
	 */
	private boolean checkCostGrpByGrn(Long id) {
		try {
			String mode = sfGrnMapper.getOpModeByGrn(id);
			if (SoaBillUtils.isNotBlank(mode) && mode.equals("1")) {
				return true;
			} else if (SoaBillUtils.isNotBlank(mode) && mode.equals("-1")) {
				logger.warn(MessageFormat.format("入库单{0}成本组末设置，不做成本计算", id));
				return false;
			} else {
				logger.warn(MessageFormat.format("入库单{0}成本组状态不活动，无法计算成本", id));
				return false;
			}
		} catch (Exception e) {
			throw new RuntimeException(MessageFormat.format("入库单{0}成本核算发生错误",
					id));
		}

	}


	/**
	 * 获取单价精度
	 * 
	 * @return int
	 */
	private int getPricePrecision() {
		String value = newERPCommonService
				.getMainSysParamValue("PRICE_PRECISION");
		if (SoaBillUtils.isBlank(value)) {
			return 2;
		}
		return Integer.valueOf(value);
	}

	/**
	 * 获取成本精度
	 * 
	 * @return int
	 */
	private int getCostPrecision() {
		String value = newERPCommonService
				.getMainSysParamValue("COST_PRECISION");
		if (SoaBillUtils.isBlank(value)) {
			return 2;
		}
		return Integer.valueOf(value);
	}

	/**
	 * 更新出库单明细成本
	 * 
	 * @param id
	 * @param pricePrecision
	 */
	private void updateDetailUnitCostByGDN(Long id, int pricePrecision) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pricePrecision", pricePrecision);
		sfGdnMapper.updateDetailUnitCost(map);
	}
	
	/**
	 * 更新入库单明细成本
	 * 
	 * @param id
	 * @param pricePrecision
	 */
	private void updateDetailUnitCostByGRN(Long id, int pricePrecision) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pricePrecision", pricePrecision);
		sfGrnMapper.updateDetailUnitCost(map);
	}
	
	/**
	 * 更新出库单明细成本(成本组获取)
	 * 
	 * @param id
	 * @param pricePrecision
	 */
	private void updateDetailUnitCostByGDN_1(Long id, int pricePrecision) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pricePrecision", pricePrecision);
		sfGdnMapper.updateDetailUnitCostByGrpCost(map);
	}
	
	/**
	 * 更新入库单明细成本(成本组获取)
	 * 
	 * @param id
	 * @param pricePrecision
	 */
	private void updateDetailUnitCostByGRN_1(Long id, int pricePrecision) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pricePrecision", pricePrecision);
		sfGrnMapper.updateDetailUnitCostByGrpCost(map);
	}

	/**
	 * 更新出库单成本
	 * 
	 * @param id
	 * @param pricePrecision
	 * @param costPrecision
	 */
	private void updateCostByGDN(Long id, int pricePrecision, int costPrecision) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pricePrecision", pricePrecision);
		map.put("costPrecision", costPrecision);
		sfGdnMapper.updateCost(map);
	}
	
	/**
	 * 更新入库单成本
	 * 
	 * @param id
	 * @param pricePrecision
	 * @param costPrecision
	 */
	private void updateCostByGRN(Long id, int pricePrecision, int costPrecision) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pricePrecision", pricePrecision);
		map.put("costPrecision", costPrecision);
		sfGrnMapper.updateCost(map);
	}
	
	/**
	 * 移动加权平均核算出库成本组成本
	 */
	private void updateGrpCostStockByGdn(Long gdnId,Long costGrpId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gdnId", gdnId);
		map.put("costGrpId", costGrpId);
		sfGdnMapper.updateInitGrpCostStockByGdn(map);
	}
	
	/**
	 * 移动加权平均核算入库成本组成本
	 */
	private void updateGrpCostStockByGrn(Long grnId,Long costGrpId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("grnId", grnId);
		map.put("costGrpId", costGrpId);
		sfGrnMapper.updateInitGrpCostStockByGrn(map);
	}
}
