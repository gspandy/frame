package mb.erp.dr.o2o.service.impl.common;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;
import mb.erp.dr.soa.old.service.bill.CommonService;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.utils.SoaBillUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 通用接口服务
 * 包含接口：获取主键编号、获取账户信息
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         TaxRateService
 * @since       全流通改造
 */
@Service
public class TaxRateService {
	private final Logger logger = LoggerFactory.getLogger(TaxRateService.class);
    @Resource
    private NewERPCommonService newERPCommonService;
    @Resource
    private CommonService commonService;
    
	/**
	 * 新ERP获取税率
	 * @param docType 单据类型
	 * @param srcDocType 源头/原始 单据类型（除了docType是出库单，入库单的为原始单据类型，其他都为源头单据类型）
	 * @param docNum 单据编号
	 * @param vendeeId 购货方组织编码
	 * @param venderId 供货方组织编码
	 *  srcDocType赋值说明：
     *  除了docType是出库单，入库单的为原始单据类型，其他都为源头单据类型；
     *   
     *   docNum赋值说明： (根据docType分别处理）
     *   1、交货单：调配出库，docNum为源头单据(调配单)编号 
     *   2、到货通知单：采购入库，docNum为源头单据编号
     *   3、到货通知单：订货入库，docNum为源头单据编号
     *   4、出库单：docNum为交货单编号
     *   5、入库单：docNum为到货通知单编号
     *   6、其它情况就为docType对应的单据编号
	 * @return
	 */
	public Double getTaxRateForNewErp(String docType, String srcDocType,
			String docNum, String vendeeId, String venderId) {
		Double defaultTaxRate = 17d;
		Double taxRate = 0d;
		if (BillType.TFO.name().equals(docType)) {
			taxRate = 0d;
		}else if(BillType.BGR.name().equals(docType)){
			if (vendeeId.equals(venderId)) {
				// 直营门店退货
                taxRate = 0d;
			}else {
				// 根据购货方到税率表中获取
				taxRate = commonService.selectTaxRate(vendeeId);
			}
		}else if(BillType.IDT.name().equals(docType)){
			if (vendeeId.equals(venderId)) {
				// 直营门店退货
                taxRate = 0d;
			}else {
				// 根据购货方到税率表中获取
				taxRate = commonService.selectTaxRate(vendeeId);
			}
		}else if(BillType.TBN.name().equals(docType)){
			 //根据购货方到税率表中获取税率
			taxRate = commonService.selectTaxRate(vendeeId);
		}else if(NewBillType.DGN.name().equals(docType)){
			if (BillType.TFO.name().equals(srcDocType) || (BillType.IDT.name().equals(srcDocType) && vendeeId.equals(venderId))
					|| (BillType.BGR.name().equals(srcDocType) && vendeeId.equals(venderId))) {
				//调拨出库TRAN(TFO)，直营门店配发SHOR(IDT)，直营门店退货SHCR(BGR)
                taxRate = 0d;
			}else if(BillType.TBN.name().equals(srcDocType)){
				//从源头单据（调配单）获取税率
                taxRate = newERPCommonService.selectTaxRateFromDrTbn(docNum);
			}else {
				// 根据发货方到老ERP税率表中获取
				taxRate = commonService.selectTaxRate(venderId);
			}
		}else if(NewBillType.RVD.name().equals(docType)){
			if (BillType.TFO.name().equals(srcDocType) || (BillType.IDT.name().equals(srcDocType) && vendeeId.equals(venderId))
					|| (BillType.BGR.name().equals(srcDocType) && vendeeId.equals(venderId))) {
				//调拨入库TRAN(TFO)，直营门店订货入库SHOR(IDT)，直营门店退货入库SHCR(BGR)
                taxRate = 0d;
			}else if(BillType.IDT.name().equals(srcDocType) && !vendeeId.equals(venderId)){
				 //订货入库RCPT_MODE:AGOR:根据源头单据获取税率TAX_RATE
                taxRate = newERPCommonService.selectTaxRateFromSfIdt(docNum);
			}else {
				 // 根据购货方到老ERP税率表中获取
                taxRate = commonService.selectTaxRate(vendeeId);
			}
		}else if (NewBillType.GDN.name().equals(docType)) {
			if (NewBillType.DGN.name().equals(srcDocType)) {
				// 获取交货单SF_DGN上的税率
				taxRate = newERPCommonService.selectTaxRateFromSfDgn(docNum);
			}else {
				//根据venderId到税率表中获取税率
                taxRate = commonService.selectTaxRate(venderId);
			}
		}else if (NewBillType.GRN.name().equals(docType)) {
			if (NewBillType.RVD.name().equals(srcDocType)) {
				// 获取到货通知单SF_RVD上的税率
				taxRate = newERPCommonService.selectTaxRateFromSfRvd(docNum);
			}else {
				//根据venderId到税率表中获取税率
                taxRate = commonService.selectTaxRate(venderId);
			}
		}else {
			taxRate = commonService.selectTaxRate(venderId);
		}
		if (taxRate < 0) {//无法从数据库获取税率
			//获取新ERP系统参数税率SYS_PARAMETER
			String taxRateStr = newERPCommonService.getMainSysParamValue("TAX_RATE_VALUE_ADDED");
			taxRate = SoaBillUtils.isNotBlank(taxRateStr) ? Double.parseDouble(taxRateStr) : defaultTaxRate;
		}
		return taxRate;
	}
}
