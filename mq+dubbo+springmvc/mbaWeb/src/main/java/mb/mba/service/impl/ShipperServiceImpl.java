package mb.mba.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import mb.mba.core.entity.InventoryEntity;
import mb.mba.dao.ShipperMapper;
import mb.mba.service.IShipperService;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * 类描述： 多货主库存查询
 * @author:余从玉
 * @version   1.0
 * @since 2015年6月5日           
 */
@Service
public class ShipperServiceImpl implements IShipperService {

	@Resource
	private ShipperMapper shipperMapper;
	
	/**
   	 * 分页查询多货主库存
   	 * @param params 封装好的页面参数
   	 * @param page
   	 * @return 
   	 */
	public PageList<InventoryEntity> findPage(PageBounds page,Map<String, Object> params) {
		// 仓库，货主，批次，商品编码
		// warehCode,goodsCode,bathInfo,acwarehCode
		return shipperMapper.findPage(params, page);
	}

}
