package mb.erp.dr.soa.service.price;

import java.util.List;

import mb.erp.dr.soa.vo.common.MsgListVo;
import mb.erp.dr.soa.vo.common.MsgVo;

/**
 * 零售价格服务 - 接口
 * 其中公开方法即对外开放的服务，包括根据商品编码获取零售价格折率和根据批量商品编码获取零售价格折率相关操作
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         NewRetailPriceRateService
 * @since       全流通改造
 */
public interface NewRetailPriceRateService {
	/**
	 * 	获取零售价格
	 */
	public MsgVo getRetailPriceRateByProdClsNum(String prod_cls_num, String unit_code, String currency) ;
	
	public MsgVo getRetailPriceRateByProdId(String prod_id, String unit_id, String currency) ;
	
	public MsgVo getRetailPriceRateByProdColorNum(String prod_color_num, String unit_code, String currency) ;

   /**
    * 批量获取零售价格(商品款)
    */
	public List<MsgListVo> bulkGetRetailPriceRateByProdClsNum(List<String> prod_cls_numlist, String unit_code, String currency) ;
	
	/**
	 * 批量获取零售价格(商品编码)
	 */
	public List<MsgListVo> bulkGetRetailPriceRateByProdId(List<String> prod_numlist, String unit_code, String currency) ;
	
	/**
	 * 批量获取零售价格(商品颜色)
	 */
	public List<MsgListVo> bulkGetRetailPriceRateByProdColorNum(List<String> prod_color_numlist, String unit_code, String currency) ;
	
	public List<MsgListVo> bulkGetRetailPriceRateByIntnlBc(List<String> inTnlBcList, String unit_code, String currency) ;
	
}
