package mb.erp.dr.soa.dao;

import java.util.Map;

import mb.erp.dr.soa.vo.SfWarehProdVo;

/**
 * 新ERP仓库商品接口
 * 包含接口：
 * @author     余从玉
 * @version    1.0, 2014-12-17
 * @see         SfWarehProdVo
 * @since       全流通改造
 */
public interface SfWarehProdMapper {
	
    public SfWarehProdVo selectById(Long id);
    
    /**
     * 获取出库单成本组成本核算方法
     * @param gdnId
     * @return
     */
    public String getGdnCalType(Long gdnId);
    
    /**
     * 获取入库单成本组成本核算方法
     * @param grnId
     * @return
     */
    public String getGrnCalType(Long grnId);
    
    /**
	 * 更新在途库存
	 * @param params isAdd rvdId
	 * @return
	 */
	public Integer updateQtyInTransit(Map<String, Object> params);
	
	
	/**
	 * 插入在途库存
	 * @param params isAdd rvdId
	 * @return
	 */
	public Integer insertQtyInTransit(Map<String, Object> params);
	
	/**
	 * 更新在途库存
	 * @param params isAdd gdnId
	 * @return
	 */
	public Integer updateQtyInTransitByGdn(Map<String, Object> params);
	
	
	/**
	 * 插入在途库存
	 * @param params isAdd gdnId
	 * @return
	 */
	public Integer insertQtyInTransitByGdn(Map<String, Object> params);
	
	/**
	 * 插入实际库存 根据出库单
	 * @param params isAdd gdnId
	 * @return
	 */
	public Integer insertStkOnHandSfGdn(Map<String, Object> params);
	
	/**
	 * 更新实际库存 根据出库单
	 * @param params isAdd gdnId
	 * @return
	 */
	public Integer updateStkOnHandSfGdn(Map<String, Object> params);
	
	/**
	 * 更新商品库存-根据出库单
	 * @param params isAdd gdnId
	 * @return
	 */
	public Integer updateSfWarehProdSfGdn(Map<String, Object> params);
	
	/**
	 * 插入实际库存 根据入库单
	 * @param params isAdd grnId
	 * @return
	 */
	public Integer insertStkOnHandSfGrn(Map<String, Object> params);
	
	/**
	 * 更新实际库存 根据入库单
	 * @param params isAdd grnId
	 * @return
	 */
	public Integer updateStkOnHandSfGrn(Map<String, Object> params);
	
	
	/**
	 * 更新商品库存-根据入库单
	 * @param params isAdd grnId
	 * @return
	 */
	public Integer updateSfWarehProdSfGrn(Map<String, Object> params);
	
	/**
	 * 添加库位数量 -根据出库单
	 * @param params isAdd gdnId sfWarehouseLocId
	 * @return
	 */
	public Integer insertLocByGdn(Map<String, Object> params);
	
	/**
	 * 更新库位数量 -根据出库单
	 * @param params isAdd gdnId sfWarehouseLocId
	 * @return
	 */
	public Integer updateLocByGdn(Map<String, Object> params);
	
	/**
	 * 添加库位数量 -根据入库单
	 * @param params isAdd grnId sfWarehouseLocId
	 * @return
	 */
	public Integer insertLocByGrn(Map<String, Object> params);
	
	/**
	 * 更新库位数量 -根据入库单
	 * @param params isAdd grnId sfWarehouseLocId
	 * @return
	 */
	public Integer updateLocByGrn(Map<String, Object> params);
	
	/**
	 * 添加已分配库存 -根据交货单
	 * @param params isAdd dgnId
	 * @return
	 */
	public Integer insertQtyCommittedByDgn(Map<String, Object> params);
	
	/**
	 * 更新已分配库存 -根据交货单
	 * @param params isAdd dgnId
	 * @return
	 */
	public Integer updateQtyCommittedByDgn(Map<String, Object> params);
	
	/**
	 * 添加在购库存 -根据交货单
	 * @param params isAdd dgnId
	 * @return
	 */
	public Integer insertQtyOnOrderByDgn(Map<String, Object> params);
	
	/**
	 * 更新在购库存 -根据交货单
	 * @param params isAdd dgnId
	 * @return
	 */
	public Integer updateQtyOnOrderByDgn(Map<String, Object> params);	
	
}