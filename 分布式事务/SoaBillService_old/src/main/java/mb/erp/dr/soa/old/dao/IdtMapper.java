package mb.erp.dr.soa.old.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.old.vo.IdtDtlVo;
import mb.erp.dr.soa.old.vo.IdtVo;



/**
 * 现货订单数据库接口
 * 包含接口：查询现货订单细单、保存现货订单 保存现货订单明细、更新现货订单处理进度、撤销、查询现货订单状态
 * @author     郭明帅
 * @version    1.0, 2014-11-10
 * @see         IdtMapper
 * @since       全流通改造
 */
public interface IdtMapper {
	
	public IdtVo selectByPrimaryKey(IdtVo vo);
	
	public void save(IdtVo idtVo);
	
	public void saveDtl(IdtDtlVo idtDtlVo);
	
	public IdtVo select (IdtVo idtVo);
	
	public List<IdtDtlVo> selectDtl(IdtVo idtVo);
	
	public String selectProgress (IdtVo idtVo);
	
	public void update(IdtVo idtVo);
	
	public void updateDtl(IdtDtlVo idtDtlVo);
	
	/**
	 * 更新现货订单的配货数量和配货金额为计划配货单的对应值  订单进度
	 * @param idtVo
	 */
	public void updateByAdn(IdtVo idtVo);
	
	/**
	 * 更新现货订单详情的配货数量为计划配货单详情的对应值
	 * @param idtVo
	 */
	public void updateDtlByAdn(IdtVo idtVo);
	
	/**
	 * 更新现货订单进度为已收货RD,发货数量为入库单入库数量，发货金额为入库单入库金额
	 * @param idtVo
	 */
	public void updateIdtByGrn(IdtVo idtVo);
	
	/**
	 * 更新现货订单明细上的发货数量为入库单入库数量
	 * @param idtVo
	 */
	public void updateIdtDtlByGrn(IdtVo idtVo);
	
	/**
	 * 更新现货订单的进度为已发货DD 发货数量为出库单出库数量，发货金额为出库单出库金额
	 * @param idtVo
	 */
	public void updateIdtByGdn(IdtVo idtVo);
	
	/**
	 * 更新现货订单明细上的发货数量为出库单出库数量
	 * @param idtVo
	 */
	public void updateIdtDtlByGdn(IdtVo idtVo);
	
	/**
	 * 根据出库单 GDN 查询 现货订单
	 */
	public IdtVo selectIdtByGdn(GdnVo gdnVo);
	
	/**
	 * 根据入库单 GRN 查询 现货订单
	 */
	public IdtVo selectIdtByGrn(GrnVo grnVo);
	
	/**
	 * 根据计划配货单关联到现货订单-〉从现货订单中获取税率
	 * @param map
	 * @return
	 */
	public Double selectTaxRateFromIdt(Map map);
	
	/**
	 * 将新ERP的现货订单单号写入到老ERP现货订单
	 * @param idtVo
	 * @return
	 */
	public Integer updateNewErpIdtCode(IdtVo idtVo);
}
