package mb.erp.dr.soa.old.service.dubbo;

import java.util.Map;

/**
 * 新erp需要调用到老erp的价格服务
 * 
 * @author     余从玉
 * @version    1.0, 2014-12-9
 * @see         SoaPriceDubboService
 * @since       全流通改造
 */
public interface SoaPriceDubboService {

	/**
	 * 根据商品编码获取老ERP代理商折率
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Double selectObject(Map map);
}
