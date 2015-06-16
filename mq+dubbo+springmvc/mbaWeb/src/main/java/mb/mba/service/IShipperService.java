package mb.mba.service;

import java.util.Map;

import mb.mba.core.entity.InventoryEntity;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * 类描述： 多货主库存查询
 * @author:余从玉
 * @version   1.0
 * @since 2015年6月5日           
 */
public interface IShipperService {

	/**
   	 * 分页查询多货主库存
   	 * @param params 封装好的页面参数
   	 * @param page  -- new PageBounds( int page, int limit, Order... order);
	 * @return: List<InventoryEntity>
	 */
	public PageList<InventoryEntity> findPage(PageBounds page,Map<String, Object> params);
	
}
