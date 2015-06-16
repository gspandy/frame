package mb.erp.dr.soa.old.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.old.vo.WprcLstDtlVo;


public interface AgentBrandRateListMapper {
	/**
	 * 获取代理商品牌折率（根据款）
	 * @param map
	 * @return
	 */
	public Double selectObjectByProdCls(Map map);
	
	/**
	 * 根据商品编码获取老ERP代理商折率
	 * @param map
	 * @return
	 */
	public Double selectObject(Map map);
	
	public List<WprcLstDtlVo> bulkGetAgentBrandRateOld(Map map);
}