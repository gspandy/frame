package mb.erp.dr.soa.old.service.price;

import mb.erp.dr.soa.vo.common.MsgVo;

/**
 * 吊牌价格服务 - 接口
 * 其中公开方法根据商品获取吊牌价格 查询代理商折率
 * @author     郭明帅
 * @version    1.0, 2014-11-12
 * @see         BrandPriceService
 * @since       全流通改造
 */
public interface BrandPriceService {
	/**
	 * 获取商品的吊牌价格
	 * @return
	 * @
	 */
	public MsgVo getProductOnBrandPrice(String ProdNum) ;
	
}
