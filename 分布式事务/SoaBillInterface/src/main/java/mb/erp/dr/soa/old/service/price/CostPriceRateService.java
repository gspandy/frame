package mb.erp.dr.soa.old.service.price;

import java.util.List;

import mb.erp.dr.soa.vo.common.MsgListVo;
import mb.erp.dr.soa.vo.common.MsgVo;

/**
 * 成本价格服务 - 接口
 * 其中公开方法即对外开放的服务，包括根据商品编码获取成本价格折率和根据批量商品编码获取成本价格折率相关操作
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         CostPriceRateService
 * @since       全流通改造
 */
public interface CostPriceRateService {
	/**
	 * 	获取成本价格
	 */
	public MsgVo getCostPriceRateByProdId(String prod_id, String unit_id) ;
	
	public MsgVo getCostPriceRateByProd_Id(String prod_id, String unit_id, String wareh_id) ;
	
	/**
	 * 	批量获取成本价格
	 */
	public List<MsgListVo> bulkGetCostPriceRateByProdId(List<String> prodIdList, String unit_code) ;
	
	/**
	 * 	根据仓库ID批量获取成本价格
	 */
	public List<MsgListVo> bulkGetCostPriceRateByProdIdWarehCode(List<String> prodIdList, String unit_code, String wareh_id) ;
	
}
