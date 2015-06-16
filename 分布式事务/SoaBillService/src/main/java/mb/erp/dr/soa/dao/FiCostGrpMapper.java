package mb.erp.dr.soa.dao;

import mb.erp.dr.soa.vo.FiCostGrpVo;


public interface FiCostGrpMapper {
	/**
	 * 仓库是否参与成本
	 * @param unitId
	 * @return
	 */
	public Integer isHaveCost(Long unitId);
	
	/**
	 * 根据仓库获取成本组
	 * @param unitId
	 * @return
	 */
	public FiCostGrpVo getByWarehId(Long unitId);
}