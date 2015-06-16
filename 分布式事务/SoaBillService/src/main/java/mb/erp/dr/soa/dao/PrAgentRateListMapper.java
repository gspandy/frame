package mb.erp.dr.soa.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.vo.PrWprcListDtlVo;


public interface PrAgentRateListMapper {
	
	/**
	 * 获取代理商品牌折率（根据款）
	 * @param map
	 * @return
	 */
	public Double selectAgentRateByProdCls(Map map);
	
	public Double selectAgentRateByProdID(Map map);
	
	public List<PrWprcListDtlVo> bulkGetAgentBrandRateByProdID(Map map);
}