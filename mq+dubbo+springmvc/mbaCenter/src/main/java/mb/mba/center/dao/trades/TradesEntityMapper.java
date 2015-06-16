package mb.mba.center.dao.trades;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import mb.mba.core.entity.TradesEntity;

public interface TradesEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TradesEntity record);

    int insertSelective(TradesEntity record);

    TradesEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradesEntity record);

    int updateByPrimaryKey(TradesEntity record);

	int queryTradesListCount(TradesEntity entity);

	List<TradesEntity> queryTradesList(TradesEntity entity, RowBounds rb);
}