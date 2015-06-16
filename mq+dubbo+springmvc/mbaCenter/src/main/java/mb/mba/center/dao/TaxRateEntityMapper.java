package mb.mba.center.dao;

import mb.mba.core.entity.TaxRateEntity;

public interface TaxRateEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TaxRateEntity record);

    int insertSelective(TaxRateEntity record);

    TaxRateEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaxRateEntity record);

    int updateByPrimaryKey(TaxRateEntity record);
}