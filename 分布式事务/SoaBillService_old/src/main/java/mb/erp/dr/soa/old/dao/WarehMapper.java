package mb.erp.dr.soa.old.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.bean.OrderSearchBean;
import mb.erp.dr.soa.bean.ProdBean;
import mb.erp.dr.soa.bean.WarehBean;
import mb.erp.dr.soa.old.vo.LocTranVo;
import mb.erp.dr.soa.old.vo.StkDtlVo;
import mb.erp.dr.soa.old.vo.WarehCommitedTranVo;
import mb.erp.dr.soa.old.vo.WarehOrderTranVo;
import mb.erp.dr.soa.old.vo.WarehProdVo;
import mb.erp.dr.soa.old.vo.WarehTranVo;
import mb.erp.dr.soa.old.vo.Warehouse;

/**
 * 库存服务数据库接口
 * 包含接口：查询商品的库存信息、查询货位库存信息、查询虚拟库存、批量更新实物库存等
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         WarehMapper
 * @since       全流通改造
 */
public interface WarehMapper {

	/**
	 * 查询商品的库存信息
	 * @param prodBean
	 * @return
	 */
	public WarehProdVo searchWarehInfo(ProdBean prodBean);
	
	/**
	 * 查询货位库存信息 
	 * @param prodBean
	 * @return
	 */
	public StkDtlVo searchStkDtl(ProdBean prodBean);
	
	/**
	 * 查询货位信息
	 * @param wareId
	 * @return
	 */
	public Warehouse searchWarehouseInfo(String wareId);
	
	/**
	 * 查询虚拟仓库
	 * @param param : venderId brandId
	 * @return
	 */
	public String searchVirtualWarehouse(Map<String, String> param);
	
	/**
	 * 查询已分配库存事务信息
	 * @param orderSearchBean 必填：warehId docType docNum
	 * @return
	 */
	public List<WarehCommitedTranVo> searchCommitTranInfo(OrderSearchBean orderSearchBean);
	
	/**
	 * 查询库存事务信息
	 * @param orderSearchBean 必填：warehId docType docNum
	 * @return
	 */
	public WarehTranVo searchWarehTranInfo(OrderSearchBean orderSearchBean);
	
	/**
	 * 将更新的数据保存进临时表
	 * @param list
	 * @return
	 */
	public Integer saveToTempTable(List<ProdBean> list);
	
	/**
	 * 批量更新实物库存
	 * @return
	 */
	public Integer updateWarehProdQty();
	
	/**
	 * 批量插入实物库存
	 * @return
	 */
	public Integer insertWarehProdQty();
	
	/**
	 * 批量更新货位库存
	 * @return
	 */
	public Integer updateLocProdQty();
	
	/**
	 * 批量插入货位库存
	 * @return
	 */
	public Integer insertLocProdQty();
	
	/**
	 * 批量更新已分配库存
	 * @return
	 */
	public Integer updateCommitTranProdQty();
	/**
	 * 批量插入已分配库存
	 * @return
	 */
	public Integer insertCommitTranProdQty();
	
	/**
	 * 根据出库单更新货位库存
	 * @param warehBean
	 * @return
	 */
//	public Integer updateStkDtlByGdn(WarehBean warehBean);
	public Integer updateStkDtlByGdn(Map<String,Object> map);
	/**
	 * 根据出库单插入货位库存
	 * @param warehBean
	 * @return
	 */
//	public Integer insertStkDtlByGdn(WarehBean warehBean);
	public Integer insertStkDtlByGdn(Map<String,Object> map);
	
	/**
	 * 根据入库单更新货位库存
	 * @param warehBean
	 * @return
	 */
//	public Integer updateStkDtlByGrn(WarehBean warehBean);
	public Integer updateStkDtlByGrn(Map<String,Object> map);
	/**
	 * 根据入库单插入货位库存
	 * @param warehBean
	 * @return
	 */
//	public Integer insertStkDtlByGrn(WarehBean warehBean);
	public Integer insertStkDtlByGrn(Map<String,Object> map);
	
	/**
	 * 根据出库单更新实物库存
	 * @param warehBean
	 * @return
	 */
	public Integer updateWarehProdByGdn(WarehBean warehBean);
	/**
	 * 根据出库单插入实物库存
	 * @param warehBean
	 * @return
	 */
	public Integer insertWarehProdByGdn(WarehBean warehBean);
	
	/**
	 * 根据入库单更新实物库存
	 * @param warehBean
	 * @return
	 */
	public Integer updateWarehProdByGrn(WarehBean warehBean);
	/**
	 * 根据入库单插入实物库存
	 * @param warehBean
	 * @return
	 */
	public Integer insertWarehProdByGrn(WarehBean warehBean);
	
	/**
	 * 根据计划配货单更新已分配库存
	 * @param warehBean
	 * @return
	 */
	public Integer updateWarehCommitProdByAdn(WarehBean warehBean);
	
	/**
	 * 根据计划配货单插入已分配库存
	 * @param warehBean
	 * @return
	 */
	public Integer insertWarehCommitProdByAdn(WarehBean warehBean);
	
	/**
	 * 根据计划配货单更新已分配库存
	 * @param warehBean
	 * @return
	 */
	public Integer updateWarehCommitProdByTbn(WarehBean warehBean);
	/**
	 * 根据计划配货单插入已分配库存
	 * @param warehBean
	 * @return
	 */
	public Integer insertWarehCommitProdByTbn(WarehBean warehBean);
	
	/**
	 * 根据出库单更新已分配库存
	 * @param warehBean
	 * @return
	 */
	public Integer updateWarehCommitProdByGdn(WarehBean warehBean);
	/**
	 * 根据出库单插入已分配库存
	 * @param warehBean
	 * @return
	 */
	public Integer insertWarehCommitProdByGdn(WarehBean warehBean);
	
	/**
	 * 根据出库单更新在途库存
	 * @param warehBean
	 * @return
	 */
	public Integer updateWarehTransitByGdn(WarehBean warehBean);
	
	/**
	 * 根据出库单插入在途库存
	 * @param warehBean
	 * @return
	 */
	public Integer insertWarehTransitByGdn(WarehBean warehBean);
	
	/**
	 * 根据入库单更新在途库存
	 * @param warehBean
	 * @return
	 */
	public Integer updateWarehTransitByGrn(WarehBean warehBean);
	
	/**
	 * 根据入库单插入在途库存
	 * @param warehBean
	 * @return
	 */
	public Integer insertWarehTransitByGrn(WarehBean warehBean);
	
	/**
	 * 保存仓库事务
	 * @param warehTranVo
	 * @return
	 */
	public Integer saveWarehTran(List<WarehTranVo> warehTranVos);
	
	/**
	 * 保存货位事务
	 * @param locTranVos
	 * @return
	 */
	public Integer saveLocTran(List<LocTranVo> locTranVos);
	
	/**
	 * 保存已分配库存事务
	 * @param warehCommitedTranVo
	 * @return
	 */
	public Integer saveWarehCommitTran(List<WarehCommitedTranVo> warehCommitTranVos);

	/**
	 * 保存在购库存事务
	 * @param warehCommitedTranVo
	 * @return
	 */
	public Integer saveWarehOrderTran(List<WarehOrderTranVo> warehOrderTranVos);	
	
	/**
	 * 根据计划配货单添加在购库存
	 * @param warehBean
	 * @return
	 */
	public Integer insertWarehOrderProdByAdn(WarehBean warehBean);
	
	/**
	 * 根据计划配货单更新在购库存
	 * @param warehBean
	 * @return
	 */
	public Integer updateWarehOrderProdByAdn(WarehBean warehBean);

	/**
	 * 查询在购库存事务信息
	 * @param orderSearchBean 必填：warehId docType docNum
	 * @return
	 */
	public List<WarehOrderTranVo> searchOrderTranInfo(OrderSearchBean orderSearchBean);

	/**
	 * 根据出库单添加货位库存事务
	 * @param map
	 * @return Integer
	 */
	public Integer insertLocTranByGdn(Map<String, Object> map);
	
	/**
	 * 根据入库单添加货位库存事务
	 * @param map
	 * @return Integer
	 */
	public Integer insertLocTranByGrn(Map<String, Object> map);
	
	/**
	 * 判断货位是否存在
	 * @param bean
	 * @return Long 1：存在
	 */
	public Long selectLocByWarehIdAndLocId(WarehBean bean);
}