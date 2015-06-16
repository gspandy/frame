package mb.erp.dr.soa.old.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.old.vo.ScnDtlVo;
import mb.erp.dr.soa.old.vo.ScnVo;




/**
 * 退货单数据库接口
 * 包含接口：查询退货单细单、保存退货单 保存退货单明细、更新退货单处理进度、撤销、查询退货单状态
 * @author     陈志杰
 * @version    1.0, 2015-03-19
 * @see         ScnMapper
 * @since       全流通改造
 */
public interface ScnMapper {
	/**
	 * 保存退货单
	 * @param scnVo
	 */
	public void save(ScnVo scnVo);
	
	/**
	 * 保存退货单明细
	 * @param scnDtlVo
	 */
	public void saveDtl(ScnDtlVo scnDtlVo);
	
	/**
	 * 批量保存退货单明细
	 * @param map
	 */
	public void saveDtlForBatch(Map<Object, Object> map);
	
	/**
	 * 查询退货单进度
	 * @param scnVo
	 * @return 订单状态
	 */
	public String selectProgress(ScnVo scnVo);
	
	/**
	 * 更新退货单
	 * @param scnVo
	 */
	public void update(ScnVo scnVo);
	
	/**
	 * 查询退货单
	 * @param scnVo
	 * @return ScnVo
	 */
	public ScnVo select(ScnVo scnVo);

	/**
	 * 更新退货单明细
	 * @param dtlVo
	 */
	public void updateDtl(ScnDtlVo dtlVo);
	
	/**
	 * 根据入库单更新退货单
	 * @param scnVo
	 */
	public void updateScnByGrn(ScnVo scnVo);
	
	/**
	 * 根据入库单明细更新退货单明细
	 * @param scnVo
	 */
	public void updateScnDtlByGrn(ScnVo scnVo);
	
	/**
	 * 根据退货单号查询
	 * @param tdCode
	 * @return
	 */
	public List<ScnVo> getScnVoByTDCode(String tdCode);
}
