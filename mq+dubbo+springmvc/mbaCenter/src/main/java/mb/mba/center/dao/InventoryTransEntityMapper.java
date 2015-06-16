package mb.mba.center.dao;

import mb.mba.core.entity.InventoryTransEntity;

public interface InventoryTransEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InventoryTransEntity record);

    int insertSelective(InventoryTransEntity record);

    InventoryTransEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InventoryTransEntity record);

    int updateByPrimaryKey(InventoryTransEntity record);
}