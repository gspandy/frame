package mb.mba.service.impl;

import java.util.List;

import javax.annotation.Resource;

import mb.mba.core.entity.TradesDtlEntity;
import mb.mba.core.entity.TradesEntity;
import mb.mba.dao.TradesDtlEntityMapper;
import mb.mba.dao.TradesEntityMapper;
import mb.mba.service.ITradeService;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

/**
 * @描述: 出入库交易服务实现
 * @author sun@mb.com  
 * @date 2015年5月29日
 * @version
 */
@Service(value="iTradeService")
public class TradeServiceImpl implements ITradeService {

	@Resource
	TradesEntityMapper tradesEntityMapper;
	@Resource
	TradesDtlEntityMapper tradesDtlEntityMapper;
	
	@Override
	public List<TradesEntity> queryTradesListByParams(TradesEntity entity,
			RowBounds rb) {
		return tradesEntityMapper.queryTradesList(entity,rb);
	}

	@Override
	public int queryTradesListCountByParams(TradesEntity entity) {
		return tradesEntityMapper.queryTradesListCount(entity);
	}

	@Override
	public List<TradesDtlEntity> queryTradesDtlListByParams(
			TradesEntity entity, RowBounds rb) {
		return tradesDtlEntityMapper.queryTradesDtlList(entity,rb);
	}

	@Override
	public int queryTradesDtlListCountByParams(TradesEntity entity) {
		return tradesDtlEntityMapper.queryTradesDtlListCount(entity);
	}
}
