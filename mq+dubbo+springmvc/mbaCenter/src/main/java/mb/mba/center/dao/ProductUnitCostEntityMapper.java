package mb.mba.center.dao;

import mb.mba.core.entity.ProductUnitCostEntity;

public interface ProductUnitCostEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductUnitCostEntity record);

    int insertSelective(ProductUnitCostEntity record);

    ProductUnitCostEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductUnitCostEntity record);

    int updateByPrimaryKey(ProductUnitCostEntity record);
}