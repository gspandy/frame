package mb.erp.dr.soa.dao;

import java.util.Map;

import mb.erp.dr.soa.vo.SfRvdDtlVo;
import mb.erp.dr.soa.vo.SfRvdVo;


/**
 * 新ERP到货通知单数据接口
 * 包含接口：查询到货通知单细单、保存到货通知单 保存到货通知单明细、更新到货通知单处理进度、撤销
 * @author     郭明帅
 * @version    1.0, 2014-11-17
 * @see         SfRvdMapper
 * @since       全流通改造
 */
public interface SfRvdMapper {
	/**
	 * 生成新ERP到货通知单
	 * @param sfIdtVo
	 */
	public void saveSfRvd(SfRvdVo sfRvdVo);
	
	/**
	 * 生成新ERP到货通知单明细
	 * @param sfIdtVo
	 */
	public void saveSfRvdDtl(SfRvdDtlVo sfRvdDtlVo);
	
	/**
	 * 查询新ERP到货通知单进度
	 * @param sfIdtVo
	 */
	public Integer selectDocState(SfRvdVo sfRvdVo);
	
	/**
	 * 更新新ERP到货通知单
	 * @param sfIdtVo
	 */
	public Integer updateSfRvd(SfRvdVo sfRvdVo);
	
	/**
	 * 查询新ERP到货通知单
	 * @param sfIdtVo
	 */
	public SfRvdVo selectSfRvd(Map map);
	
	/**
	 * 根据到货通知单查询出库单code
	 * @param sfIdtVo
	 */
	public String getGdnCodeByRvd(Map map);
	
	/**
	 * 根据code查询到货通知单
	 * @param code
	 * @return
	 */
	public SfRvdVo selectSfRvdByCode(String code);
	
	/**
	 * 根据id查询到货通知单
	 * @param rvdId
	 * @return
	 */
	public SfRvdVo selectSfRvdById(Long rvdId);
	
	/**
	 * 根据新ERP到货通知单code获取税率
	 * @param code
	 * @return
	 */
	public Double selectTaxRateFromSfRvd(String code);
}