package mb.erp.dr.soa.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.vo.SfIdtAllocDtlVo;

/**
 * 新ERP现货订单分配明细数据接口
 * 包含接口：
 * @author     郭明帅
 * @version    1.0, 2014-11-17
 * @see         SfIdtAllocDtlMapper
 * @since       全流通改造
 */
public interface SfIdtAllocDtlMapper {
	public List<SfIdtAllocDtlVo> selectIdtAllocDtl(Map map);
	
	public int saveIdtAllocDtl(Map map);
	
	/**
	 * 更新订单分配明细（记录出库单号、出库数量和修改进度为收货中）
	 * @param map
	 * @return
	 */
	public int updateAllocDtlRP(Map map);
	
	/**
	 * 根据交货单更新订单分配明细
	 * @param map
	 * @return
	 */
	public int updateAllocDtlByDgn(Map map);
	
	/**
	 * 根据交货单添加分配明细
	 * @param map
	 * @return
	 */
	public int saveIdtAllocDtlByDgn(Map map);
	
	/**
	 * 根据交货单更新订单分配明细为已配货
	 * @param map
	 */
	public void updateAllocDtlByDgnToAD(Map map);
	
	/**
	 * 根据计划配货单更新现货单分配明细为已配货
	 * @param map
	 */
	public int updateAllocDtlByAdnToAD(Map map);
	
	/**
	 * 更新现货单分配明细为发货中
	 * @param map
	 */
	public int updateAllocDtlToDG(Map map);
	
	/**
	 * 更新现货单分配明细为已发货
	 * @param map
	 */
	public void updateAllocDtlToDD(Map map);
	
	/**
	 * 根据老ERP出库单更新现货单分配明细为已发货
	 * @param map
	 */
	public int updateAllocDtlByGdnToDD(Map map);
	
	/**
	 * 更新现货单分配明细为收货中
	 * @param map
	 */
	public void updateAllocDtlToRG(Map map);
	
	/**
	 * 更新现货单分配明细为收货中
	 * @param map
	 */
	public void updateAllocDtlToRD(Map map);
	
}