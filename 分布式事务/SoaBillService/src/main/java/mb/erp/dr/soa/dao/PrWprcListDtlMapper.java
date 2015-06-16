package mb.erp.dr.soa.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.vo.PrWprcListDtlVo;

/**
 * 新ERP商品结算价格表数据接口
 * 包含接口：
 * @author     郭明帅
 * @version    1.0, 2014-11-17
 * @see         PrWprcListDtlMapper
 * @since       全流通改造
 */
public interface PrWprcListDtlMapper {
	public List<PrWprcListDtlVo> selectNewSettlementPriceRateByProdClsNum(Map map);
	
	/**
	 * 查询结算价格详细
	 * @param map
	 * @return
	 */
	public List<PrWprcListDtlVo> selectByPOUC(Map map);
    
	public List<PrWprcListDtlVo> bulkGetSettlePrice(Map map);
}