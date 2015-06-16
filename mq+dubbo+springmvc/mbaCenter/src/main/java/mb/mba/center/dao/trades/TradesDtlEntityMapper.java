package mb.mba.center.dao.trades;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import mb.mba.core.entity.TradesDtlEntity;
import mb.mba.core.entity.TradesEntity;

public interface TradesDtlEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TradesDtlEntity record);
    
    int insertSelective(TradesDtlEntity record);

    TradesDtlEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradesDtlEntity record);

    int updateByPrimaryKey(TradesDtlEntity record);

	int updateDtlsPrice(TradesDtlEntity record);

	int updateDtlsDisrate(TradesDtlEntity record);

	int updateDtlsTaxRate(TradesDtlEntity record);

	List<TradesDtlEntity> queryTradesDtlList(TradesEntity record, RowBounds rb);

	int queryTradesDtlListCount(TradesEntity record);
	
	int bathinsert(List<TradesDtlEntity> records);

}