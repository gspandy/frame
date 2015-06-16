package mb.erp.dr.soa.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.vo.PrPrcListDtlVo;


/**
 * 新ERP商品零售价格表数据接口
 * 包含接口：
 * @author     郭明帅
 * @version    1.0, 2014-11-17
 * @see         PrWprcListDtlMapper
 * @since       全流通改造
 */
public interface PrPrcListDtlMapper {
	public List<PrPrcListDtlVo> selectByPrddClsNum(Map map);
	
	public List<PrPrcListDtlVo> selectByProdIdUnitID(Map map);
	
	public List<PrPrcListDtlVo> selectByProdColorUnitID(Map map);
	
	public List<PrPrcListDtlVo> priceBulkGetNewRetailPriceRate(Map map);
	
	public List<PrPrcListDtlVo> priceBulkGetNewRetailPriceRateByProdNum(Map map);
	
	public List<String> selectProdNumByProdColorNum(Map map);
	
	public List<PrPrcListDtlVo> selectByProdColorUnitID2(Map map);
	
	public List<PrPrcListDtlVo> selectByProdColorUnitID3(Map map);
	
	public List<PrPrcListDtlVo> priceBulkGetNewRetailPriceRateByIntnlBc(Map map);
	
}