package mb.erp.dr.soa.old.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.bean.OrderSearchBean;
import mb.erp.dr.soa.old.vo.GdnDtlVo;
import mb.erp.dr.soa.old.vo.GdnVo;

/**
 * 出库单数据库接口
 * 包含接口：查询出库明细单、保存 保存出库单-表头、保存 出库单明细、更新出库单处理进度、撤销	、查询出库单状态
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         GdnMapper
 * @since       全流通改造
 */
public interface GdnMapper {

	/**
	 * 查询出库明细单
	 * @param orderSearchBean
	 * @return
	 */
	public List<GdnDtlVo> searchGdnDtl(OrderSearchBean orderSearchBean);
	/**
	 * 保存 保存出库单-表头
	 * @param gdn
	 * @return
	 */
	public int save(GdnVo gdn);
	
	/**
	 * 保存 出库单明细
	 * @param gdn
	 * @return
	 */
	public int saveDtl(GdnDtlVo gdnDtl);
	
	/**
	 * 更新出库单处理进度
	 * @param gdn
	 */
	public void updateProcess(GdnVo gdn);
	
	/**
	 * 撤销	
	 * 可撤销出库单，进度在录入中、已确认、已审核后才可以撤销
	 * @param gdn
	 */
	public void cancel(GdnVo gdn);
	
	/**
	 * 查询出库单状态
	 * @param gdn
	 * @return
	 */
	public String selectProcess(GdnVo gdn);
	
	/**
	 * 更新出库单成本
	 * @param map
	 */
	public void updateCost(Map<String, Object> map);
	
	/**
	 * 更新出库单明细的单位成本
	 * @param map
	 */
	public void updateUnitCost(Map<String, Object> map);
	
	public GdnVo selectGdn(GdnVo gdnVo);
	
	public List<GdnDtlVo> selectGdnDtl(GdnVo gdnVo);
	
	/**
	 * 更新出库单的总金额
	 * @param gdn
	 */
	public void updateTtlVal(GdnVo gdn);
	
	/**
	 * 更新出库单的单价和折率
	 * @param gdnDtl
	 */
	public void updatePriceAndDiscRate(GdnDtlVo gdnDtl);
	
	/**
	 * 更新出库单原因
	 * @param gdnVo
	 */
	public void updateRcvState(GdnVo gdnVo);
	
	/**
	 * 
	 * 更新出库单sapFlag
	 * @param gdnVo
	 */
	public void updateSapFlagOne(GdnVo gdnVo);
	
	/**
	 * 
	 * 更新出库单sapFlag
	 * @param gdnVo
	 */
	public void updateSapFlagTwo(GdnVo gdnVo);
	
	/**
	 * 
	 * 更新出库单sapFlag
	 * @param gdnVo
	 */
	public void updateSapFlagForH(GdnVo gdnVo);
	
	/**
	 * 
	 * 更新newFlag
	 * @param gdnVo
	 */
	public void updateNewFlag(GdnVo gdnVo);
	
	
	/**
	 * 分销传输新ERP
	 * @param gdnVo
	 */
	public void transNewFlag(GdnVo gdnVo);
	
	/**
	 * 查询未指定货位的明细信息
	 * @param gdnVo
	 * @return List<GdnDtlVo>
	 */
	public List<GdnDtlVo> selectGdnDtlWithOutLocId(GdnVo gdnVo);
	
	/**
	 * 根据出库单查询是否有活动的成本组
	 * @param map
	 * @return Integer
	 */
	public Integer checkCostGrpByGdn(Map<String, Object> map);
	
	/**
	 * 将新ERP的出库单号写入到老ERP出库单
	 * @param gdnVo
	 * @return
	 */
	public Integer updateNewErpGdnCode(GdnVo gdnVo);
}
