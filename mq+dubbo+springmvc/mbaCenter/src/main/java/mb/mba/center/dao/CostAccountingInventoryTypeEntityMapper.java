package mb.mba.center.dao;

import mb.mba.core.entity.CostAccountingInventoryTypeEntity;

public interface CostAccountingInventoryTypeEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CostAccountingInventoryTypeEntity record);

    int insertSelective(CostAccountingInventoryTypeEntity record);

    CostAccountingInventoryTypeEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CostAccountingInventoryTypeEntity record);

    int updateByPrimaryKey(CostAccountingInventoryTypeEntity record);
}