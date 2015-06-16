package mb.erp.dr.soa.old.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.old.vo.GrpCostVo;


public interface GrpCostMapper {
	
	public List<GrpCostVo> selectByUnitIDProdId(Map map);
	
	public List<GrpCostVo> selectByUnitIDProdIdWareID(Map map);
	
	public List<GrpCostVo> bulkGetByUnitIDProdId(Map map);
	
	public List<GrpCostVo> bulkGetByUnitIDProdIdWareID(Map map);
	
}