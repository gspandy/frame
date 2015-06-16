package mb.erp.dr.soa.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.vo.SfDgnDtlVo;
import mb.erp.dr.soa.vo.SfDgnVo;

/**
 * 新ERP交货单数据接口
 * 包含接口：
 * @author     郭明帅
 * @version    1.0, 2014-11-17
 * @see         SfDgnMapper
 * @since       全流通改造
 */
public interface SfDgnMapper {
	/**
	 * 生成新ERP交货单
	 * @param sfIdtVo
	 */
	public void saveSfDgn(SfDgnVo sfDgnVo);
	
	/**
	 * 生成新ERP交货单明细
	 * @param sfIdtVo
	 */
	public void saveSfDgnDtl(SfDgnDtlVo sfDgnDtlVo);
	
	/**
	 * 查询新ERP交货单进度
	 * @param sfIdtVo
	 */
	public Integer selectDocState(SfDgnVo sfDgnVo);
	
	/**
	 * 更新新ERP交货单
	 * @param sfIdtVo
	 */
	public Integer updateSfDgn(SfDgnVo sfDgnVo);
	
	/**
	 * 根据调配单生成交货单
	 * @param tbnId
	 * @return
	 */
	public SfDgnVo genDgnByDrTbn(Long tbnId);
	
	/**
	 * 根据交货单查询交货单详情
	 * @param sfDgnVo
	 * @return
	 */
	public List<SfDgnDtlVo> genSfDgnDtlByDgn(SfDgnVo sfDgnVo);
	
	/**
	 * 根据调配单生成交货单明细
	 * @param tbnId dgnId
	 * @return
	 */
	public List<SfDgnDtlVo> genDgnDtlByDrTbn(Map<String, Long> params);
	
	/**
	 * 根据交货单code查询
	 * @param code
	 * @return
	 */
	public SfDgnVo selectDgnByCode(String code);
	
	/**
	 * 根据交货单id查询
	 * @param code
	 * @return
	 */
	public SfDgnVo selectDgnById(Long id);
	
	/**
	 * 根据现货单生成交货单
	 * @return
	 */
	public SfDgnVo genDgnBySfIdt(Long sfIdtId);
	
	/**
	 * 根据现货单详情生成交货单详情
	 * @parm sfIdtId,sfDgnId
	 */
	public List<SfDgnDtlVo> genDgnDtlBySfIdtDtl(Map map);
	
	/**
	 * 根据交货单查询税率
	 * @param code
	 * @return
	 */
	public Double selectTaxRateFromSfDgn(String code);

}