package mb.mba.core.service;

import java.util.List;

import mb.mba.core.bean.CostAccountBean;
import mb.mba.core.bean.Message;
import mb.mba.core.bean.QueryCostAccountingGroupBean;
import mb.mba.core.entity.CostAccountingGroupEntity;
import mb.mba.core.entity.TradesEntity;
import mb.mba.core.exception.MbaException;

/**
 * 成本核算接口
 * 
 * @author czj
 *
 */
public interface ICostAccountService {
	
	/**
	 * 出库成本核算
	 */
	public Message calculationByDeliverStorage(TradesEntity entity) throws MbaException;
	
	/**
	 * 入库成本核算
	 * @param entity
	 * @return
	 */
	public Message calculationByAddStorage(TradesEntity entity) throws MbaException;
	
	/**
	 * 页面手动触发、进行成本核算
	 * @return
	 */
	public Message calculationByManual(CostAccountBean accountBean) throws MbaException;
	
	/*自动触发
	public Message calculationByAuto() throws MbaException;
	*/

	/**
	 * 新增成本组
	 * @param costAccountingGroupEntity
	 * @return
	 */
	public Message addCostAccountingGroup(CostAccountingGroupEntity costAccountingGroupEntity);

	/**
	 * 更新成本组
	 * @param costAccountingGroupEntity
	 * @return
	 */
	public Message modifyCostAccountingGroup(CostAccountingGroupEntity costAccountingGroupEntity);

	/**
	 * 删除成本组
	 * @param costAccountingGroupEntity
	 * @return
	 */
	public Message deleteCostAccountingGroup(CostAccountingGroupEntity costAccountingGroupEntity);

	/**
	 * 查询成本组
	 * @param queryCostAccountingGroupBean
	 * @return
	 */
	public List<CostAccountingGroupEntity> queryCostAccountingGroup(QueryCostAccountingGroupBean queryCostAccountingGroupBean);
}
