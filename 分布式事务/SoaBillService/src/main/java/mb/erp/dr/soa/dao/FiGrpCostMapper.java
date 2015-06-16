package mb.erp.dr.soa.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.vo.FiGrpCostVo;

/**
 * 新ERP成本价格数据接口
 * 包含接口：
 * @author     郭明帅
 * @version    1.0, 2014-11-17
 * @see         SfIdtAllocDtlMapper
 * @since       全流通改造
 */
public interface FiGrpCostMapper {
	public List<FiGrpCostVo> SelectByUnitIDProdId(Map map);
	
	public List<FiGrpCostVo> selectByUnitIDProdIdWarethID(Map map);
	
	public List<FiGrpCostVo> buklGetByUnitIDProdId(Map map);
	
	public List<FiGrpCostVo> buklGetByUnitIDProdIdWarehID(Map map);
}