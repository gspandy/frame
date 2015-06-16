package mb.mba.center.dao;

import mb.mba.core.entity.PartnerShipEntity;

public interface PartnerShipEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PartnerShipEntity record);

    int insertSelective(PartnerShipEntity record);

    PartnerShipEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PartnerShipEntity record);

    int updateByPrimaryKey(PartnerShipEntity record);
}