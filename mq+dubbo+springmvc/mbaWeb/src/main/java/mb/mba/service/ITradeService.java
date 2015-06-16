package mb.mba.service;

import java.util.List;

import mb.mba.core.entity.TradesDtlEntity;
import mb.mba.core.entity.TradesEntity;

import org.apache.ibatis.session.RowBounds;

/**
 * @Description:出入库交易服务
 * @author sun@mb.com  
 * @date 2015年5月29日
 * @version
 */
public interface ITradeService {

	/**
	 * 查询交易信息
	 * @param entity
	 * @param rb
	 * @return list
	 */
	public List<TradesEntity> queryTradesListByParams(TradesEntity entity,RowBounds rb);
	public int queryTradesListCountByParams(TradesEntity entity);
	/**
	 * 查询交易明细
	 * @param entity
	 * @param rb
	 * @return list
	 */
	public List<TradesDtlEntity> queryTradesDtlListByParams(TradesEntity entity,RowBounds rb);
	public int queryTradesDtlListCountByParams(TradesEntity entity);
}
