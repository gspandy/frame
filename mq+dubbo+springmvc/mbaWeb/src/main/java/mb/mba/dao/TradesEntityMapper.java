package mb.mba.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import mb.mba.core.entity.TradesEntity;

public interface TradesEntityMapper {
	
    TradesEntity selectByPrimaryKey(Long id);

	int queryTradesListCount(TradesEntity entity);

	List<TradesEntity> queryTradesList(TradesEntity entity, RowBounds rb);
}