package mb.erp.dr.o2o.service.impl.consumer;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import mb.erp.dr.o2o.service.impl.common.TaxRateService;
import mb.erp.dr.o2o.utils.O2oUtils;
import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.APPROVED;
import mb.erp.dr.soa.constant.O2OBillConstant.AllocType;
import mb.erp.dr.soa.constant.O2OBillConstant.BASE_EXTRA;
import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OBillConstant.CREATE_TYPE;
import mb.erp.dr.soa.constant.O2OBillConstant.DATA_SOURCE;
import mb.erp.dr.soa.constant.O2OBillConstant.DIFFER_FLAG;
import mb.erp.dr.soa.constant.O2OBillConstant.IS_OOS;
import mb.erp.dr.soa.constant.O2OBillConstant.NEW_DOC_STATE;
import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;
import mb.erp.dr.soa.constant.O2OBillConstant.PROGRESS;
import mb.erp.dr.soa.old.service.bill.CommonService;
import mb.erp.dr.soa.old.vo.AdnDtlVo;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.GdnDtlVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnDtlVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.old.vo.IdtDtlVo;
import mb.erp.dr.soa.old.vo.IdtVo;
import mb.erp.dr.soa.old.vo.TbnDtlVo;
import mb.erp.dr.soa.old.vo.TbnVo;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.service.dubbo.NewSoaJmsDubboService;
import mb.erp.dr.soa.utils.DateUtil;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.DrTbnDtlVo;
import mb.erp.dr.soa.vo.DrTbnVo;
import mb.erp.dr.soa.vo.SfDgnDtlVo;
import mb.erp.dr.soa.vo.SfDgnVo;
import mb.erp.dr.soa.vo.SfGdnDtlVo;
import mb.erp.dr.soa.vo.SfGdnVo;
import mb.erp.dr.soa.vo.SfGrnVo;
import mb.erp.dr.soa.vo.SfIdtDtlVo;
import mb.erp.dr.soa.vo.SfIdtVo;
import mb.erp.dr.soa.vo.SfRvdDtlVo;
import mb.erp.dr.soa.vo.SfRvdVo;
import mb.erp.dr.soa.vo.common.CommonIdCodeVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class NewCreateVoService {
	private final Logger logger = LoggerFactory.getLogger(NewCreateVoService.class);
    @Resource
    private NewERPCommonService newERPCommonService;
    @Resource
    private CommonService commonService;
    @Resource
    private TaxRateService taxRateService;
    @Resource
	private NewSoaJmsDubboService newSoaJmsDubboService;
    @Resource
    private NewExistValidate newExistValidate;
    @Value("${wareh.locId.error}")
    private String warehLocIdError;
    @Value("${rcvWareh.locId.error}")
    private String rcvWarehLocIdError;
    
    /**
     * 生成新ERP现货订单
     * @param idtVo
     * @return
     */
	public SfIdtVo genSfIdtVoValue(IdtVo idtVo){
		CommonIdCodeVo commonIdCodeVo = new CommonIdCodeVo();
		commonIdCodeVo.setBfOrgVendeeCode(idtVo.getVendeeId());
		commonIdCodeVo.setBfOrgVenderCode(idtVo.getVenderId());
		commonIdCodeVo.setBfOrgShopCode(idtVo.getShopId());
		commonIdCodeVo.setBfOrgRcvWarehCode(idtVo.getRcvWarehId());
		commonIdCodeVo.setLowShopCode(idtVo.getLowShopId());
		commonIdCodeVo.setBrandCode(idtVo.getBrandId());
		commonIdCodeVo = newERPCommonService.getIdByCode(commonIdCodeVo);
		SfIdtVo sfIdtVo = new SfIdtVo();
		sfIdtVo.setDocDate(idtVo.getDocDate());
		sfIdtVo.setVendeeCode(idtVo.getVendeeId());
		sfIdtVo.setBfOrgVendeeId(commonIdCodeVo.getBfOrgVendeeId());
		sfIdtVo.setVenderCode(idtVo.getVenderId());
		sfIdtVo.setBfOrgVenderId(commonIdCodeVo.getBfOrgVenderId());
		sfIdtVo.setShopCode(idtVo.getShopId());
		sfIdtVo.setBfOrgShopId(commonIdCodeVo.getBfOrgShopId());
		sfIdtVo.setRcvWarehCode(idtVo.getRcvWarehId());
		sfIdtVo.setBfOrgRcvWarehId(commonIdCodeVo.getBfOrgRcvWarehId());
		sfIdtVo.setReqdAt(idtVo.getReqdAt());
		sfIdtVo.setDelivMthd(idtVo.getDelivMthd());
		sfIdtVo.setDelivAddr(idtVo.getDelivAddr());
		sfIdtVo.setDelivPstd(idtVo.getDelivPstd());
		sfIdtVo.setOldIdtCode(idtVo.getIdtNum());
		sfIdtVo.setIdtType(idtVo.getIdtType());
		sfIdtVo.setLowIdtFlag(idtVo.getLowIdtFlag());
		sfIdtVo.setSfIdtType(idtVo.getIdtType());
		sfIdtVo.setLowShopCode(idtVo.getLowShopId());
		sfIdtVo.setBfOrgLowShopId(commonIdCodeVo.getLowShopId());
		Double taxRate = taxRateService.getTaxRateForNewErp(BillType.IDT.name(), BillType.IDT.name(), sfIdtVo.getCode(), idtVo.getVendeeId(), idtVo.getVenderId());
		logger.warn("-----新ERP现货单税率 genSfIdtVoValue-----："+taxRate +", "+ BillType.IDT.name() +", "+ BillType.IDT.name()+", "+ sfIdtVo.getCode()+", "+ idtVo.getVendeeId()+", "+ idtVo.getVenderId());
		sfIdtVo.setTaxRate(taxRate);
		sfIdtVo.setCurrency(idtVo.getCurrency());
		sfIdtVo.setOrderQty(idtVo.getOrderQty());
		sfIdtVo.setOrderVal(idtVo.getOrderVal());
		sfIdtVo.setAdmQty(idtVo.getAdmQty());
		sfIdtVo.setAdmVal(idtVo.getAdmVal());
		sfIdtVo.setDataSource(DATA_SOURCE.NEWERP);
		sfIdtVo.setApproved(APPROVED.OLDERP);
		sfIdtVo.setCreateUser(idtVo.getOprId());
		sfIdtVo.setLastModifiedUser(idtVo.getOprId());
		sfIdtVo.setDelivQty(idtVo.getDelivQty());
		sfIdtVo.setDelivVal(idtVo.getDelivVal());
		sfIdtVo.setRcvQty(idtVo.getRcvQty());
		sfIdtVo.setRcvVal(idtVo.getRcvVal());
		sfIdtVo.setRemark(idtVo.getRemark());
		sfIdtVo.setSaleType("B2B");
		sfIdtVo.setBrandCode(idtVo.getBrandId());
		sfIdtVo.setBrandId(Long.parseLong(commonIdCodeVo.getBrandId()));
		sfIdtVo.setIsOos(IS_OOS.M.equals(idtVo.getIsOos()) ? "2" : (IS_OOS.T.equals(idtVo.getIsOos()) ? "1" : "0"));
		sfIdtVo.setOsDocCode(idtVo.getOsDocCode());
		sfIdtVo.setAllocType(idtVo.getAllocType()); //配货模式
		sfIdtVo.setDocState(NEW_DOC_STATE.PG_NEW);
		sfIdtVo.setOrgDocType(NewBillType.IDT.name());
		sfIdtVo.setLastFactDispWarehId(idtVo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用）
		sfIdtVo.setLastFactRcvWarehId(idtVo.getLastFactRcvWarehId());// 最终收货仓库
		sfIdtVo.setDifferFlag(DIFFER_FLAG.IS_DIFFER_OVER);
		return sfIdtVo;
	}
	
    /**
     * 生成新ERP现货订单详情 
     * @param sfIdtVo
     * @param idtVo
     * @return
     */
	public SfIdtVo genSfIdtDtlVoValue(SfIdtVo sfIdtVo,IdtVo idtVo) {
		List<SfIdtDtlVo> sfIdtDtlVos = new ArrayList<SfIdtDtlVo>();
		Set<String> set = new HashSet<String>();
		for (IdtDtlVo idtDtlVo : idtVo.getIdtDtlVos()) {
			SfIdtDtlVo sfIdtDtlVo = new SfIdtDtlVo();
			sfIdtDtlVo.setSfIdtId(sfIdtVo.getId());
			sfIdtDtlVo.setProdCode(idtDtlVo.getProdId());
			Long prodId = newERPCommonService.getProdIdByProdNum(idtDtlVo.getProdId());
			sfIdtDtlVo.setProdId(prodId);
			sfIdtDtlVo.setOrderQty(idtDtlVo.getOrderQty());
			sfIdtDtlVo.setUnitPrice(idtDtlVo.getUnitPrice());
			sfIdtDtlVo.setDiscRate(idtDtlVo.getDiscRate());
			sfIdtDtlVo.setAllocQty(idtDtlVo.getAdmQty());
			sfIdtDtlVo.setRcvQty(idtDtlVo.getRcvQty());
			sfIdtDtlVo.setActQty(idtDtlVo.getDelivQty());
			sfIdtDtlVo.setLocCode(idtDtlVo.getLocId());
			sfIdtDtlVo.setRcptLocCode(idtDtlVo.getRcptLocId());
			logger.warn("*******新ERP现货单(genSfIdtDtlVoValue)：商品："+sfIdtDtlVo.getProdCode() +" 发货货位："+sfIdtDtlVo.getLocCode()+" 收货货位："+sfIdtDtlVo.getRcptLocCode());
			sfIdtDtlVos.add(sfIdtDtlVo);
			set.add(idtDtlVo.getProdId().substring(0,6));
		}
		//品项数
		sfIdtVo.setProductCount((double)set.size());
		sfIdtVo.setSfIdtDtlVos(sfIdtDtlVos);
    	// 验证参数是否有效
    	String existProperty = newExistValidate.existValidateSfIdt(sfIdtVo);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
        return sfIdtVo;
	}
	
	/**
	 * 根据计划配货单生成新ERP上级现货订单
	 * @param adnVo
	 * @return
	 */
	public SfIdtVo genSfIdtByAdn(AdnVo adnVo){
		IdtVo idtVo = new IdtVo();
		idtVo.setIdtNum(adnVo.getFactIdtNum());
		idtVo.setVendeeId(adnVo.getFactVendeeId());
		idtVo = commonService.select(idtVo);
		CommonIdCodeVo commonIdCodeVo = new CommonIdCodeVo();
		commonIdCodeVo.setBfOrgVendeeCode(adnVo.getVendeeId());
		commonIdCodeVo.setBfOrgVenderCode(adnVo.getVenderId());
		commonIdCodeVo.setBfOrgWarehCode(adnVo.getWarehId());
		commonIdCodeVo.setBrandCode(adnVo.getBrandId());
		commonIdCodeVo.setBfOrgShopCode(idtVo.getShopId());
		commonIdCodeVo.setBfOrgRcvWarehCode(adnVo.getTranRcvWarehId());
		commonIdCodeVo.setLowShopCode(idtVo.getLowShopId());
		commonIdCodeVo = newERPCommonService.getIdByCode(commonIdCodeVo);
		SfIdtVo sfIdtVo = new SfIdtVo();
		sfIdtVo.setDocDate(adnVo.getDocDate());
		sfIdtVo.setVendeeCode(adnVo.getVendeeId());
		sfIdtVo.setBfOrgVendeeId(commonIdCodeVo.getBfOrgVendeeId());
		sfIdtVo.setVenderCode(adnVo.getVenderId());
		sfIdtVo.setBfOrgVenderId(commonIdCodeVo.getBfOrgVenderId());
		sfIdtVo.setRcvWarehCode(adnVo.getWarehId());
		sfIdtVo.setBfOrgRcvWarehId(commonIdCodeVo.getBfOrgRcvWarehId());
		sfIdtVo.setReqdAt(adnVo.getReqdAt());
		sfIdtVo.setDelivMthd(idtVo.getDelivMthd());
		sfIdtVo.setDelivAddr(idtVo.getDelivAddr());
		sfIdtVo.setDelivPstd(idtVo.getDelivPstd());
		sfIdtVo.setOldIdtCode("");
		sfIdtVo.setIdtType(idtVo.getIdtType());
		sfIdtVo.setLowIdtFlag(idtVo.getLowIdtFlag());
		sfIdtVo.setSfIdtType(idtVo.getIdtType());
		sfIdtVo.setBfOrgShopId(commonIdCodeVo.getBfOrgShopId());
		sfIdtVo.setShopCode(idtVo.getShopId());
		sfIdtVo.setLowShopCode(idtVo.getLowShopId());
		sfIdtVo.setBfOrgLowShopId(commonIdCodeVo.getLowShopId());
		Double taxRate = taxRateService.getTaxRateForNewErp(BillType.IDT.name(), BillType.IDT.name(), sfIdtVo.getCode(), adnVo.getVendeeId(), adnVo.getVenderId());
		logger.warn("-----新ERP现货单税率 genSfIdtByAdn-----："+taxRate +", "+ BillType.IDT.name() +", "+ BillType.IDT.name()+", "+ sfIdtVo.getCode()+", "+ adnVo.getVendeeId()+", "+ adnVo.getVenderId());
		sfIdtVo.setTaxRate(taxRate);
		sfIdtVo.setCurrency(adnVo.getCurrency());
		sfIdtVo.setOrderQty(idtVo.getOrderQty());
		sfIdtVo.setOrderVal(idtVo.getOrderVal());
		sfIdtVo.setAdmQty(adnVo.getAdmQty());
		sfIdtVo.setAdmVal(adnVo.getAdmVal());
		sfIdtVo.setDataSource(adnVo.getDataSource());
		sfIdtVo.setApproved(APPROVED.OLDERP);
		sfIdtVo.setCreateUser(adnVo.getCtrlrId());
		sfIdtVo.setLastModifiedUser(adnVo.getCtrlrId());
		sfIdtVo.setDelivQty(adnVo.getDelivQty());
		sfIdtVo.setDelivVal(adnVo.getDelivVal());
		sfIdtVo.setRcvQty(adnVo.getRcvQty());
		sfIdtVo.setRcvVal(adnVo.getRcvVal());
		sfIdtVo.setRemark(adnVo.getRemark());
		sfIdtVo.setSaleType("B2B");
		sfIdtVo.setBrandCode(adnVo.getBrandId());
		sfIdtVo.setBrandId(Long.parseLong(commonIdCodeVo.getBrandId()));
		sfIdtVo.setIsOos(IS_OOS.M.equals(idtVo.getIsOos()) ? "2" : (IS_OOS.T.equals(idtVo.getIsOos()) ? "1" : "0"));
		sfIdtVo.setOsDocCode(adnVo.getOsDocCode());
		sfIdtVo.setAllocType(adnVo.getAllocType()); //配货模式
		sfIdtVo.setDocState(NEW_DOC_STATE.PG_NEW);
		sfIdtVo.setOrgDocType(NewBillType.IDT.name());
		sfIdtVo.setLastFactDispWarehId(adnVo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用）
		sfIdtVo.setLastFactRcvWarehId(adnVo.getLastFactRcvWarehId());// 最终收货仓库
		sfIdtVo.setDifferFlag(DIFFER_FLAG.IS_DIFFER_OVER);
		return sfIdtVo;
	}
	
	/**
	 * 根据计划配货单详情生成新ERP上级现货订单详情
	 * @param adnVo
	 * @return
	 */
	public SfIdtVo genSfIdtDtlByAdnDtl(AdnVo adnVo,SfIdtVo sfIdtVo){
		List<SfIdtDtlVo> sfIdtDtlVos = new ArrayList<SfIdtDtlVo>();
		Set<String> set = new HashSet<String>();
		for(AdnDtlVo adnDtlVo : adnVo.getAdnDtlVos()){
			SfIdtDtlVo sfIdtDtlVo = new SfIdtDtlVo();
			sfIdtDtlVo.setSfIdtId(sfIdtVo.getId());
			sfIdtDtlVo.setProdCode(adnDtlVo.getProdId());
			Long prodId = newERPCommonService.getProdIdByProdNum(adnDtlVo.getProdId());
			sfIdtDtlVo.setProdId(prodId);
			sfIdtDtlVo.setOrderQty(adnDtlVo.getAdmQty());
			sfIdtDtlVo.setUnitPrice(adnDtlVo.getUnitPrice());
			sfIdtDtlVo.setDiscRate(adnDtlVo.getDiscRate());
			sfIdtDtlVo.setAllocQty(adnDtlVo.getAdmQty());
			sfIdtDtlVo.setRcvQty(adnDtlVo.getRcvQty());
			sfIdtDtlVo.setActQty(adnDtlVo.getDelivQty());
			sfIdtDtlVo.setLocCode(adnDtlVo.getLocId());
			sfIdtDtlVo.setRcptLocCode(adnDtlVo.getRcptLocId());
			logger.warn("*******新ERP现货单(genSfIdtDtlByAdnDtl)：商品："+sfIdtDtlVo.getProdCode() +" 发货货位："+sfIdtDtlVo.getLocCode()+" 收货货位："+sfIdtDtlVo.getRcptLocCode());
			sfIdtDtlVos.add(sfIdtDtlVo);
			set.add(adnDtlVo.getProdId().substring(0, 6));
		}
		sfIdtVo.setProductCount((double)set.size());
		sfIdtVo.setSfIdtDtlVos(sfIdtDtlVos);
    	// 验证参数是否有效
    	String existProperty = newExistValidate.existValidateSfIdt(sfIdtVo);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
		return sfIdtVo;
	}
	
	/**
	 * 根据新ERP现货订单生成交货单
	 * @param sfIdtVo
	 * @param adnVo
	 * @return
	 */
	public SfDgnVo genSfDgnBySfIdt(SfDgnVo sfDgnVo,SfIdtVo sfIdtVo,AdnVo adnVo){
		Double taxRate = taxRateService.getTaxRateForNewErp(NewBillType.DGN.name(), BillType.IDT.name(), sfDgnVo.getCode(), adnVo.getVendeeId(), adnVo.getVenderId());
		logger.warn("-----新ERP交货单税率 genSfDgnBySfIdt-----："+taxRate +", "+ NewBillType.DGN.name() +", "+ BillType.IDT.name()+", "+ sfDgnVo.getCode()+", "+ adnVo.getVendeeId()+", "+ adnVo.getVenderId());
		sfDgnVo.setTaxRate(taxRate);
		sfDgnVo.setBrandCode(adnVo.getBrandId());
		sfDgnVo.setVenderCode(adnVo.getVenderId());
		sfDgnVo.setRcvWarehCode(adnVo.getTranRcvWarehId());
		sfDgnVo.setNewBillType(NewBillType.DGN);
		sfDgnVo.setAllocType(sfIdtVo.getAllocType()); //配货模式 (队列使用)
		sfDgnVo.setDataSource(sfIdtVo.getDataSource());
		sfDgnVo.setOsDocCode(sfIdtVo.getOsDocCode());
		Long warenId = newERPCommonService.getUnitIdByUnitCode(adnVo.getWarehId());
		sfDgnVo.setWarehId(warenId); //发货仓库
		sfDgnVo.setDispWarehCode(adnVo.getWarehId());
		sfDgnVo.setOriginDocType(sfIdtVo.getOrgDocType());
		sfDgnVo.setOriginDocNum(sfIdtVo.getCode());
		sfDgnVo.setOriginUnitId(sfIdtVo.getBfOrgVendeeId());
		sfDgnVo.setGdnState(O2OBillConstant.RCV_STATE_NEW.N); // 出库原因
		sfDgnVo.setLastFactDispWarehId(adnVo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用）
		sfDgnVo.setLastFactRcvWarehId(adnVo.getLastFactRcvWarehId());// 最终收货仓库
		Set<String> set = new HashSet<String>();
		for(SfDgnDtlVo sfDgnDtlVo : sfDgnVo.getSfDgnDtlVos()){
			Long locId = null;
			String prodCode = newERPCommonService.getProdNumByProdId(sfDgnDtlVo.getProdId());
			sfDgnDtlVo.setProdCode(prodCode);
			sfDgnDtlVo.setSfDgnId(sfDgnVo.getId());
			for(AdnDtlVo adnDtlVo : adnVo.getAdnDtlVos()){
				if (prodCode.equals(adnDtlVo.getProdId())) {
					sfDgnDtlVo.setLocCode(adnDtlVo.getLocId());
					if (SoaBillUtils.isNotBlank(adnDtlVo.getLocId())) {
						if (sfDgnVo.getDispWarehCode().equals(adnVo.getLastFactDispWarehId())) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("warehId", sfDgnVo.getWarehId());
							map.put("locCode", adnDtlVo.getLocId());
							locId = newERPCommonService.getLocIdByLocCode(map);
							if (null == locId) {
								throw new RuntimeException(MessageFormat.format(warehLocIdError, adnVo.getWarehId(),adnDtlVo.getLocId()));
							}
						}
					}
					sfDgnDtlVo.setLocId(locId);
					sfDgnDtlVo.setRcptLocCode(adnDtlVo.getRcptLocId());
				}
			}
			logger.warn("*******新ERP交货单（genSfDgnBySfIdt）：商品："+sfDgnDtlVo.getProdCode() +" 发货货位："+sfDgnDtlVo.getLocCode()+"发货货位ID:"+sfDgnDtlVo.getLocId()+" 收货货位："+sfDgnDtlVo.getRcptLocCode());
			set.add(prodCode.substring(0, 6));
		}
		sfDgnVo.setProductCount((long) set.size());
    	// 验证参数是否有效
    	String existProperty = newExistValidate.existValidateSfDgn(sfDgnVo);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
		return  sfDgnVo;
	}
	
	/**
	 * 根据计划配货单生成交货单
	 * @param sfIdtVo
	 * @param adnVo
	 * @return
	 */
	public SfDgnVo genSfDgnByAdn(AdnVo adnVo){
		CommonIdCodeVo commonIdCodeVo = new CommonIdCodeVo();
		commonIdCodeVo.setBfOrgVendeeCode(adnVo.getVendeeId());
		commonIdCodeVo.setBfOrgVenderCode(adnVo.getVenderId());
		commonIdCodeVo.setBfOrgWarehCode(adnVo.getWarehId());
		commonIdCodeVo.setBfOrgRcvWarehCode(adnVo.getTranRcvWarehId());
		commonIdCodeVo.setBrandCode(adnVo.getBrandId());
		commonIdCodeVo = newERPCommonService.getIdByCode(commonIdCodeVo);
		SfDgnVo sfDgnVo = new SfDgnVo();
		sfDgnVo.setDgnTypeSl("DD");
		sfDgnVo.setDataSource(adnVo.getDataSource());
		sfDgnVo.setCreateType("02");
		sfDgnVo.setDocDate(adnVo.getDocDate());
		sfDgnVo.setBrandCode(adnVo.getBrandId()); 
		sfDgnVo.setBrandId(Long.parseLong(commonIdCodeVo.getBrandId()));
		sfDgnVo.setVenderCode(adnVo.getVenderId()); 
		sfDgnVo.setBfOrgUnitId(commonIdCodeVo.getBfOrgVenderId());
		sfDgnVo.setVendeeCode(adnVo.getVendeeId());
		sfDgnVo.setBfOrgRcvUnitId(commonIdCodeVo.getBfOrgVendeeId());
		sfDgnVo.setRcvWarehCode(adnVo.getTranRcvWarehId()); 
		sfDgnVo.setBfOrgRcvWarehId(commonIdCodeVo.getBfOrgRcvWarehId());
		sfDgnVo.setDispWarehCode(adnVo.getWarehId());
		sfDgnVo.setWarehId(commonIdCodeVo.getBfOrgWarehId());
		sfDgnVo.setTtlQty(adnVo.getAdmQty());
		sfDgnVo.setTtlVal(adnVo.getAdmVal());
		Double taxRate = taxRateService.getTaxRateForNewErp(NewBillType.DGN.name(), BillType.IDT.name(), sfDgnVo.getCode(), adnVo.getVendeeId(), adnVo.getVenderId());
		logger.warn("-----新ERP交货单税率 genSfDgnByAdn-----："+taxRate +", "+ NewBillType.DGN.name() +", "+ BillType.IDT.name()+", "+ sfDgnVo.getCode()+", "+ adnVo.getVendeeId()+", "+ adnVo.getVenderId());
		sfDgnVo.setTaxRate(taxRate);
		sfDgnVo.setCurrency(adnVo.getCurrency());
		sfDgnVo.setRemark(adnVo.getRemark());
		sfDgnVo.setCreateUser(adnVo.getCtrlrId());
		sfDgnVo.setLastModifiedUser(adnVo.getCtrlrId());
		sfDgnVo.setSrcDocCode(adnVo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_NUM).toString());
		sfDgnVo.setSrcDocType(NewBillType.IDT.name());
		sfDgnVo.setSrcUnitId(commonIdCodeVo.getBfOrgVenderId());
		sfDgnVo.setVendeeCode(adnVo.getVendeeId());
		sfDgnVo.setDocState(NEW_DOC_STATE.PG_NEW);
		sfDgnVo.setDelivMode(adnVo.getGdnMode().name());
		sfDgnVo.setOriginDocType(NewBillType.IDT.name());
		sfDgnVo.setOriginDocNum(O2oUtils.ToString(adnVo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_NUM)));
		sfDgnVo.setOriginUnitId(commonIdCodeVo.getBfOrgVendeeId());
		sfDgnVo.setProgress(PROGRESS.PG);
		sfDgnVo.setGdnState(O2OBillConstant.RCV_STATE_NEW.N); // 出库原因
		sfDgnVo.setOsDocCode(adnVo.getOsDocCode());
		sfDgnVo.setLastFactDispWarehId(adnVo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用）
		sfDgnVo.setLastFactRcvWarehId(adnVo.getLastFactRcvWarehId());// 最终收货仓库
		return  sfDgnVo;
	}
	
	/**
	 * 根据计划配货单详情生成交货单详情
	 * @param sfIdtVo
	 * @param adnVo
	 * @return
	 */
	public SfDgnVo genSfDgnDtlByAdnDtl(SfDgnVo sfDgnVo,AdnVo adnVo){
		List<SfDgnDtlVo> sfDgnDtlVos = new ArrayList<SfDgnDtlVo>();
		Set<String> set = new HashSet<String>();
		for(AdnDtlVo adnDtlVo : adnVo.getAdnDtlVos()){
			SfDgnDtlVo sfDgnDtlVo = new SfDgnDtlVo();
			sfDgnDtlVo.setSfDgnId(sfDgnVo.getId());
			Long prodId = newERPCommonService.getProdIdByProdNum(adnDtlVo.getProdId());
			sfDgnDtlVo.setProdId(prodId);
			sfDgnDtlVo.setProdCode(adnDtlVo.getProdId());
			sfDgnDtlVo.setUnitPrice(adnDtlVo.getUnitPrice());
			sfDgnDtlVo.setQuantity(adnDtlVo.getAdmQty());
			sfDgnDtlVo.setDiscRate(adnDtlVo.getDiscRate());
			sfDgnDtlVo.setPickedQty(adnDtlVo.getAdmQty());
			sfDgnDtlVo.setLocCode(adnDtlVo.getLocId());
			Long locId = null;
			if (SoaBillUtils.isNotBlank(adnDtlVo.getLocId())) {
				if (sfDgnVo.getDispWarehCode().equals(sfDgnVo.getLastFactDispWarehId())) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("warehId", sfDgnVo.getWarehId());
					map.put("locCode", adnDtlVo.getLocId());
					locId = newERPCommonService.getLocIdByLocCode(map);
					if (null == locId) {
						throw new RuntimeException(MessageFormat.format(warehLocIdError, adnVo.getWarehId(),adnDtlVo.getLocId()));
					}
				}
			}
			sfDgnDtlVo.setLocId(locId);
			sfDgnDtlVo.setLocCode(adnDtlVo.getLocId());
			sfDgnDtlVo.setRcptLocCode(adnDtlVo.getRcptLocId());
			logger.warn("*******新ERP交货单（genSfDgnDtlByAdnDtl）：商品："+sfDgnDtlVo.getProdCode() +" 发货货位："+sfDgnDtlVo.getLocCode()+" 发货货位ID:"+sfDgnDtlVo.getLocId()+" 收货货位："+sfDgnDtlVo.getRcptLocCode());
			sfDgnDtlVos.add(sfDgnDtlVo);
			set.add(adnDtlVo.getProdId().substring(0, 6));
		}
		sfDgnVo.setProductCount((long)set.size());
		sfDgnVo.setSfDgnDtlVos(sfDgnDtlVos);
    	// 验证参数是否有效
    	String existProperty = newExistValidate.existValidateSfDgn(sfDgnVo);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
		return  sfDgnVo;
	}
	
	/**
	 * 根据老ERP出库单生成交货单
	 * @param sfIdtVo
	 * @param adnVo
	 * @return
	 */
	public SfDgnVo genSfDgnByGdn(GdnVo vo){
		SfDgnVo sfDgnVo = newSoaJmsDubboService.createDgnByDrTbn(vo.getDrTbnId());
		sfDgnVo.setBrandCode(vo.getBrandId()); 
		sfDgnVo.setDocState(NEW_DOC_STATE.PG_NEW);
		sfDgnVo.setOsDocCode(vo.getOsDocCode());
		sfDgnVo.setOriginDocType(O2oUtils.ToString(vo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_TYPE)));
		sfDgnVo.setOriginDocNum(O2oUtils.ToString(vo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_NUM)));
		sfDgnVo.setOriginUnitId(sfDgnVo.getBfOrgRcvUnitId());
		sfDgnVo.setRemark(vo.getRemark());
		sfDgnVo.setGdnState(O2OBillConstant.RCV_STATE_NEW.N); // 出库原因
		sfDgnVo.setDocDate(new Date());
		sfDgnVo.setCreateDate(DateUtil.now());
		Double taxRate = taxRateService.getTaxRateForNewErp(NewBillType.DGN.name(), NewBillType.TBN.name(), sfDgnVo.getOriginDocNum(), sfDgnVo.getVendeeCode(), sfDgnVo.getVenderCode());
		logger.warn("-----新ERP交货单税率 genSfDgnByGdn-----："+taxRate +", "+ NewBillType.DGN.name() +", "+ NewBillType.TBN.name()+", "+ sfDgnVo.getOriginDocNum()+", "+ sfDgnVo.getVendeeCode()+", "+ sfDgnVo.getVenderCode());
		sfDgnVo.setTaxRate(taxRate);
		Map<String, Long> params = new HashMap<String, Long>();
		params.put("dgnId", sfDgnVo.getId());
		params.put("tbnId", vo.getDrTbnId());
		List<SfDgnDtlVo> sfDgnDtlVos = newSoaJmsDubboService.createDgnDtlByDrTbn(params);
		Set<String> set = new HashSet<String>();
		for(SfDgnDtlVo sfDgnDtlVo : sfDgnDtlVos){
			Long locId = null;
			for(GdnDtlVo gdnDtlVo : vo.getGdnDtlVos()){
				if (sfDgnDtlVo.getProdCode().equals(gdnDtlVo.getProdId())) {
					sfDgnDtlVo.setLocCode(gdnDtlVo.getLocId());
					if (SoaBillUtils.isNotBlank(gdnDtlVo.getLocId())) {
						if (sfDgnVo.getDispWarehCode().equals(vo.getLastFactDispWarehId())) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("warehId", sfDgnVo.getWarehId());
							map.put("locCode",gdnDtlVo.getLocId());
							locId = newERPCommonService.getLocIdByLocCode(map);
							if (null == locId) {
								throw new RuntimeException(MessageFormat.format(warehLocIdError, vo.getWarehId(),gdnDtlVo.getLocId()));
							}
						}
					}
					sfDgnDtlVo.setLocId(locId);
					sfDgnDtlVo.setRcptLocCode(gdnDtlVo.getRcptLocId());
				}
			}
			logger.warn("*******新ERP交货单（genSfDgnByGdn）：商品："+sfDgnDtlVo.getProdCode() +" 发货货位："+sfDgnDtlVo.getLocCode()+" 发货货位ID:"+sfDgnDtlVo.getLocId()+" 收货货位："+sfDgnDtlVo.getRcptLocCode());
			set.add(sfDgnDtlVo.getProdCode().substring(0, 6));
		}
		sfDgnVo.setProductCount((long) set.size());
		sfDgnVo.setSfDgnDtlVos(sfDgnDtlVos);
		return  sfDgnVo;
	}

	 /**
    * 根据老ERP调配单生成新ERP调配单
    * @param tbnVo
    * @return
    */
	public DrTbnVo genDrTbnByTbn(TbnVo tbnVo) {
		CommonIdCodeVo commonIdCodeVo = new CommonIdCodeVo();
		commonIdCodeVo.setBfOrgVendeeCode(tbnVo.getVendeeId());
		commonIdCodeVo.setBfOrgVenderCode(tbnVo.getVenderId());
		commonIdCodeVo.setOprCode(tbnVo.getOprId());
		commonIdCodeVo.setBfOrgRcvWarehCode(tbnVo.getRcvWarehId());
		commonIdCodeVo.setBfOrgWarehCode(tbnVo.getDispWarehId());
		commonIdCodeVo.setBrandCode(tbnVo.getBrandId());
		commonIdCodeVo = newERPCommonService.getIdByCode(commonIdCodeVo);
		DrTbnVo drTbnVo = new DrTbnVo();
		drTbnVo.setVenderCode(tbnVo.getVenderId());
		drTbnVo.setVenderId(commonIdCodeVo.getBfOrgVenderId());
		drTbnVo.setVendeeCode(tbnVo.getVendeeId());
		drTbnVo.setVendeeId(commonIdCodeVo.getBfOrgVendeeId());
		drTbnVo.setDispWarehCode(tbnVo.getDispWarehId());
		drTbnVo.setDispWarehId(commonIdCodeVo.getBfOrgWarehId());
		drTbnVo.setDispTime(new Date());
		drTbnVo.setDocDate(new Date());
		drTbnVo.setRcvWarehCode(tbnVo.getRcvWarehId());
		drTbnVo.setRcvWarehId(commonIdCodeVo.getBfOrgRcvWarehId());
		drTbnVo.setOprCode(tbnVo.getOprId());
		drTbnVo.setOprId(commonIdCodeVo.getOprId());
		drTbnVo.setReqdAt(tbnVo.getReqdAt());
		drTbnVo.setDelivAddr(tbnVo.getDelivAddr());
		Double taxRate = taxRateService.getTaxRateForNewErp(NewBillType.TBN.name(), NewBillType.TBN.name(), drTbnVo.getTbnNum(), drTbnVo.getVendeeCode(), drTbnVo.getVenderCode());
		logger.warn("-----新ERP调配单税率 genDrTbnByTbn-----："+taxRate +", "+ NewBillType.TBN.name() +", "+ NewBillType.TBN.name()+", "+ drTbnVo.getTbnNum()+", "+ drTbnVo.getVendeeCode()+", "+ drTbnVo.getVenderCode());
		drTbnVo.setTaxRate(taxRate);
		drTbnVo.setCurrency(tbnVo.getCurrency());
		drTbnVo.setTtlQty(tbnVo.getCrQty());
		drTbnVo.setTtlVal(tbnVo.getCrVal());
		drTbnVo.setProgress(PROGRESS.PG);
		drTbnVo.setDocState(NEW_DOC_STATE.PG_NEW);
		drTbnVo.setRemark(tbnVo.getRemark());
		drTbnVo.setOldTbnNum(tbnVo.getTbnNum());
		drTbnVo.setBrandCode(tbnVo.getBrandId());
		drTbnVo.setBrandId(Long.parseLong(commonIdCodeVo.getBrandId()));
		drTbnVo.setReasonCode(tbnVo.getReasonCode());
		drTbnVo.setRcvAddr(tbnVo.getRcvAddr());
		drTbnVo.setRcvUser(tbnVo.getRcvUser());
		drTbnVo.setRcvPhoneNo(tbnVo.getRcvPhoneNo());
		drTbnVo.setDataSource(tbnVo.getDataSource());
		drTbnVo.setApproved(APPROVED.OLDERP);
		drTbnVo.setCreateUser(tbnVo.getOprId());
		drTbnVo.setLastModifiedUser(tbnVo.getOprId());
		drTbnVo.setIsOos(IS_OOS.M.equals(tbnVo.getIsOos()) ? "2" : (IS_OOS.T.equals(tbnVo.getIsOos()) ? "1" : "0"));
		drTbnVo.setOsDocCode(tbnVo.getOsDocCode());
		drTbnVo.setOrgDocType(NewBillType.TBN.name());
		drTbnVo.setLastFactDispWarehId(tbnVo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用）
		drTbnVo.setLastFactRcvWarehId(tbnVo.getLastFactRcvWarehId());// 最终收货仓库
		return drTbnVo;
	}
	
	
	 /**
	* 根据老ERP调配单详情生成新ERP调配单详情
	* @param tbnVo
	* @return
	*/
	public DrTbnVo genDrTbnDtlByTbnDtl(TbnVo tbnVo,DrTbnVo drTbnVo) {
		List<DrTbnDtlVo> drTbnDtlVos = new ArrayList<DrTbnDtlVo>();
		for(TbnDtlVo tbnDtlVo : tbnVo.getTbnDtlVos()){
			DrTbnDtlVo drTbnDtlVo = new DrTbnDtlVo();
			drTbnDtlVo.setDrTbnId(drTbnVo.getId());
			drTbnDtlVo.setProdCode(tbnDtlVo.getProdId());
			Long prodId = newERPCommonService.getProdIdByProdNum(tbnDtlVo.getProdId());
			drTbnDtlVo.setProdId(prodId);
			drTbnDtlVo.setExpdQty(tbnDtlVo.getExpdQty());
			drTbnDtlVo.setUnitPrice(tbnDtlVo.getUnitPrice());
			drTbnDtlVo.setDiscRate(tbnDtlVo.getDiscRate());
			drTbnDtlVo.setCrVal(tbnDtlVo.getExpdQty() * tbnDtlVo.getUnitPrice() * tbnDtlVo.getDiscRate() * 0.01);
			drTbnDtlVo.setLocCode(tbnDtlVo.getLocId());
			drTbnDtlVo.setRcptLocCode(tbnDtlVo.getRcptLocId());
			logger.warn("*******新ERP调配单（genDrTbnDtlByTbnDtl）：商品："+drTbnDtlVo.getProdCode() +" 发货货位："+drTbnDtlVo.getLocCode()+" 收货货位："+drTbnDtlVo.getRcptLocCode());
			drTbnDtlVos.add(drTbnDtlVo);
		}
		drTbnVo.setDtlVos(drTbnDtlVos);
    	// 验证参数是否有效
    	String existProperty = newExistValidate.existValidateDrTbn(drTbnVo);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
		return drTbnVo;
	}
	
	/**
	 * 根据交货单生成出库单
	 * @param dgnVo
	 * @return
	 */
	public SfGdnVo genSfGdnBySfDgn(SfDgnVo sfDgnVo,SfGdnVo sfGdnVo,GdnVo gdnVo){
		CommonIdCodeVo commonIdCodeVo = new CommonIdCodeVo();
		commonIdCodeVo.setBfOrgVendeeId(sfGdnVo.getBfOrgRcvUnitId());
		commonIdCodeVo.setBfOrgVenderId(sfGdnVo.getBfOrgUnitId());
		commonIdCodeVo.setBfOrgWarehId(sfGdnVo.getBfOrgWarehId());
		commonIdCodeVo.setBfOrgRcvWarehId(sfGdnVo.getBfOrgRcvWarehId());
		commonIdCodeVo.setBrandId(String.valueOf(sfGdnVo.getBrandId()));
		commonIdCodeVo = newERPCommonService.getCodeById(commonIdCodeVo);
		sfGdnVo.setVendeeCode(commonIdCodeVo.getBfOrgVendeeCode());
		sfGdnVo.setVenderCode(commonIdCodeVo.getBfOrgVenderCode());
		sfGdnVo.setBrandCode(commonIdCodeVo.getBrandCode());
		sfGdnVo.setDispWarehCode(commonIdCodeVo.getBfOrgWarehCode());
		sfGdnVo.setRcvWarehCode(commonIdCodeVo.getBfOrgRcvWarehCode());
		sfGdnVo.setDelivAddr(gdnVo.getDelivAddr());
		sfGdnVo.setAllocType(gdnVo.getAllocType());
		sfGdnVo.setRcvState(O2OBillConstant.RCV_STATE_NEW.N); // 出库原因
		//新事务 查询不到新保存的交货单
//		Double taxRate = taxRateService.getTaxRateForNewErp(NewBillType.GDN.name(), NewBillType.DGN.name(), sfDgnVo.getCode(), sfGdnVo.getVendeeCode(), sfGdnVo.getVenderCode());
		Double taxRate = sfDgnVo.getTaxRate();
		logger.warn("-----新ERP出库单税率 genSfGdnBySfDgn-----："+taxRate +", "+ NewBillType.GDN.name() +", "+ NewBillType.DGN.name()+", "+ sfDgnVo.getCode()+", "+ sfGdnVo.getVendeeCode()+", "+ sfGdnVo.getVenderCode());
		sfGdnVo.setTaxRate(taxRate);
    	//TODO可以放入缓存
		String amount_precision = newERPCommonService.getMainSysParamValue(BASE_EXTRA.AMOUNT_PRECISION); //获取新ERP金额的小数点精度
    	Double taxVal = (sfGdnVo.getTtlVal()/(100+taxRate))*taxRate;
    	BigDecimal b = new BigDecimal(taxVal);  
    	sfGdnVo.setTaxVal(b.setScale(Integer.parseInt(amount_precision), BigDecimal.ROUND_HALF_UP).doubleValue());
		Set<String> set = new HashSet<String>();
		for(SfGdnDtlVo sfGdnDtlVo : sfGdnVo.getSfGdnDtlVos()){
			String prodCode = newERPCommonService.getProdNumByProdId(sfGdnDtlVo.getProdId());
			sfGdnDtlVo.setProdCode(prodCode);
			sfGdnDtlVo.setSfGdnId(sfGdnVo.getId());
			Long locId = null;
			for (GdnDtlVo gdnDtlVo : gdnVo.getGdnDtlVos()) {
				if (prodCode.equals(gdnDtlVo.getProdId())) {
					sfGdnDtlVo.setLocCode(gdnDtlVo.getLocId());
					if (SoaBillUtils.isNotBlank(gdnDtlVo.getLocId())) {
						if (sfGdnVo.getDispWarehCode().equals(gdnVo.getLastFactDispWarehId())) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("warehId", sfGdnVo.getBfOrgWarehId());
							map.put("locCode", gdnDtlVo.getLocId());
							locId = newERPCommonService.getLocIdByLocCode(map);
							if (null == locId) {
								throw new RuntimeException(MessageFormat.format(warehLocIdError, gdnVo.getWarehId(),gdnDtlVo.getLocId()));
							}
						}
					}
					sfGdnDtlVo.setLocId(locId);
					sfGdnDtlVo.setRcptLocCode(gdnDtlVo.getRcptLocId());
				}
			}
			logger.warn("*******新ERP出库单（genSfGdnBySfDgn）：商品："+sfGdnDtlVo.getProdCode() +" 发货货位："+sfGdnDtlVo.getLocCode()+" 发货货位ID："+sfGdnDtlVo.getLocId()+" 收货货位："+sfGdnDtlVo.getRcptLocCode());
			set.add(prodCode.substring(0,6));
		}
		sfGdnVo.setProductCount((double)set.size());
    	// 验证参数是否有效
    	String existProperty = newExistValidate.existValidateSfGdn(sfGdnVo);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
		return sfGdnVo;
	}
	
	/**
	 * 根据老ERP出库单生成到货通知单
	 * @param gdnVo
	 * @return
	 */
	public SfRvdVo genSfRvdByGdn(GdnVo gdnVo){
		CommonIdCodeVo commonIdCodeVo = new CommonIdCodeVo();
		commonIdCodeVo.setFactRcvWarehCode(gdnVo.getRcvWarehId());
		commonIdCodeVo.setBrandCode(gdnVo.getBrandId());
		commonIdCodeVo.setBfOrgVendeeCode(gdnVo.getRcvUnitId());
		commonIdCodeVo.setBfOrgVenderCode(gdnVo.getUnitId());
		commonIdCodeVo.setBfOrgWarehCode(gdnVo.getWarehId());
		commonIdCodeVo = newERPCommonService.getIdByCode(commonIdCodeVo);
		SfRvdVo sfRvdVo = new SfRvdVo();
		sfRvdVo.setSrcUnitId(commonIdCodeVo.getBfOrgVenderId());//原始单据组织id
		sfRvdVo.setWarehId(commonIdCodeVo.getBfOrgWarehId());//发货仓库
		sfRvdVo.setDocDate(gdnVo.getDocDate());
		sfRvdVo.setVendeeCode(gdnVo.getRcvUnitId());//接收组织编码
		sfRvdVo.setUnitId(commonIdCodeVo.getBfOrgVendeeId());
		sfRvdVo.setRcvWarehCode(gdnVo.getRcvWarehId());//接收仓库编码
		sfRvdVo.setBfOrgRcvWarehId(commonIdCodeVo.getFactRcvWarehId());
		sfRvdVo.setFactRcvWarehCode(gdnVo.getRcvWarehId());//实际接收仓库编码
		sfRvdVo.setFactRcvWarehId(commonIdCodeVo.getFactRcvWarehId());
		sfRvdVo.setRcptMode(gdnVo.getDelivMode());
		sfRvdVo.setSrcDocType(NewBillType.GDN.name());
		sfRvdVo.setRcvState(O2OBillConstant.RCV_STATE_NEW.N); // 出库原因
		sfRvdVo.setSrcDocNum(gdnVo.getGdnNum());
		sfRvdVo.setSrcUnitCode(gdnVo.getUnitId());
		sfRvdVo.setVenderCode(gdnVo.getUnitId());//发货组织编码
		sfRvdVo.setBfOrgUnitId(commonIdCodeVo.getBfOrgVenderId());
		sfRvdVo.setDispWarehCode(gdnVo.getWarehId());//发货仓库编码
		sfRvdVo.setBrandCode(gdnVo.getBrandId());
		sfRvdVo.setBrandId(Long.parseLong(commonIdCodeVo.getBrandId()));
		sfRvdVo.setDocState(NEW_DOC_STATE.PG_NEW);
		sfRvdVo.setProgress(PROGRESS.PG);
		sfRvdVo.setCurrency(gdnVo.getCurrency());
		sfRvdVo.setTtlQty(gdnVo.getTtlQty());//实收数量
		sfRvdVo.setTtlVal(gdnVo.getTtlVal());//实收金额
		sfRvdVo.setDelivQty(gdnVo.getTtlQty());//发货总数量
		sfRvdVo.setDelivVal(gdnVo.getTtlVal());//发货总金额
		sfRvdVo.setPsnVal(gdnVo.getPsnVal());//折让总金额
		sfRvdVo.setAddtCost(gdnVo.getAddtCost());//附加总金额
		sfRvdVo.setCost(gdnVo.getCost());//成本总金额
		sfRvdVo.setCreateType(CREATE_TYPE.AUTOMATION);//生成方式：自动
		sfRvdVo.setDataSource(gdnVo.getDataSource());
		sfRvdVo.setRemark(gdnVo.getRemark());
		sfRvdVo.setApproved(APPROVED.OLDERP);
		sfRvdVo.setCreateUser(gdnVo.getOprId());
		sfRvdVo.setLastModifiedUser(gdnVo.getOprId());
		sfRvdVo.setOsDocCode(gdnVo.getOsDocCode());
		String orginDocNum = O2oUtils.ToString(gdnVo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_NUM));
		String orginDocType = O2oUtils.ToString(gdnVo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_TYPE));
		// 三方下层调配或者下层转配 - 更新最原始的的现货单
    	if (AllocType.XX3Z.equals(gdnVo.getAllocType()) || AllocType.XXLS.equals(gdnVo.getAllocType())) {
    		orginDocNum = O2oUtils.ToString(gdnVo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_NUM_FIRST));
    		orginDocType = O2oUtils.ToString(gdnVo.getExtraParams().get(BASE_EXTRA.ORIGIN_DOC_TYPE_FIRST));
		}
		sfRvdVo.setOriginDocType(orginDocType);
		sfRvdVo.setOriginDocNum(orginDocNum);
		sfRvdVo.setOriginUnitId(commonIdCodeVo.getBfOrgVendeeId());
		Double taxRate = taxRateService.getTaxRateForNewErp(NewBillType.RVD.name(), sfRvdVo.getOriginDocType(), sfRvdVo.getOriginDocNum(), sfRvdVo.getVendeeCode(), sfRvdVo.getVenderCode());
		logger.warn("-----新ERP到货通知单税率 genSfGdnBySfDgn-----："+taxRate +", "+ NewBillType.RVD.name() +", "+ sfRvdVo.getOriginDocType()+", "+ sfRvdVo.getOriginDocNum()+", "+ sfRvdVo.getVendeeCode()+", "+ sfRvdVo.getVenderCode());
		sfRvdVo.setTaxRate(taxRate);
		//TODO可以放入缓存
		String amount_precision = newERPCommonService.getMainSysParamValue(BASE_EXTRA.AMOUNT_PRECISION); //获取新ERP金额的小数点精度
    	Double taxVal = (sfRvdVo.getTtlVal()/(100+taxRate))*taxRate;
    	BigDecimal b = new BigDecimal(taxVal);  
    	sfRvdVo.setTaxVal(b.setScale(Integer.parseInt(amount_precision), BigDecimal.ROUND_HALF_UP).doubleValue());
		sfRvdVo.setLastFactDispWarehId(gdnVo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用）
		sfRvdVo.setLastFactRcvWarehId(gdnVo.getLastFactRcvWarehId());// 最终收货仓库
		logger.warn(">>>>>>>最终收货仓（genSfRvdByGdn）："+gdnVo.getLastFactRcvWarehId()+" 最终发货仓："+gdnVo.getLastFactDispWarehId());
		return sfRvdVo;
	}
	
	/**
	 * 根据老ERP出库单详情生成到货通知单详情
	 * @param gdnVo
	 * @return
	 */
	public SfRvdVo genSfRvdByGdnDtl(GdnVo gdnVo,SfRvdVo sfRvdVo,GrnVo vo){
		List<SfRvdDtlVo> sfRvdDtlVos = new ArrayList<SfRvdDtlVo>();
		Set<String> set = new HashSet<String>();
		for(GdnDtlVo gdnDtlVo : gdnVo.getGdnDtlVos()){
			SfRvdDtlVo sfRvdDtlVo = new SfRvdDtlVo();
			sfRvdDtlVo.setSfRvdId(sfRvdVo.getId());
			sfRvdDtlVo.setProdCode(gdnDtlVo.getProdId());
			Long prodId = newERPCommonService.getProdIdByProdNum(gdnDtlVo.getProdId());
			sfRvdDtlVo.setProdId(prodId);
			sfRvdDtlVo.setUnitPrice(gdnDtlVo.getUnitPrice());
			sfRvdDtlVo.setQuantity(gdnDtlVo.getQuantity());
			sfRvdDtlVo.setDiscRate(gdnDtlVo.getDiscRate());
			sfRvdDtlVo.setTtlVal(gdnDtlVo.getQuantity() * gdnDtlVo.getUnitPrice() * gdnDtlVo.getDiscRate() / 100);
			sfRvdDtlVo.setRemark(gdnDtlVo.getRemark());
			sfRvdDtlVo.setDelivQty(gdnDtlVo.getQuantity());
			Long rcptLocId = null;
			for (GrnDtlVo grnDtlVo : vo.getGrnDtlVos()) {
				logger.warn("*******（genSfRvdByGdnDtl）：商品："+sfRvdDtlVo.getProdCode() +" 最终收货仓："+sfRvdVo.getLastFactRcvWarehId()+" 收货仓："+sfRvdVo.getRcvWarehCode()+" 收货货位："+grnDtlVo.getRcptLocId());
				if (gdnDtlVo.getProdId().equals(grnDtlVo.getProdId())) {
					if (SoaBillUtils.isNotBlank(grnDtlVo.getRcptLocId())) {
						if (sfRvdVo.getRcvWarehCode().equals(sfRvdVo.getLastFactRcvWarehId())) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("warehId", sfRvdVo.getBfOrgRcvWarehId());
							map.put("locCode", grnDtlVo.getRcptLocId());
							rcptLocId = newERPCommonService.getLocIdByLocCode(map);
							if (null == rcptLocId) {
								throw new RuntimeException(MessageFormat.format(rcvWarehLocIdError, vo.getWarehId(),grnDtlVo.getRcptLocId()));
							}
						}
					}
				}
			}	
			sfRvdDtlVo.setRcptLocId(rcptLocId);
			logger.warn("*******新ERP到货通知单（genSfRvdByGdnDtl）：商品："+sfRvdDtlVo.getProdCode() +" 发货货位："+sfRvdDtlVo.getLocCode()+" 收货货位："+sfRvdDtlVo.getRcptLocCode()+" 收货货位ID："+sfRvdDtlVo.getRcptLocId());
			sfRvdDtlVos.add(sfRvdDtlVo);
			set.add(gdnDtlVo.getProdId().substring(0, 6));
		}
		sfRvdVo.setProductCount((double) set.size());
		sfRvdVo.setSfRvdDtlVos(sfRvdDtlVos);
    	// 验证参数是否有效
    	String existProperty = newExistValidate.existValidateSfRvd(sfRvdVo);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
		return sfRvdVo;
	}
	
	/**
	 * 根据新ERP出库单生成到货通知单
	 * @param gdnVo
	 * @return
	 */
	public SfRvdVo genSfRvdBySfGdn(SfGdnVo sfGdnVo){
		CommonIdCodeVo commonIdCodeVo = new CommonIdCodeVo();
	    commonIdCodeVo.setBfOrgVendeeId(sfGdnVo.getBfOrgRcvUnitId());
	    commonIdCodeVo.setBfOrgVenderId(sfGdnVo.getBfOrgUnitId());
	    commonIdCodeVo.setBfOrgWarehId(sfGdnVo.getBfOrgWarehId());
	    commonIdCodeVo.setBfOrgRcvWarehId(sfGdnVo.getBfOrgRcvWarehId());
	    commonIdCodeVo.setBrandId(String.valueOf(sfGdnVo.getBrandId()));
		commonIdCodeVo = newERPCommonService.getCodeById(commonIdCodeVo);
		SfRvdVo sfRvdVo = new SfRvdVo();
		sfRvdVo.setUnitId(sfGdnVo.getBfOrgRcvUnitId());
		sfRvdVo.setBfOrgUnitId(sfGdnVo.getBfOrgUnitId());
		sfRvdVo.setVendeeCode(commonIdCodeVo.getBfOrgVendeeCode());
		sfRvdVo.setBfOrgRcvWarehId(sfGdnVo.getBfOrgRcvWarehId());
		sfRvdVo.setRcvWarehCode(commonIdCodeVo.getBfOrgRcvWarehCode());
		sfRvdVo.setFactRcvWarehId(sfGdnVo.getBfOrgRcvWarehId());
		sfRvdVo.setRcptMode(sfGdnVo.getDelivMode());
		sfRvdVo.setSrcDocType(NewBillType.GDN.name());
		sfRvdVo.setRcvState(O2OBillConstant.RCV_STATE_NEW.N); // 出库原因
		sfRvdVo.setSrcDocNum(sfGdnVo.getCode());
		sfRvdVo.setSrcUnitId(sfGdnVo.getBfOrgUnitId());
		sfRvdVo.setVenderCode(commonIdCodeVo.getBfOrgVenderCode());
		sfRvdVo.setWarehId(sfGdnVo.getBfOrgWarehId());
		sfRvdVo.setDispWarehCode(commonIdCodeVo.getBfOrgWarehCode());
		sfRvdVo.setBrandId(sfGdnVo.getBrandId());
		sfRvdVo.setBrandCode(commonIdCodeVo.getBrandCode());
		sfRvdVo.setDocState(NEW_DOC_STATE.PG_NEW);
		sfRvdVo.setProgress(PROGRESS.PG);
		sfRvdVo.setCurrency(sfGdnVo.getCurrency());
		sfRvdVo.setTtlQty(sfGdnVo.getTtlQty());
		sfRvdVo.setTtlVal(sfGdnVo.getTtlVal());
		sfRvdVo.setDelivQty(sfGdnVo.getTtlQty());
		sfRvdVo.setDelivVal(sfGdnVo.getTtlVal());
		sfRvdVo.setPsnVal(sfGdnVo.getPsnVal());
		sfRvdVo.setAddtCost(sfGdnVo.getAddtCost());//附加总金额
		sfRvdVo.setCost(sfGdnVo.getCost());//成本总金额
		sfRvdVo.setCreateType(CREATE_TYPE.AUTOMATION);//生成方式：自动
		sfRvdVo.setDataSource(sfGdnVo.getDataSource());
		sfRvdVo.setRemark(sfGdnVo.getRemark());
		sfRvdVo.setApproved(APPROVED.OLDERP);
		sfRvdVo.setCreateUser(sfGdnVo.getCreateUser());
		sfRvdVo.setLastModifiedUser(sfGdnVo.getLastModifiedUser());
		sfRvdVo.setOriginDocType(sfGdnVo.getOrigDocType());
		sfRvdVo.setOriginDocNum(sfGdnVo.getOrigDocCode());
		sfRvdVo.setOriginUnitId(sfGdnVo.getOrigUnitId());
		Double taxRate = taxRateService.getTaxRateForNewErp(NewBillType.RVD.name(), sfRvdVo.getOriginDocType(), sfRvdVo.getOriginDocNum(), sfRvdVo.getVendeeCode(), sfRvdVo.getVenderCode());
		logger.warn("-----新ERP到货通知单税率 genSfGdnBySfDgn-----："+taxRate +", "+ NewBillType.RVD.name() +", "+ sfRvdVo.getOriginDocType()+", "+ sfRvdVo.getOriginDocNum()+", "+ sfRvdVo.getVendeeCode()+", "+ sfRvdVo.getVenderCode());
		sfRvdVo.setTaxRate(taxRate);
		//TODO可以放入缓存
		String amount_precision = newERPCommonService.getMainSysParamValue(BASE_EXTRA.AMOUNT_PRECISION); //获取新ERP金额的小数点精度
    	Double taxVal = (sfRvdVo.getTtlVal()/(100+taxRate))*taxRate;
    	BigDecimal b = new BigDecimal(taxVal);  
    	sfRvdVo.setTaxVal(b.setScale(Integer.parseInt(amount_precision), BigDecimal.ROUND_HALF_UP).doubleValue());
		sfRvdVo.setOsDocCode(sfGdnVo.getOsDocCode());
		return sfRvdVo;
	}
	
	/**
	 * 根据新ERP出库单详情生成到货通知单详情
	 * @param gdnVo
	 * @return
	 */
	public SfRvdVo genSfRvdBySfGdnDtl(SfGdnVo sfGdnVo,SfRvdVo sfRvdVo,GrnVo vo){
		List<SfRvdDtlVo> sfRvdDtlVos = new ArrayList<SfRvdDtlVo>();
		Set<String> set = new HashSet<String>();
		for(SfGdnDtlVo sfGdnDtlVo : sfGdnVo.getSfGdnDtlVos()){
			SfRvdDtlVo sfRvdDtlVo = new SfRvdDtlVo();
			sfRvdDtlVo.setSfRvdId(sfRvdVo.getId());
			sfRvdDtlVo.setProdId(sfGdnDtlVo.getProdId());
			String prodCode = newERPCommonService.getProdNumByProdId(sfGdnDtlVo.getProdId());
			sfRvdDtlVo.setProdCode(prodCode);
			sfRvdDtlVo.setUnitPrice(sfGdnDtlVo.getUnitPrice());
			sfRvdDtlVo.setQuantity(sfGdnDtlVo.getQuantity());
			sfRvdDtlVo.setDiscRate(sfGdnDtlVo.getDiscRate());
			sfRvdDtlVo.setTtlVal(sfGdnDtlVo.getQuantity() * sfGdnDtlVo.getUnitPrice() * sfGdnDtlVo.getDiscRate() / 100);
			sfRvdDtlVo.setDelivQty(sfGdnDtlVo.getQuantity());
			Long rcptLocId = null;
			for (GrnDtlVo grnDtlVo : vo.getGrnDtlVos()) {
				if (prodCode.equals(grnDtlVo.getProdId())) {
					if (SoaBillUtils.isNotBlank(grnDtlVo.getRcptLocId())) {
						if (sfRvdVo.getRcvWarehCode().equals(vo.getLastFactRcvWarehId())) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("warehId", sfRvdVo.getBfOrgRcvWarehId());
							map.put("locCode", grnDtlVo.getRcptLocId());
							rcptLocId = newERPCommonService.getLocIdByLocCode(map);
							if (null == rcptLocId) {
								throw new RuntimeException(MessageFormat.format(rcvWarehLocIdError, vo.getWarehId(),grnDtlVo.getRcptLocId()));
							}
						}
					}
				}
			}
			sfRvdDtlVo.setRcptLocId(rcptLocId);
			logger.warn("*******新ERP到货通知单（genSfRvdBySfGdnDtl）：商品："+sfRvdDtlVo.getProdCode() +" 发货货位："+sfRvdDtlVo.getLocCode()+" 收货货位："+sfRvdDtlVo.getRcptLocCode()+" 收货货位ID："+sfRvdDtlVo.getRcptLocId());
			sfRvdDtlVos.add(sfRvdDtlVo);
			set.add(prodCode.substring(0, 6));
		}
		sfRvdVo.setProductCount((double) set.size());
		sfRvdVo.setSfRvdDtlVos(sfRvdDtlVos);
		sfRvdVo.setAllocType(vo.getAllocType());
    	// 验证参数是否有效
    	String existProperty = newExistValidate.existValidateSfRvd(sfRvdVo);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
		return sfRvdVo;
	}
	
	/**
     * 根据到货通知单生成新ERP入库单和入库单详情
     * @param sfRvdVo
     * @return
     */
    public SfGrnVo genSfGrn(SfRvdVo sfRvdVo){
//    	Integer i = newSoaJmsDubboService.insertByRVD(sfRvdVo);
//    	Integer j = newSoaJmsDubboService.insertDtlByRVD(sfRvdVo);
//    	SfGrnVo sfGrnVo = newSoaJmsDubboService.selectSfGrnById(sfRvdVo.getSfGrnId());
    	return null;
    }
}
