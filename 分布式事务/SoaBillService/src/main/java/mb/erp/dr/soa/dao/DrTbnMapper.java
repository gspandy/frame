package mb.erp.dr.soa.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.vo.DrTbnAllocDtlVo;
import mb.erp.dr.soa.vo.DrTbnDtlVo;
import mb.erp.dr.soa.vo.DrTbnVo;
import mb.erp.dr.soa.vo.SfGdnVo;

/**
 * 新erp调配单数据库接口
 * 包含接口：查询调配明细单、保存 保存调配单-表头、保存 调配单明细、更新调配单处理进度、撤销	、查询调配单状态
 * @author     余从玉
 * @version    1.0, 2014-11-21
 * @see         DrTbnMapper
 * @since       全流通改造
 */
public interface DrTbnMapper {

	/**
	 * 保存 保存调配单-表头
	 * @param tbn
	 * @return
	 */
	public int save(DrTbnVo tbn);
	
	/**
	 * 保存 调配单明细
	 * @param tbn
	 * @return
	 */
	public int saveDtl(DrTbnDtlVo tbnDtl);
	
	/**
	 * 保存 调配单配货明细
	 * @param tbn
	 * @return
	 */
	public int saveAllocDtl(DrTbnAllocDtlVo allocDtlVo);
	/**
	 * 查询调配单明细
	 * @param orderSearchBean
	 * @return
	 */
	public List<DrTbnDtlVo> searchTbnDtl(Integer drTbnId);
	/**
	 * 查询调配单配货明细
	 * @param orderSearchBean
	 * @return
	 */
	public List<DrTbnAllocDtlVo> searchDrTbnAllocDtl(Long drTbnId);
	
	public List<DrTbnAllocDtlVo> selectDrTbnByDGN(Map map);
	/**
	 * 更新调配单处理进度
	 * @param tbn
	 */
	public void updateProcess(DrTbnVo tbn);
	
	/**
	 * 查询调配单状态
	 * @param tbn
	 * @return
	 */
	public Integer selectProcess(DrTbnVo tbn);
	
	/**
	 * 原始单据修改-调配单表头（发货）
	 * @param tbn
	 * @return
	 */
	public Integer updateOriginalTbnF(DrTbnVo tbn);
	/**
	 * 原始单据修改-调配单明细 （发货）
	 * @param tbn
	 * @return
	 */
	public Integer updateOriginalTbnDtlF(DrTbnVo tbn);
	/**
	 * 原始单据修改-调配单表头 （收货）
	 * @param tbn
	 * @return
	 */
	public Integer updateOriginalTbnS(DrTbnVo tbn);
	/**
	 * 原始单据修改-调配单明细 （收货）
	 * @param tbn
	 * @return
	 */
	public Integer updateOriginalTbnDtlS(DrTbnVo tbn);
	/**
	 * 原始单据修改-调配单表头 （已发货接口）
	 * @param tbn
	 * @return
	 */
	public Integer updateOriginalTbnYfh(DrTbnVo tbn);
	/**
	 * 原始单据修改-调配单明细 （已发货接口）
	 * @param tbn
	 * @return
	 */
	public Integer updateOriginalTbnDtlYfh(DrTbnVo tbn);
	/**
	 * 原始单据修改-调配单表头 （已收货接口）
	 * @param tbn
	 * @return
	 */
	public Integer updateOriginalTbnYsh(DrTbnVo tbn);
	/**
	 * 原始单据修改-调配单明细 （已收货接口）
	 * @param tbn
	 * @return
	 */
	public Integer updateOriginalTbnDtlYsh(DrTbnVo tbn);
	
	/**
	 * 根据出库单 GDN 查询 调配单
	 */
	public DrTbnVo selectTbnByGdn(GdnVo gdnVo);
	
	/**
	 * 根据入库单 GDN 查询 调配单
	 */
	public DrTbnVo selectTbnByGrn(GrnVo grnVo);
	
	/**
	 * 从调配单中获取税率
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Double selectTaxRateFromTbn(Map map);
	
	/**
	 * 根据交货单更新原始调配单据
	 * @param params
	 * @return
	 */
	public Integer updateDrTbnByDGN(Map<String, Object> params);
	
	/**
	 * 根据交货单添加分配明细
	 * @param params
	 * @return
	 */
	public Integer insertDrTbnAllocByDgn(Map<String, Object> params);
	
	/**
	 * 配货明细 -  已配货
	 * @param params :  dgnId  drTbnId
	 * @return
	 */
	public Integer updateDrTbnAllocToDc(Map<String, Object> params);
	
	/**
	 * 配货明细 -  发货中
	 * @param params drTbnId dgnCode
	 * @return
	 */
	public Integer updateDrTbnAllocToSp(Map<String, Object> params);
	
	/**
	 * 配货明细 -  已发货
	 * @param params sfGdnId drTbnId dgnCode
	 * @return
	 */
	public Integer updateDrTbnAllocToSc(Map<String, Object> params);
	
	/**
	 * 配货明细 -  收货中
	 * @param params gdnCode drTbnId
	 * @return
	 */
	public Integer updateDrTbnAllocToRp(Map<String, Object> params);
	
	/**
	 * 配货明细 -  已收货
	 * @param params gdnCode sfGrnId drTbnId
	 * @return
	 */
	public Integer updateDrTbnAllocToRc(Map<String, Object> params);
	
	/**
	 * 根据老ERP入库单更新配货明细 -  已收货
	 * @param params gdnCode sfGrnId drTbnId
	 * @return
	 */
	public Integer updateDrTbnAllocByGrnToRc(Map<String, Object> params);
	
	/**
	 * 已配货 更新调配单明细的数量
	 * @param drTbnId
	 * @return
	 */
	public Integer updateDrTbnQty(Long drTbnId);
	
	/**
	 * 已配货 更新调配单的数量
	 * @param drTbnId
	 * @return
	 */
	public Integer updateDrTbnQtyVAL(Long drTbnId);
	
	/**
	 * 根据调配单id查询调配单
	 * @param gdnId
	 * @return
	 */
	public DrTbnVo selectTbnById(Long tbnId);
	
	/**
	 * 根据调配单code查询调配单
	 * @param code
	 * @return
	 */
	public DrTbnVo selectTbnByCode(String code);
	
	/**
	 * 根据新ERPtbn_num获取税率
	 * @param tbn_num
	 * @return
	 */
	public Double selectTaxRateFromDrTbn(String tbn_num);
}