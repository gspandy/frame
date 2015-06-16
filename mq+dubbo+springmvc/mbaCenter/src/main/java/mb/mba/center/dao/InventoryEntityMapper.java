package mb.mba.center.dao;

import mb.mba.core.bean.InventoryHelper;
import mb.mba.core.entity.InventoryEntity;

public interface InventoryEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InventoryEntity record);

    int insertSelective(InventoryEntity record);

    InventoryEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InventoryEntity record);

    int updateByPrimaryKey(InventoryEntity record);

	InventoryEntity queryInventory(InventoryHelper helper);

	int updateInventoryValue(InventoryHelper helper);
}