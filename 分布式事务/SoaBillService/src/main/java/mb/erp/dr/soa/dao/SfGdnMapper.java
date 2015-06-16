package mb.erp.dr.soa.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.bean.EnMarginProdNumBean;
import mb.erp.dr.soa.vo.SfDgnVo;
import mb.erp.dr.soa.vo.SfGdnDtlVo;
import mb.erp.dr.soa.vo.SfGdnVo;

/**
 * 新ERP出库单数据接口
 * 包含接口：查询出库明细单、保存 保存出库单-表头、保存 出库单明细、更新出库单处理进度、撤销	、查询出库单状态
 * @author     郭明帅
 * @version    1.0, 2014-12-07
 * @see         SfGdnMapper
 * @since       全流通改造
 */
public interface SfGdnMapper {
	/**
	 * 查询出库明细单
	 * @param orderSearchBean
	 * @return
	 */
	public List<SfGdnDtlVo> searchSfGdnDtl(SfGdnDtlVo sfGdnDtl);
	/**
	 * 保存 保存出库单-表头
	 * @param gdn
	 * @return
	 */
	public int save(SfGdnVo sfGdn);
	
	/**
	 * 保存 出库单明细
	 * @param gdn
	 * @return
	 */
	public int saveDtl(SfGdnDtlVo sfGdnDtl);
	
	/**
	 * 更新出库单
	 * @param gdn
	 */
	public void updateSfGdn(SfGdnVo sfGdn);
	
	/**
	 * 更新出库单详情
	 * @param gdn
	 */
	public void updateSfGdnDtl(SfGdnDtlVo sfGdnDtl);
	
	/**
	 * 更新出库单明细的单位成本
	 * @param gdn
	 */
	public void updateUnitCost(SfGdnVo sfGdn);
	
	/**
	 * 撤销	
	 * 可撤销出库单，进度在录入中、已确认、已审核后才可以撤销
	 * @param gdn
	 */
	public void cancel(SfGdnVo sfGdn);
	
	/**
	 * 查询出库单状态
	 * @param gdn
	 * @return
	 */
	public int selectDocState(SfGdnVo sfGdn);
	
	/**
	 * 根据出库单id查询出库单
	 * @param gdnId
	 * @return
	 */
	public SfGdnVo selectGdnById(Long gdnId);
	
	/**
	 * 根据出库单查询详情
	 * @param code
	 * @return
	 */
	public List<SfGdnDtlVo> selectGdnDtlById(SfGdnVo sfGdn);
	
	/**
	 * 根据出库单code查询出库单
	 * @param code
	 * @return
	 */
	public SfGdnVo selectGdnByCode(String code);
	
	/**
	 * 根据到货通知单查询出库单code
	 * @param rvdId
	 * @return
	 */
	public String getCodeByRvd(Long rvdId);
	
	/**
	 * 根据交货单生成出库单
	 * @param id, sfGdnId, createUser, docState(已确认)
	 */
	public void insertByDgn(SfDgnVo dgn);
	
	/**
	 * 根据交货单生成出库单明细
	 * @param id, sfGdnId, 
	 */
	public SfGdnVo genSfGdnBySfDgn(SfDgnVo dgn);
	
	/**
	 * 根据出库单明细更新总单的数量和金额
	 * @param sfGdnId, id
	 */
	public List<SfGdnDtlVo> genSfGdnDtlBySfDgnDtl(SfDgnVo dgn);
	
	/**
	 * 查询未指定货位Id的明细
	 * @param id
	 * @return List<SfGdnDtlVo>
	 */
	public List<SfGdnDtlVo> selectGdnDtlWithoutLocId(Long id);
	
	/**
	 * 根据出库单查询成本组是否活动
	 * @param id
	 * @return String
	 */
	public String getOpModeByGdn(Long id);
	
	/**
	 * 根据出库单查询成本组id
	 * @param id
	 * @return Long
	 */
	public Long getCostGrpIdByGdn(Long id);
	
	/**
	 * 更新出库单成本核算标志位
	 * @param map
	 */
	public void updateCostCHG(Map<String, Object> map);
	
	/**
	 * 获取成本核算方式
	 * @param id
	 * @return Integer
	 */
	public Integer getCalType(Long id);
	
	/**
	 * 
	 * 判断出库仓库的成本组对应的出库方式是否参与成本核算
	 * @param map
	 * @return integer  1:参与
	 */
	public Integer isCostAcount(Map<String, Object> map);
	
	/**
	 * 更新出库单明细成本
	 * @param map
	 */
	public void updateDetailUnitCost(Map<String, Object> map);
	
	/**
	 * 更新出库单明细成本
	 * @param map
	 */
	public void updateDetailUnitCostByGrpCost(Map<String, Object> map);
	/**
	 * 更新出库单成本
	 * @param map
	 */
	public void updateCost(Map<String, Object> map);
	
	/**
	 * 根据出库单更新成本表
	 * @param id
	 */
	public void updateFiGrpCostByGdn(Long id);
	
	/**
	 * 根据出库单插入成本表
	 * @param id
	 */
	public void insertFiGrpCostByGdn(Long id);
	
	/**
	 * 根据出库单查询没有成本的商品
	 * @param id
	 * @return List<Long>
	 */
	public List<Long> getProdNoCost(Long id);
	
	/**
	 * 根据出库单明细 更新成本表总数量和总成本
	 * @param id
	 */
	public void updateGrpCostStockByGdn(Long id);
	
	/**
	 * 移动加权 更新更新成本表
	 * @param map
	 */
	public void updateInitGrpCostStockByGdn(Map<String, Object> map);
	
	/**
	 * 添加库存成本(初始化) 
	 * @param id
	 */
	public void insertFiGrpCostInitByGdn(Long id);
	/**
	 * 根据出库号获取所有需要核算的商品款
	 * @param id
	 * @return List<EnMarginProdNumBean>
	 */
	public List<EnMarginProdNumBean> getMarginProdNum(Long id);
	
	
	public List<EnMarginProdNumBean> getCurrentMonthTotal(
			Map<String, Object> map);
	
	/**
	 * 根据出入库进行成本核算
	 * @param map
	 */
	public void updateUnitCostByGrnGdn(Map<String, Object> map);
	
}