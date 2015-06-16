package mb.erp.dr.soa.old.dao;

import java.util.List;

import mb.erp.dr.soa.bean.OrderSearchBean;
import mb.erp.dr.soa.old.vo.AdnDtlVo;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.IdtVo;

/**
 * 计划配货单数据库接口
 * 包含接口：查询计划配货单细单、保存计划配货单 保存计划配货单明细、更新计划配货单处理进度、撤销、查询计划配货单状态
 * @author     郭明帅
 * @version    1.0, 2014-11-10
 * @see         AdnMapper
 * @since       全流通改造
 */
public interface AdnMapper {
	public void save(AdnVo adnVo);
	
	public void saveDtl(AdnDtlVo adnDtlVo);
	
	public AdnVo select (AdnVo adnVo);
	
	public List<AdnDtlVo> selectDtl(OrderSearchBean orderSearchBean);
	
	public List<AdnDtlVo> selectAdnDtl(AdnVo adnVo);
	
	public String selectProgress (AdnVo adnVo);
	
	public void update(AdnVo adnVo);
	
	public void updateDtl(AdnDtlVo adnDtlVo);
	
	/**
	 * 根据VENDEE_ID IDT_NUM 更新计划配货单明细中收货数量为0
	 * @param adnVo
	 */
	public void updateAdnDtlByIdt(AdnVo adnVo);
	
	/**
	 * 更新计划配货单进度为已收货RD,发货数量为入库单入库数量，发货金额为入库单入库金额
	 */
	public void updateAdnByGrn(IdtVo idtVo);
	
	/**
	 * 更新计划配货单明细上的发货数量为入库单入库数量
	 */
	public void updateAdnDtlByGrn(IdtVo idtVo);
	
	/**
	 * 更新计划配货单的进度为已发货DD 发货数量为出库单出库数量，发货金额为出库单出库金额
	 */
	public void updateAdnByGdn(IdtVo idtVo);
	
	/**
	 * 更新计划配货单明细上的发货数量为出库单出库数量
	 */
	public void updateAdnDtlByGdn(IdtVo idtVo);
	
	
	/**
	 * 更新上层计划配货单进度为已收货RD,发货数量为入库单入库数量，发货金额为入库单入库金额
	 */
	public void updateUpAdnByGrn(AdnVo adnVo);
	
	/**
	 * 更新上层计划配货单明细上的发货数量为入库单入库数量
	 */
	public void updateUpAdnDtlByGrn(AdnVo adnVo);
	
	/**
	 * 更新上层计划配货单的进度为已发货DD 发货数量为出库单出库数量，发货金额为出库单出库金额
	 */
	public void updateUpAdnByGdn(AdnVo adnVo);
	
	/**
	 * 更新上层计划配货单明细上的发货数量为出库单出库数量
	 */
	public void updateUpAdnDtlByGdn(AdnVo adnVo);
	
	
}