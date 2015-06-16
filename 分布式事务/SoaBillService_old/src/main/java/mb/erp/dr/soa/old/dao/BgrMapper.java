package mb.erp.dr.soa.old.dao;

import java.util.Map;

import mb.erp.dr.soa.old.vo.BgrDtlVo;
import mb.erp.dr.soa.old.vo.BgrVo;




/**
 * 退货申请单数据库接口
 * 包含接口：查询退货申请单细单、保存退货申请单 保存退货申请单明细、更新退货申请单处理进度、撤销、查询退货申请单状态
 * @author     陈志杰
 * @version    1.0, 2015-03-19
 * @see         BgrMapper
 * @since       全流通改造
 */
public interface BgrMapper {
	
	/**
	 * 保存退货申请单
	 * @param bgrVo
	 */
	public void save (BgrVo bgrVo);
	
	/**
	 * 保存退货申请单明细
	 * @param bgrDtlVo
	 */
	public void saveDtl(BgrDtlVo bgrDtlVo);
	
	/**
	 * 批量保存退货申请单明细
	 * @param map
	 */
	public void saveDtlForBatch(Map<Object, Object> map);

	/**
	 * 获取退货申请单单据状态
	 * @param bgrVo
	 * @return
	 */
	public String selectProgress(BgrVo bgrVo);
	
	/**
	 * 更新订货申请单
	 * @param bgrVo
	 */
	public void update(BgrVo bgrVo);
	
	/**
	 * 查询退货申请单
	 * @param bgrVo
	 * @return bgrVo
	 */
	public BgrVo select(BgrVo bgrVo);
	
	/**
	 * 更新退货申请单明细信息
	 * @param bgrDtlVo
	 */
	public void updateDtl(BgrDtlVo bgrDtlVo);

	/**
	 * 根据出库单更新退货申请单的出库数量
	 * @param bgrVo
	 */
	public void updateBgrByGdn(BgrVo bgrVo);
	
	/**
	 * 根据出库单明细更新退货申请单明细的出库数量
	 * @param bgrVo
	 */
	public void updateBgrDtlByGdn(BgrVo bgrVo);

	/**
	 * 根据入库单更新退货申请单的收货数量
	 * @param bgrVo
	 */
	public void updateBgrByGrn(BgrVo bgrVo);

	/**
	 * 根据入库单更新退货申请单明细的收货数量
	 * @param bgrVo
	 */
	public void updateBgrDtlByGrn(BgrVo bgrVo);
	
}
