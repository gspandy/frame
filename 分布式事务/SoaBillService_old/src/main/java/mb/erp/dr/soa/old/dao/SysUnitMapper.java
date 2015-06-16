package mb.erp.dr.soa.old.dao;

import java.util.Map;

import mb.erp.dr.soa.old.vo.SysUnitVo;


public interface SysUnitMapper {
	
	/**
	 * 查询组织详细信息
	 * @param unitId
	 * @return
	 */
	public SysUnitVo getByPrimaryKey(String unitId);
	
	/**
	 * 查询上级组织
	 * @param map
	 * @return
	 */
    public String selectOwnerID(Map map);
    
	/**
	 * 查询贸易供货方和上级组织
	 * @param map
	 * @return
	 */
    public SysUnitVo selectCusIDAndOwnerID(Map map);
    
}