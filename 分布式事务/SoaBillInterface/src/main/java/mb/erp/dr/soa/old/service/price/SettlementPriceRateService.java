package mb.erp.dr.soa.old.service.price;

import java.util.List;

import mb.erp.dr.soa.vo.common.MsgListVo;
import mb.erp.dr.soa.vo.common.MsgVo;

/**
 * 结算价格服务 - 接口
 * 其中公开方法即对外开放的服务，包括根据商品编码获取结算价格折率和根据批量商品编码获取结算价格折率相关操作
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         SettlementPriceRateService
 * @since       全流通改造
 */
public interface SettlementPriceRateService {
	
	/**
	 * 根据传入的商品款6位编码来查询传出价格和折率  结算价格
	 */
	public MsgVo getSettlementPriceRateByProdClsNum(String prod_cls_num, String unit_code, String cpd_unit_code, String currency) ;
	
	public MsgVo getSettlementPriceRateByProdId(String prod_id, String unit_id, String cpd_unit_id, String currency) ;
	
	/**
	 * 批量获取结算价格(商品款)
	 */
	public List<MsgListVo> bulkGetSettlementPriceRateByProdClsNum(List<String> prod_cls_numlist, String unit_code, List<String> cpd_unit_code, String currency) ;
	
	/**
	 * 批量获取结算价格(商品编码)
	 */
	public List<MsgListVo> bulkGetSettlementPriceRateByProdId(List<String> prod_numlist, String unit_code, List<String> cpd_unit_code, String currency) ;
	
	/**
	 * 批量获取结算价格(商品编码) 临时表处理
	 */
	public List<MsgListVo> bulkGetSettlementPriceRateByProdIdList(List<String> prod_numlist, String unit_code, String cpd_unit_code, String currency) ;
}
