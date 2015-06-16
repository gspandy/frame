package mb.erp.dr.soa.old.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.bean.OrderSearchBean;
import mb.erp.dr.soa.old.vo.GrnDtlVo;
import mb.erp.dr.soa.old.vo.GrnVo;


/**
 * 入库单数据库接口
 * 包含接口：查询入库明细单、保存 保存入库单-表头、保存 入库单明细、更新入库单处理进度、撤销	、查询入库单状态
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         GrnMapper
 * @since       全流通改造
 */
public interface GrnMapper {

	/**
	 * 保存 保存入库单-表头
	 * @param grn
	 * @return
	 */
	public int save(GrnVo grn);
	
	/**
	 * 保存 入库单明细
	 * @param grn
	 * @return
	 */
	public int saveDtl(GrnDtlVo grnDtl);
	
	/**
	 * 查询入库单详情
	 * @param orderSearchBean
	 * @return
	 */
	public List<GrnDtlVo> searchGrnDtl(OrderSearchBean orderSearchBean);
	
	/**
	 * 更新入库单处理进度
	 * @param grn
	 */
	public void updateProcess(GrnVo grn);
	
	/**
	 * 撤销	
	 * 可撤销入库单，进度在录入中、已确认、已审核后才可以撤销
	 * @param grn
	 */
	public void cancel(GrnVo grn);
	
	/**
	 * 查询入库单状态
	 * @param grn
	 * @return
	 */
	public String selectProcess(GrnVo grn);
	
//	/**
//	 * 更新入库单总单单位成本
//	 */
//	public void updateGrnCost(GrnVo grn);
//	
//	/**
//	 * 更新入库单明细 单位成本
//	 */
//	public void updateGrnUnitCost(GrnVo grn);
	
	/**
	 * 更新入库单总单单位成本
	 * @param map
	 */
	public void updateCost(Map<String, Object> map);
	
	/**
	 * 更新入库单明细 单位成本
	 * @param map
	 */
	public void updateUnitCost(Map<String, Object> map);
	
	/**
	 * 设置需要传给sap标志 
	 * @param grn
	 * @return
	 */
	public Integer updateSapFlag(GrnVo grn);
	
	/**
	 * 设置需要传给sap标志 
	 * @param grn
	 * @return
	 */
	public Integer updateSapFlagTwo(GrnVo grn);
	
	/**
	 * 更新入库原因
	 * @param grn
	 */
	public Integer updateRcvState(GrnVo grn);
	
	/**
	 * 设置需要传给sap标志 
	 * @param grn
	 */
	public Integer updateSapFlagForH(GrnVo grn);
	
	/**
	 * 更新新ERP传输标志
	 * @param grn
	 */
	public Integer updateNewFlag(GrnVo grn);
	
	/**
	 * 查询未指定货位的明细信息
	 * @param grn
	 * @return List<GrnDtlVo>
	 */
	public List<GrnDtlVo> selectGrnDtlWithOutLocId(GrnVo grn);
	
	/**
	 * 根据入库单查询是否有活动的成本组
	 * @param map
	 * @return Integer
	 */
	public Integer checkCostGrpByGrn(Map<String, Object> map);
	
	/**
	 * 根据入库单查询入库方式
	 * @param map
	 * @return String
	 */
	public String selectRcptMode(Map<String, Object> map);
	
	/**
     * 将新ERP的入库单号写入到老ERP入库单
     * @param grnVo
     * @return
     */
	public Integer updateNewErpGrnCode(GrnVo grnVo);
}
