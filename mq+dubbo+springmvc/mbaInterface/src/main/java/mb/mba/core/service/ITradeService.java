package mb.mba.core.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import mb.mba.core.bean.Message;
import mb.mba.core.entity.TradesDtlEntity;
import mb.mba.core.entity.TradesEntity;
import mb.mba.core.exception.MbaException;

/**
 * @Description:出入库交易服务
 * @author sun@mb.com  
 * @date 2015年5月29日
 * @version
 */
public interface ITradeService {

	/**
	 * 入库交易
	 * @param entity
	 * @param dtlEntitys
	 * @return
	 * @throws MbaException
	 */
	public Message tradeInInventory(TradesEntity entity,List<TradesDtlEntity> dtlEntitys) throws MbaException;
	/**
	 * 出库交易
	 * @param entity
	 * @param dtlEntitys
	 * @return
	 * @throws MbaException
	 */
	public Message tradeOutInventory(TradesEntity entity,List<TradesDtlEntity> dtlEntitys) throws MbaException;

	/**
	 * 添加出入库交易流水信息
	 * @param tradeTransDto
	 * @return
	 */
	public Message addTradeAndTrans(TradesEntity entity) throws MbaException;
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
