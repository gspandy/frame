package mb.mba.center.service.proxy;

import javax.annotation.Resource;

import mb.erp.dr.soa.old.service.price.BrandPriceService;
import mb.erp.dr.soa.old.service.price.CostPriceRateService;
import mb.erp.dr.soa.old.service.price.PurchasingPriceRateService;
import mb.erp.dr.soa.old.service.price.RetailPriceRateService;
import mb.erp.dr.soa.old.service.price.SettlementPriceRateService;
import mb.erp.dr.soa.vo.common.MsgVo;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 价格服务接口
 * 包含接口：查询吊牌价，成本价，采购价，零售价，结算价
 * @author     余从玉
 * @version    1.0, 2015-6-4
 * @see         PriceService
 * @since       账务中心
 */
@Service
public class PriceService {

	@Resource
	private BrandPriceService brandPriceService;
	@Resource
	private CostPriceRateService costPriceRateService;
	@Resource
	private PurchasingPriceRateService purchasingPriceRateService;
	@Resource
	private RetailPriceRateService retailPriceRateService;
	@Resource
	private SettlementPriceRateService settlementPriceRateService;
	
	/**
	 * 吊牌价格 - 获取商品的吊牌价格
	 * @param ProdNum 商品编码
	 * @return
	 */
	public MsgVo getProductOnBrandPrice(String ProdNum){
		return brandPriceService.getProductOnBrandPrice(ProdNum);
	}
	
	/**
	 * 成本价 - 根据商品编码和组织编码来查询
	 * @param prodId	商品编码
	 * @param unitId		组织编码
	 * @return
	 */
	public MsgVo getCostPriceRateByProdId(String prodId, String unitId){
		return costPriceRateService.getCostPriceRateByProdId(prodId, unitId);
	 }
	
	/**
	 * 成本价 - 根据商品编码，组织编码和仓库编码来查询
	 * @param prodId	商品编码
	 * @param unitId		组织编码
	 * @param warehId	仓库编码
	 * @return
	 */
	public MsgVo getCostPriceRateByProdIdAndWarehId(String prodId, String unitId,String warehId){
		return costPriceRateService.getCostPriceRateByProd_Id(prodId, unitId,warehId);
	 }
	
	/**
	 * 采购价 - 根据商品编码和组织编码查询
	 * @param prodId	商品编码
	 * @param unitId		组织编码
	 * @param currency 币种 ， （RMB）
	 * @return
	 */
	public MsgVo getPurchasingPriceRateByProdId(String prodId, String unitId,String currency){
		return purchasingPriceRateService.getPurchasingPriceRateByProdId(prodId, unitId, currency);
	}
	
	/**
	 * 零售价 - 根据商品编码和组织编码查询
	 * @param prodId	商品编码
	 * @param unitId		组织编码
	 * @param currency 币种 ， （RMB）
	 * @return
	 */
	public MsgVo getRetailPriceRateByProdId(String prodId, String unitId,String currency)  {
		return retailPriceRateService.getRetailPriceRateByProdId(prodId, unitId, currency);
	}
	
	/**
	 * 结算价 - 根据商品编码和供货方，购货方编码查询
	 * @param prodId	商品编码
	 * @param unitId		发货组织编码
	 * @param cpdUnitId	收货组织编码
	 * @param currency 币种 ， （RMB）
	 * @return
	 */
	public MsgVo getSettlementPriceRateByProdId(String prodId, String unitId,
			String cpdUnitId, String currency)  {
		return settlementPriceRateService.getSettlementPriceRateByProdId(prodId, unitId, cpdUnitId, currency);
	}
}
