package mb.mba.center.dao;

import mb.mba.core.entity.ProductCalculationAccuracyEntity;

public interface ProductCalculationAccuracyEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductCalculationAccuracyEntity record);

    int insertSelective(ProductCalculationAccuracyEntity record);

    ProductCalculationAccuracyEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductCalculationAccuracyEntity record);

    int updateByPrimaryKey(ProductCalculationAccuracyEntity record);
}