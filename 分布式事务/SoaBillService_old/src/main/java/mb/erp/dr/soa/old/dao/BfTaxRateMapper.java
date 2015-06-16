package mb.erp.dr.soa.old.dao;

import java.util.Map;


public interface BfTaxRateMapper {
	  /**
	   * 根据组织查询税率
	   * @param map
	   * @return
	   */
	  public Double selectTaxRate(Map map);
}