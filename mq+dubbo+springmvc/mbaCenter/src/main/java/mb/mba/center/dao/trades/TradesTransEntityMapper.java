package mb.mba.center.dao.trades;

import mb.mba.core.entity.TradesTransEntity;

public interface TradesTransEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TradesTransEntity record);

    int insertSelective(TradesTransEntity record);

    TradesTransEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradesTransEntity record);

    int updateByPrimaryKey(TradesTransEntity record);
}