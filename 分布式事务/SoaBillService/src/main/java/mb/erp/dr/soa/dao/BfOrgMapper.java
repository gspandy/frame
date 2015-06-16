package mb.erp.dr.soa.dao;

import java.util.Map;

import mb.erp.dr.soa.vo.BfOrgVo;

/**
 * 新ERP组织表数据接口
 * 包含接口：
 * @author     郭明帅
 * @version    1.0, 2014-11-17
 * @see         BfOrgMapper
 * @since       全流通改造
 */
public interface BfOrgMapper {
	
	/**
	 * 根据code查询组织详情
	 * @param code
	 * @return
	 */
	public BfOrgVo getBfOrgByCode(String code);
	
	/**
	 * 查询上级组织
	 * @param map
	 * @return
	 */
    public String selectOwnerId(Map map);
    
	/**
	 * 获取门店的上级组织
	 * @param map
	 * @return
	 */
    public String selectAgentID(Map map);
    
}