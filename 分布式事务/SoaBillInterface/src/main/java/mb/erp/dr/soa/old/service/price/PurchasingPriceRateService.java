package mb.erp.dr.soa.old.service.price;

import mb.erp.dr.soa.vo.common.MsgVo;

/**
 * 采购价格服务 - 接口
 * 其中公开方法即对外开放的服务，包括根据商品编码获取采购价格折率相关操作
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         PurchasingPriceRateService
 * @since       全流通改造
 */
public interface PurchasingPriceRateService {
	/**
	 * 	获取采购价格
	 */
	public MsgVo getPurchasingPriceRateByProdId(String prod_id, String unit_id, String currency) ;
	
	/**
	 * 根据传入的商品11位码的ID来查询供应商往来价格(采购价格)，折率默认为100
	 */
	public MsgVo getPurchasePriceByProdId(int prod_id, int vendeeId,int venderId,String currency) ;
	
}
