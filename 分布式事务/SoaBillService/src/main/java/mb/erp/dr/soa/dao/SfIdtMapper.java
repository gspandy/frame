package mb.erp.dr.soa.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.vo.SfIdtDtlVo;
import mb.erp.dr.soa.vo.SfIdtVo;


/**
 * 新ERP现货订单数据接口
 * 包含接口：查询现货订单细单、保存现货订单 保存现货订单明细、更新现货订单处理进度、撤销、查询现货订单状态
 * @author     郭明帅
 * @version    1.0, 2014-11-17
 * @see         SfIdtMapper
 * @since       全流通改造
 */
public interface SfIdtMapper {
	/**
	 * 生成新ERP现货订单
	 * @param sfIdtVo
	 */
	public Integer saveSfIdt(SfIdtVo sfIdtVo);
	
	/**
	 * 生成新ERP现货订单明细
	 * @param sfIdtDtlVo
	 */
	public void saveSfIdtDtl(SfIdtDtlVo sfIdtDtlVo);
	
	/**
	 * 查询新ERP现货订单进度
	 * @param sfIdtVo
	 */
	public Integer selectDocState(SfIdtVo sfIdtVo);
	
	/**
	 * 更新新ERP现货订单
	 * @param sfIdtVo
	 */
	public Integer updateSfIdt(SfIdtVo sfIdtVo);
	
	/**
	 * 查询新ERP现货订单
	 * @param sfIdtVo
	 * @return
	 */
	public SfIdtVo selectSfIdt(SfIdtVo sfIdtVo);
	
	/**
	 * 根据id查询新ERP现货订单
	 * @param sfIdtId
	 * @return
	 */
	public SfIdtVo selectSfIdtById(Long sfIdtId);
	
	/**
	 * 根据code查询新ERP现货订单
	 * @param code
	 * @return
	 */
	public SfIdtVo selectSfIdtByCode(String code);
	
	/**
	 * 查询新ERP现货订单明细
	 * @param sfIdtDtlVo
	 * @return
	 */
	public List<SfIdtDtlVo> selectSfIdtDtl(SfIdtDtlVo sfIdtDtlVo);
	
	/**
	 * 更新现货订单明细信息的配货数量
	 * @param map
	 * @return
	 */
	public int updateSfIdtDtlAllocQty(Map map);
	
	/**
	 * 更新现货订单状态
	 * @param map
	 * @return
	 */
	public int updateIdtDocState(Map map);
	
	/**
	 * 更新现货订单明细的配货数量
	 * @param map
	 * @return
	 */
	public int updateIdtDtlQty(Map map);
	
	/**
	 * 更新现货订单明细的数量
	 * @param map
	 */
	public void updateSfIdtDtlQty(Map map);
	
	/**
	 * 更新现货订单总单的数量和金额
	 * @param map
	 */
	public void updateSfIdtQtyVal(Map map);
	
	/**
	 * 更新现货订单明细单价和折率
	 * @param vo
	 */
	public void updateSfIdtDtlPriceAndDiscrate(SfIdtDtlVo vo);
	
	/**
	 * 根据现货单查询税率
	 * @param code
	 * @return
	 */
	public Double selectTaxRateFromSfIdt(String code);
}