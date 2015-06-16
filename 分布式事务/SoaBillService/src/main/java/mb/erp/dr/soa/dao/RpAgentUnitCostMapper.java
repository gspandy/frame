package mb.erp.dr.soa.dao;


public interface RpAgentUnitCostMapper {
	/**
	 * 更新单位成本记录
	 * @param sfGrnId
	 * @return
	 */
   public Integer updateUnitCost(Long sfGrnId);
   
   /**
	 * 新增单位成本记录
	 * @param sfGrnId
	 * @return
	 */
  public Integer insertUnitCost(Long sfGrnId);
}