package mb.mba.center.dao;

import mb.mba.core.entity.TaxrateAllEntity;

public interface TaxrateAllEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TaxrateAllEntity record);

    int insertSelective(TaxrateAllEntity record);

    TaxrateAllEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaxrateAllEntity record);

    int updateByPrimaryKey(TaxrateAllEntity record);

	TaxrateAllEntity selectByUnitId(String unitId);
}