package mb.erp.dr.soa.dao;

import java.util.List;

import mb.erp.dr.soa.vo.FiFsclMonthVo;


public interface FiFsclMonthMapper {
	public List<FiFsclMonthVo> getByCostGrp(Long costGrpId);
}