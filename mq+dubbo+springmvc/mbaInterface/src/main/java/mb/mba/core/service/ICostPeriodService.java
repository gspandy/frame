package mb.mba.core.service;

import java.util.List;

import mb.mba.core.bean.Message;
import mb.mba.core.bean.QueryCostAccountingPeriodBean;
import mb.mba.core.entity.CostAccountingPeriodEntity;
import mb.mba.core.entity.TradesEntity;
import mb.mba.core.exception.MbaException;


/**
 * 封账服务
 * 
 * @author czj
 *
 */
public interface ICostPeriodService {
	
	/**
	 * 出入库检查是否封账
	 * @return Message success表示未封账
	 */
	public Message checkCostPeriod(TradesEntity entity) throws MbaException;

	/**
	 * 添加会计期间信息 页面添加用
	 * @param costAccountingPeriodEntity
	 * @return
	 */
	public Message addCostPeriod(CostAccountingPeriodEntity costAccountingPeriodEntity);

	/**
	 * 修改会计期间信息 页面修改用
	 * @param accountingPeriodEntities
	 * @return
	 */
	public Message modifyCostPeriod(CostAccountingPeriodEntity costAccountingPeriodEntity);

	/**
	 * 页面返回会计期间信息
	 * @return
	 */
	public List<CostAccountingPeriodEntity> queryCostPeriod(QueryCostAccountingPeriodBean queryCostAccountingPeriodBean);
}
