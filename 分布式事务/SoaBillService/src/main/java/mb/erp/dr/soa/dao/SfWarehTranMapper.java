package mb.erp.dr.soa.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.bean.NewOrderSearchBean;
import mb.erp.dr.soa.vo.SfWarehCommitedTranVo;
import mb.erp.dr.soa.vo.SfWarehOrderTranVo;
import mb.erp.dr.soa.vo.SfWarehTransitTranVo;

/**
 * 新ERP仓库相关事务接口
 * 包含接口：
 * @author     余从玉
 * @version    1.0, 2015-1-8
 * @since       全流通改造
 */
public interface SfWarehTranMapper {
	
	/**
	 * 查询已分配事务
	 * @param searchBean 必填：warehId ,docType,docNum (DOC_CODE字段)
	 * @return
	 */
	public List<SfWarehCommitedTranVo> searchCommitTranInfo(NewOrderSearchBean searchBean);
	/**
	 * 添加仓库商品事务-根据出库单
	 * @param params isAdd gdnId
	 * @return
	 */
	public Integer insertSfWarehTranByGdn(Map<String, Object> params);
	
	/**
	 * 添加仓库商品事务-根据入库单
	 * @param params isAdd grnId
	 * @return
	 */
	public Integer insertSfWarehTranByGrn(Map<String, Object> params);
	
	/**
	 * 添加货位商品事务-根据出库单
	 * @param params isAdd gdnId
	 * @return
	 */
	public Integer insertSfLocTranByGdn(Map<String, Object> params);
	
	/**
	 * 添加货位商品事务-根据入库单
	 * @param params isAdd grnId
	 * @return
	 */
	public Integer insertSfLocTranByGrn(Map<String, Object> params);
	
	/**
	 * 添加已分配库存事务-根据交货单
	 * @param params isAdd dgnId
	 * @return
	 */
	public Integer insertSfWarehCommitedTranByDgn(Map<String, Object> params);
	
	/**
	 * 添加在购库存事务-根据交货单
	 * @param params isAdd dgnId
	 * @return
	 */
	public Integer insertSfWarehOrderTranByDgn(Map<String, Object> params);
	
	/**
	 * 添加在途库存事务-根据出库单
	 * @param params isAdd gdnId
	 * @return
	 */
	public Integer insertSfWarehTransitTranByGdn(Map<String, Object> params);
	
	/**
	 * 添加在途库存事务-根据入库单
	 * @param params isAdd grnId
	 * @return
	 */
	public Integer insertSfWarehTransitTranByGrn(Map<String, Object> params);
	
	/**
	 * 查询在购库存事务
	 * @param searchBean 必填：warehId ,docType,docNum (DOC_CODE字段)
	 * @return
	 */
	public List<SfWarehOrderTranVo> searchOrderTranInfo(NewOrderSearchBean searchBean);
	
	/**
	 * 查询在途库存事务
	 * @param searchBean 必填：warehId ,docType,docNum (DOC_CODE字段)
	 * @return
	 */
	public List<SfWarehTransitTranVo> searchTransitTranInfo(NewOrderSearchBean searchBean);
	
}