package mb.erp.dr.soa.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.vo.PoPrcListDtlVo;


public interface PoPrcListDtlMapper {
   public List<PoPrcListDtlVo> priceUdfacePoPrcLstDtlGetByID(Map map);
}