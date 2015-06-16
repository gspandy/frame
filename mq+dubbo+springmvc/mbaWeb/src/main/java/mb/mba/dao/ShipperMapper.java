package mb.mba.dao;

import java.util.Map;

import mb.mba.core.entity.InventoryEntity;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * 类描述： 查询多货主库存
 * @author:余从玉
 * @version   1.0
 * @since 2015年6月5日           
 */
public interface ShipperMapper {
	
   	/**
   	 * 分页查询多货主库存
   	 * @param params 封装好的页面参数
   	 * @param page
   	 * @return 
   	 */
   	public PageList<InventoryEntity> findPage(Map<String, Object> params,PageBounds page);
}