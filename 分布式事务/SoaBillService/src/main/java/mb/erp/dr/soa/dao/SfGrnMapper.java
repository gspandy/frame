package mb.erp.dr.soa.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.bean.EnMarginProdNumBean;
import mb.erp.dr.soa.bean.OrderSearchBean;
import mb.erp.dr.soa.vo.SfGrnDtlVo;
import mb.erp.dr.soa.vo.SfGrnVo;
import mb.erp.dr.soa.vo.SfRvdVo;


/**
 * 新ERP入库单数据库接口
 * 包含接口：查询入库明细单、保存 保存入库单-表头、保存 入库单明细、更新入库单处理进度、撤销	、查询入库单状态
 * @author     余从玉
 * @version    1.0, 2014-12-08
 * @see         SfGrnMapper
 * @since       全流通改造
 */
public interface SfGrnMapper {

	/**
	 * 保存 保存入库单-表头
	 * @param grn
	 * @return
	 */
	public int save(SfGrnVo grn);
	
	/**
	 * 保存 入库单明细
	 * @param grn
	 * @return
	 */
	public int saveDtl(SfGrnDtlVo grnDtl);
	
	/**
	 * 查询入库单详情
	 * @param orderSearchBean
	 * @return
	 */
	public List<SfGrnDtlVo> searchGrnDtl(OrderSearchBean orderSearchBean);
	
	/**
	 * 根据入库单id查询单据
	 * @param grnId
	 * @return
	 */
	public SfGrnVo selectGrnById(Long grnId);
	
	/**
	 * 更新入库单处理进度
	 * @param grn
	 */
	public void updateProcess(SfGrnVo grn);
	
	/**
	 * 查询入库单状态
	 * @param grn
	 * @return
	 */
	public Integer selectProcess(SfGrnVo grn);
	
	/**
	 * 更新入库单总单单位成本
	 */
	public void updateGrnCost(SfGrnVo grn);
	
	/**
	 * 更新入库单明细 单位成本
	 */
	public void updateGrnUnitCost(SfGrnVo grn);
	
	/**
	 * 设置需要传给sap标志 
	 * @param grn
	 * @return
	 */
	public Integer updateSapFlag(SfGrnVo grn);
	
	/**
	 * 根据到货通知单生成入库单VO
	 * @param sfRvdId
	 * @return
	 */
	public SfGrnVo genSfGrnByRvd(Integer sfRvdId);
	
	/**
	 * 根据到货通知单生成入库单
	 * @param id, sfGrnId,
	 * @return 
	 */
	public Integer insertByRVD(SfRvdVo rvd);
	
	/**
	 * 根据到货通知单生成入库单明细
	 * @param id, sfGrnId
	 */
	public Integer insertDtlByRVD(SfRvdVo rvd);
	
	/**
	 * 根据入库单明细更新总单的数量和金额
	 * @param sfGrnId, 
	 */
	public void updateByGrnDtl(Long sfGrnId);
	
	/**
	 * 
	 * 根据入库单Id查询明细
	 * @param id
	 * @return List<SfGrnDtlVo>
	 */
	public List<SfGrnDtlVo> selectGrnDtlByGrnId(Long id);
	
	/**
	 * 
	 * 根据入库单Id查询明细 
	 * @param id
	 * @return  List<SfGrnDtlVo>
	 */
	public List<SfGrnDtlVo> selectGrnDtlWithoutLocId(Long id);

	/**
	 * 根据入库号获取所有需要核算的商品款
	 * @param id
	 * @return List<EnMarginProdNumBean>
	 */
	public List<EnMarginProdNumBean> getMarginProdNum(Long id);

	/**
	 * 
	 * @param map
	 */
	public List<EnMarginProdNumBean> getCurrentMonthTotal(
			Map<String, Object> map);
	
	/**
	 * 更新入库单成本核算标志位
	 * @param map
	 */
	public void updateCostCHG(Map<String, Object> map);

	/**
	 * 判断入库库单的成本组是否活动
	 * @param id
	 * @return String 1:活动
	 */
	public String getOpModeByGrn(Long id);

	/**
	 * 获取成本核算方式
	 * @param id
	 * @return integer
	 */
	public Integer getCalType(Long id);
	
	/**
	 * 判断入库仓库的成本组对应的出库方式是否参与成本核算
	 * @param map
	 * @return Integer
	 */
	public Integer isCostAcount(Map<String, Object> map);
	
	/**
	 * 更新入库单明细成本
	 * @param map
	 */
	public void updateDetailUnitCost(Map<String, Object> map);
	
	/**
	 * 更新入库单成本
	 * @param map
	 */
	public void updateCost(Map<String, Object> map);

	/**
	 * 根据入库单更新成本表
	 * @param id
	 */
	public void updateFiGrpCostByGrn(Long id);
	
	/**
	 * 根据入库单更新成本表
	 * @param map
	 */
	public void updateInitGrpCostStockByGrn(Map<String, Object> map);
	
	/**
	 * 根据入库单插入成本表
	 * @param id
	 */
	public void insertFiGrpCostByGrn(Long id);

	/**
	 * 获取没有成本的商品 
	 * @param id
	 * @return List<Long>
	 */
	public List<Long> getProdNoCost(Long id);

	/**
	 * 更新入库单明细成本(成本组获取)
	 * @param map
	 */
	public void updateDetailUnitCostByGrpCost(Map<String, Object> map);

	/**
	 * 更新成本表总数
	 * @param id
	 */
	public void updateGrpCostStockByGrn(Long id);

	/**
	 * 添加库存成本(初始化)
	 * @param id
	 */
	public void insertFiGrpCostInitByGrn(Long id);
	
	/**
	 * 根据入库单查询成本组ID
	 * @param id
	 * @return
	 */
	public Long getCostGrpIdByGrn(Long id);

}
