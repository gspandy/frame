package mb.mba.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import mb.mba.core.entity.TradesDtlEntity;
import mb.mba.core.entity.TradesEntity;

public interface TradesDtlEntityMapper {

    TradesDtlEntity selectByPrimaryKey(Long id);

	List<TradesDtlEntity> queryTradesDtlList(TradesEntity record, RowBounds rb);

	int queryTradesDtlListCount(TradesEntity record);
	
}