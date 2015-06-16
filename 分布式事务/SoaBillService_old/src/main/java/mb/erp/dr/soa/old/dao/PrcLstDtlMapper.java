package mb.erp.dr.soa.old.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.old.vo.PrcLstDtlVo;


public interface PrcLstDtlMapper {
	
   public List<PrcLstDtlVo> selectByProdClsNum(Map map);
   
   public List<PrcLstDtlVo> selectByUnitIDProdID(Map map);
   
   public List<PrcLstDtlVo> selectByProdColorNum(Map map);
   
   public List<PrcLstDtlVo> bulkGetRetailPriceRateByProdClsNum(Map map); 
   
   public List<PrcLstDtlVo> bulkGetRetailPriceUnitIDProdIDOld(Map map); 
   
   public List<PrcLstDtlVo> selectByProdColorNum2(Map map);
   
   public List<PrcLstDtlVo> bulkGetRetailPriceRateByIntnlBc(Map map);
   
}