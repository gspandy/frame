package mb.mba.center.dao;

import mb.mba.core.entity.CostAccountingWarehouseEntity;

public interface CostAccountingWarehouseEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CostAccountingWarehouseEntity record);

    int insertSelective(CostAccountingWarehouseEntity record);

    CostAccountingWarehouseEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CostAccountingWarehouseEntity record);

    int updateByPrimaryKey(CostAccountingWarehouseEntity record);
}