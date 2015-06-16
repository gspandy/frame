package mb.erp.dr.soa.service.wareh;

import mb.erp.dr.soa.vo.common.MsgVo;

/**
 * 新ERP库存服务
 * 
 * @author     余从玉
 * @version    1.0, 2014-12-18
 * @see         NewWarehService
 * @since       全流通改造
 */
public interface NewWarehService {

	/**
     * 出库增加在途库存
     * @param gdnId
     * @return
     */
    public MsgVo increaseQtyInTransitByGdn(Long gdnId);
    /**
     * 出库减少在途库存
     * @param gdnId
     * @return
     */
    public MsgVo reduceQtyInTransitByGdn(Long gdnId);
    
    
    /**
     * 入库增加在途库存
     * @param grnId
     * @return
     */
    public MsgVo increaseQtyInTransitByGrn(Long grnId);
    
    /**
     * 入库减少在途库存
     * @param grnId
     * @return
     */
    public MsgVo reduceQtyInTransitByGrn(Long grnId);
    
    /**
     * 根据出库单增加库存
     * @param gdnId
     * @return
     */
    public MsgVo increaseStockByGdn(Long gdnId);
    
    /**
     * 根据出库单减少库存
     * @param gdnId
     * @return
     */
    public MsgVo reduceStockByGdn(Long gdnId);
    
    /**
     * 根据入库单增加库存
     * @param grnId
     * @return
     */
    public MsgVo increaseStockByGrn(Long grnId);
    
    /**
     * 根据入库单减少库存
     * @param grnId
     * @return
     */
    public MsgVo reduceStockByGrn(Long grnId);
    
    /**
     * 根据交货单增加已分配库存
     * @param dgnId
     * @return
     */
    public MsgVo increaseQtyCommittedByDgn(Long dgnId);
    
    /**
     * 根据交货单减少已分配库存
     * @param dgnId
     * @return
     */
    public MsgVo reduceQtyCommittedByDgn(Long dgnId);
    
    /**
     * 根据交货单增加在购库存
     * @param dgnId
     * @return
     */
    public MsgVo increaseQtyOnOrderByDgn(Long dgnId);
    
    /**
     * 根据交货单减少在购库存
     * @param dgnId
     * @return
     */
    public MsgVo reduceQtyOnOrderByDgn(Long dgnId);
    
    /**
	 * 根据出库单增加货位库存
	 * @param Id
	 * @return msgVo
	 * @ 
	 */
	public MsgVo increaseLocQtyByGDN(Long Id);
	/**
	 * 根据出库单扣减货位库存
	 * @param Id
	 * @return msgVo
	 */
	public MsgVo reduceLocQtyByGDN(Long Id);
	/**
	 * 根据入库单增加货位库存
	 * @param Id
	 * @return msgVo
	 */
	public MsgVo increaseLocQtyByGRN(Long Id);
	/**
	 * 根据入库单扣减货位库存
	 * @param Id
	 * @return msgVo
	 */
	public MsgVo reduceLocQtyByGRN(Long Id);
}
