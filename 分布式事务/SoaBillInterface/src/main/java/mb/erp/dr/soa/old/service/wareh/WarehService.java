package mb.erp.dr.soa.old.service.wareh;

import mb.erp.dr.soa.bean.WarehBean;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.old.vo.TbnVo;
import mb.erp.dr.soa.vo.common.MsgVo;

/**
 * 库存服务 - 实现接口
 * 其中公开方法即对外开放的服务，包括查询虚拟库存，增加减少实物库存、货位库存、可分配库存等的库存相关操作
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         WarehService
 * @since       全流通改造
 */
public interface WarehService {

	/**
	 * 增加库存	给仓库增加商品库存
	 * @param warehBean 必填项：warehId,prodList
	 * @return msgVo
	 */
	public MsgVo increaseQty(WarehBean warehBean);
	/**
	 * 扣减库存	给仓库扣减商品库存
	 * @param warehBean 必填项：warehId,prodList
	 * @return msgVo
	 */
	public MsgVo reduceQty(WarehBean warehBean);
//	/**
//	 * 增加货位库存	给仓库增加商品货位库存
//	 * @param warehBean 必填项：warehId,locId,prodList
//	 * @return msgVo
//	 */
//	public MsgVo increaseLocQty(WarehBean warehBean);
//	/**
//	 * 扣减货位库存	给仓库扣减商品货位库存
//	 * @param warehBean 必填项：warehId,locId,prodList
//	 * @return msgVo
//	 */
//	public MsgVo reduceLocQty(WarehBean warehBean);
	/**
	 * 根据出库单增加货位库存
	 * @param warehBean 必填项：unitId,warehId,gdnNum
	 * @return msgVo
	 * @ 
	 */
	public MsgVo increaseLocQtyByGDN(GdnVo gdnVo);
	/**
	 * 根据出库单扣减货位库存
	 * @param warehBean 必填项：unitId,warehId,gdnNum
	 * @return msgVo
	 */
	public MsgVo reduceLocQtyByGDN(GdnVo gdnVo);
	/**
	 * 根据入库单增加货位库存
	 * @param warehBean 必填项：unitId,warehId,grnNum
	 * @return msgVo
	 */
	public MsgVo increaseLocQtyByGRN(GrnVo grnVo);
	/**
	 * 根据入库单扣减货位库存
	 * @param warehBean 必填项：unitId,warehId,grnNum
	 * @return msgVo
	 */
	public MsgVo reduceLocQtyByGRN(GrnVo grnVo);
	/**
	 * 增加已分配库存	给仓库增加商品已分配库存
	 * @param warehBean 必填项：warehId,prodList
	 * @return msgVo
	 */
	public MsgVo increaseCommitQty(WarehBean warehBean);
	/**
	 * 扣减已分配库存	给仓库扣减商品已分配库存
	 * @param warehBean 必填项：warehId,prodList
	 * @return msgVo
	 */
	public MsgVo reduceCommitQty(WarehBean warehBean);
	/**
	 * 根据计划配货单增加已分配库存	按计划配货单给仓库增加商品已分配库存
	 * 
	 * @param adnVo 必填项：VenderId,warehId,adnNum
	 * @return msgVo
	 * @ 
	 */
	public MsgVo increaseCommitQtyByADN(AdnVo adnVo) ;
	/**
	 * 根据计划配货单扣减已分配库存	按计划配货单给仓库扣减商品已分配库存
	 * @param adnVo 必填项：VenderId,warehId,adnNum
	 * @return msgVo
	 * @ 
	 */
	public MsgVo reduceCommitQtyByADN(AdnVo adnVo) ;
	/**
	 * 根据入库单给仓库增加商品已分配库存
	 * @param tbnVo 必填项：VenderId,warehId,tbnNum
	 * @return msgVo
	 * @ 
	 */
	public MsgVo increaseCommitQtyByTBN(TbnVo tbnVo) ;
	/**
	 * 根据入库单给仓库扣减商品已分配库存
	 * @param tbnVo 必填项：VenderId,warehId,tbnNum
	 * @return msgVo
	 * @ 
	 */
	public MsgVo reduceCommitQtyByTBN(TbnVo tbnVo) ;
	/**
	 * 根据出库单给仓库增加商品已分配库存
	 * @param warehBean 必填项：unitId,warehId,gdnNum
	 * @return msgVo
	 * @ 
	 */
	public MsgVo increaseCommitQtyByGDN(GdnVo gdnVo) ;
	/**
	 * 根据出库单给仓库扣减商品已分配库存
	 * @param warehBean 必填项：unitId,warehId,gdnNum
	 * @return msgVo
	 * @ 
	 */
	public MsgVo reduceCommitQtyByGDN(GdnVo gdnVo) ;

	/**
	 * 根据入库单增加库存
	 * @param warehBean 必填项：unitId,warehId,grnNum
	 * @return msgVo
	 * @ 
	 */
	public MsgVo increaseQtyByGRN(GrnVo grnVo) ;
	/**
	 * 根据入库单扣减库存
	 * @param warehBean 必填项：unitId,warehId,grnNum
	 * @return msgVo
	 * @ 
	 */
	public MsgVo reduceQtyByGRN(GrnVo grnVo) ;
	/**
	 * 根据出库单增加库存
	 * @param warehBean 必填项：unitId,warehId,gdnNum
	 * @return msgVo
	 * @ 
	 */
	public MsgVo increaseQtyByGDN(GdnVo gdnVo) ;
	/**
	 * 根据出库单扣减库存
	 * @param warehBean 必填项：unitId,warehId,gdnNum
	 * @return msgVo
	 */
	public MsgVo reduceQtyByGDN(GdnVo gdnVo) ;
	
	/**
	 * 根据出库单给仓库增加在途库存(调拨)
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo increaseTransitQtyByGDN(GdnVo gdnVo);
	
	/**
	 * 根据入库单给仓库扣减在途库存(调拨)
	 * @param warehBean
	 * @return msgVo
	 */
	public MsgVo reduceTransitQtyByGRN(GrnVo grnVo);
	
	/**
	 * 根据计划配货单增加在购库存
	 * @param adnVo 必填项：VendeeId,tranRcvWarehId,adnNum
	 * @return msgVo
	 */
	public MsgVo addQtyOnOrderByAdn(AdnVo adnVo);
	
	/**
	 * 根据计划配货单减少在购库存
	 * @param adnVo 必填项：VendeeId,tranRcvWarehId,adnNum
	 * @return msgVo
	 */
	public MsgVo decQtyOnOrderByAdn(AdnVo adnVo);
	
	/**
	 * 查询虚拟仓库
	 * @param param : venderId brandId
	 * @return
	 */
	public String searchVirtualWarehouse(String venderId, String brandId);

}
