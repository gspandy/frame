package mb.erp.dr.soa.old.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.old.vo.WprcLstDtlVo;



public interface WprcLstDtlMapper {
	public List<WprcLstDtlVo> selectSettlementPriceRateByProdClsNum(Map map);
	
	public List<WprcLstDtlVo> selectSettlementPriceRateByProdId(Map map);
	
	public List<WprcLstDtlVo> bulkGetSettlePriceOld(Map map);
}