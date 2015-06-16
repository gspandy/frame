package mb.mba.center.dao;

import java.util.List;

import mb.mba.core.entity.CostAccountingPeriodEntity;

public interface CostAccountingPeriodEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CostAccountingPeriodEntity record);

    int insertSelective(CostAccountingPeriodEntity record);

    CostAccountingPeriodEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CostAccountingPeriodEntity record);

    int updateByPrimaryKey(CostAccountingPeriodEntity record);

    /**
     * 根据成本组Id获取会计期间
     * @param id
     * @return
     */
	public List<CostAccountingPeriodEntity> getByCostId(Long id);

	/**
	 * 根据ID查询有效的会计期间
	 * @param id
	 * @return
	 */
	public CostAccountingPeriodEntity selectValidPeriodById(Long id);
}