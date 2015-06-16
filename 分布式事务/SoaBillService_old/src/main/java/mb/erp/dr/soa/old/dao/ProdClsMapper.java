package mb.erp.dr.soa.old.dao;

import java.util.Map;


public interface ProdClsMapper {
	/**
	 * 根据商品读取品牌
	 * @param map
	 * @return
	 */
   public String selectBrandByProdID(Map map);
   
   /**
    * 根据商品获取吊牌价格
    * @param map
    * @return
    */
   public Double selectOnBrandPrice(Map map);
}