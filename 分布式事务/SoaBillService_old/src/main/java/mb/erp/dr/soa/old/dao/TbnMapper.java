package mb.erp.dr.soa.old.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.bean.OrderSearchBean;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.old.vo.TbnDtlVo;
import mb.erp.dr.soa.old.vo.TbnVo;


/**
 * 调配单数据库接口
 * 包含接口：查询调配明细单、保存 保存调配单-表头、保存 调配单明细、更新调配单处理进度、撤销	、查询调配单状态
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         TbnMapper
 * @since       全流通改造
 */
public interface TbnMapper {

	/**
	 * 保存 保存调配单-表头
	 * @param tbn
	 * @return
	 */
	public int save(TbnVo tbn);
	
	/**
	 * 保存 调配单明细
	 * @param tbn
	 * @return
	 */
	public int saveDtl(TbnDtlVo tbnDtl);
	
	/**
	 * 查询调配单明细
	 * @param orderSearchBean
	 * @return
	 */
	public List<TbnDtlVo> searchTbnDtl(OrderSearchBean orderSearchBean);
	
	/**
	 * 更新调配单处理进度
	 * @param tbn
	 */
	public void updateProcess(TbnVo tbn);
	
	/**
	 * 撤销	
	 * 可撤销调配单，进度在录入中、已确认、已审核后才可以撤销
	 * @param tbn
	 */
	public void cancel(TbnVo tbn);
	
	/**
	 * 查询调配单状态
	 * @param tbn
	 * @return
	 */
	public String selectProcess(TbnVo tbn);
	
	/**
	 * 原始单据修改-调配单表头（发货）
	 * @param tbn
	 * @return
	 */
	public Integer updateOriginalTbnF(TbnVo tbn);
	/**
	 * 原始单据修改-调配单明细 （发货）
	 * @param tbn
	 * @return
	 */
	public Integer updateOriginalTbnDtlF(TbnVo tbn);
	/**
	 * 原始单据修改-调配单表头 （收货）
	 * @param tbn
	 * @return
	 */
	public Integer updateOriginalTbnS(TbnVo tbn);
	/**
	 * 原始单据修改-调配单明细 （收货）
	 * @param tbn
	 * @return
	 */
	public Integer updateOriginalTbnDtlS(TbnVo tbn);
	/**
	 * 原始单据修改-调配单表头 （已发货接口）
	 * @param tbn
	 * @return
	 */
	public Integer updateOriginalTbnYfh(TbnVo tbn);
	/**
	 * 原始单据修改-调配单明细 （已发货接口）
	 * @param tbn
	 * @return
	 */
	public Integer updateOriginalTbnDtlYfh(TbnVo tbn);
	/**
	 * 原始单据修改-调配单表头 （已收货接口）
	 * @param tbn
	 * @return
	 */
	public Integer updateOriginalTbnYsh(TbnVo tbn);
	/**
	 * 原始单据修改-调配单明细 （已收货接口）
	 * @param tbn
	 * @return
	 */
	public Integer updateOriginalTbnDtlYsh(TbnVo tbn);
	
	/**
	 * 根据出库单 GDN 查询 调配单
	 */
	public TbnVo selectTbnByGdn(GdnVo gdnVo);
	
	/**
	 * 根据入库单 GDN 查询 调配单
	 */
	public TbnVo selectTbnByGrn(GrnVo grnVo);
	
	/**
	 * 从调配单中获取税率
	 * @param map
	 * @return
	 */
	public Double selectTaxRateFromTbn(Map map);
	
	/**
	 * 更新调配单
	 * @param tbnVo
	 * @return Integer
	 */
	public Integer updateTbnByPrimaryKey(TbnVo tbnVo);
}
