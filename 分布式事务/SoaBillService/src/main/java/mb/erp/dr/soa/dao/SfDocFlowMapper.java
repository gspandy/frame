package mb.erp.dr.soa.dao;

import mb.erp.dr.soa.vo.SfDocFlowVo;


public interface SfDocFlowMapper {
	/**
	 * 保存单据流
	 * @param sfDocFlowVo
	 * @return 
	 */
   public Integer saveSfDocFlow(SfDocFlowVo sfDocFlowVo);
}