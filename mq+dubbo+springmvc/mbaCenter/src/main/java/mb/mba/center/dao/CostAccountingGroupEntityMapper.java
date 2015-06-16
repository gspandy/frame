package mb.mba.center.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import mb.mba.core.entity.CostAccountingGroupEntity;

public interface CostAccountingGroupEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CostAccountingGroupEntity record);

    int insertSelective(CostAccountingGroupEntity record);

    CostAccountingGroupEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CostAccountingGroupEntity record);

    int updateByPrimaryKey(CostAccountingGroupEntity record);

    /**
     * 查询当前组织是否有成本组
     * @param unitCode
     * @return
     */
	public Integer isHaveCostGroup(@Param("code")String unitCode);

	/**
	 * 根据仓库编码获取成本组
	 * @param warehCode
	 * @return
	 */
	public CostAccountingGroupEntity getByWarehCode(@Param("code")String warehCode);

	/**
	 * 根据出库单ID查询成本组
	 * @param id
	 * @return
	 */
	public CostAccountingGroupEntity getCostGrpByDelivId(Long id);

	/**
	 * 根据入库单ID查询成本组
	 * @param id
	 * @return
	 */
	public CostAccountingGroupEntity getCostGrpByAddId(Long id);
	

	/**
	 * 判断出库单的出库方式是否满足成本核算
	 * @param map
	 * @return
	 */
	public Integer isDelivTypeConfirm(Map<String, Object> map);

	/**
	 * 判断入库单的入库方式是否满足成本核算
	 * @param map
	 * @return
	 */
	public Integer isAddTypeConfirm(Map<String, Object> map);

	/**
	 * 根据ID查询是否有有效的成本组
	 * @param id
	 * @return
	 */
	public CostAccountingGroupEntity selectValidCostGrpById(Long id);
}