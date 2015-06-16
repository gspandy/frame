package mb.mba.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import mb.mba.core.entity.InventoryEntity;
import mb.mba.service.IShipperService;

import org.junit.Test;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * 类描述： 多货主库存查询测试
 * @author:余从玉
 * @version   1.0
 * @since 2015年6月5日           
 */
public class ShipperTest extends MbaTestBase {
	
	@Resource
	IShipperService shipperService;
	
	@Test
	public void testFindpage(){
		PageBounds page = new PageBounds(2,5); // 查询第二页，每页五条数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("acwarehCode", "HQ01W800");
		PageList<InventoryEntity> result = shipperService.findPage(page, params);
		for (InventoryEntity entity : result) {
			System.out.println("库存id："+entity.getId());
		}
	}
}
