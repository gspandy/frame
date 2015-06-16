package mb.mba.center.dao;

import org.apache.ibatis.annotations.Param;

import mb.mba.core.entity.TaxratePerEntity;

public interface TaxratePerEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TaxratePerEntity record);

    int insertSelective(TaxratePerEntity record);

    TaxratePerEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaxratePerEntity record);

    int updateByPrimaryKey(TaxratePerEntity record);

	TaxratePerEntity selectByUnitIdAndCpdUnitId(@Param(value = "unitId") String unitId,@Param(value = "cpdunitId") String cpdunitId);
}