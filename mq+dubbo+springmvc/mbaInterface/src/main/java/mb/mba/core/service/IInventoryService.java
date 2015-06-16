package mb.mba.core.service;

import mb.mba.core.bean.InventoryHelper;
import mb.mba.core.bean.Message;
import mb.mba.core.entity.InventoryEntity;
import mb.mba.core.entity.InventoryTransEntity;
import mb.mba.core.entity.TradesEntity;
import mb.mba.core.exception.MbaException;


/**
 * 
 * @描述 库存增减
 * @author sun
 * @date 2015年6月1日
 */
public interface IInventoryService {
//
//	/**
//	 * 出库交易操作 可包含 库存、金额的操作 
//	 * @param gdnDto
//	 * @return 返回Message对象包含失败、成功
//	 */
//	public Message billDeliverStorage(DeliverStorageDto deliverStorageDto) throws MbaException;
//	
//	/**
//	 *  入库交易操作 可包含 库存、金额的操作 
//	 * @param grnDto
//	 * @return 返回Message对象包含失败、成功
//	 */
//	public Message AddStorageGrn(AddStorageDto addStorageDto) throws MbaException;
//	
//	
//	/**
//	 * 查询出入库交易信息页面显示用
//	 * @param queryDto
//	 * @param ispage 是否需要分页
//	 * @param PageBounds mybatis分页插件
//	 * @return
//	 */
//	public List<InventoryTransactionInfoDto> queryInventoryTransInfo(QueryInventoryDto queryDto,boolean ispage,PageBounds bounds) throws MbaException;
//
//	
//	/**
//	 * 查询出入库交易明细信息页面显示用
//	 * @param queryDto
//	 * @param ispage 是否需要分页
//	 * @param PageBounds mybatis分页插件
//	 * @return
//	 */
//	public List<InventoryTransactionDtlInfoDto> queryInventoryTransDtlInfo(QueryInventoryDto queryDto,boolean ispage,PageBounds bounds) throws MbaException;
//
//	
//	/**
//	 * 查询多货主库存信息页面显示用
//	 * @param queryDto
//	 * @param ispage 是否需要分页
//	 * @param bounds mybatis分页插件
//	 * @return
//	 */
//	public List<InventoryInfoDto> queryInventoryInfo(QueryInventoryDto queryDto,boolean ispage,PageBounds bounds) throws MbaException;
//	
//	/**
//	 * 添加出入库交易流水信息
//	 * @param tradeTransDto
//	 * @return
//	 */
//	public Message addTradeTrans(TradeTransDto tradeTransDto) throws MbaException;
//	
//	/**
//	 * 添加库存流水信息
//	 * @param inventoryTransDto
//	 * @return
//	 */
//	public Message addInventoryTrans(InventoryTransDto inventoryTransDto) throws MbaException;
//	
	
	/**
	 * 库存流水
	 * @param entity
	 * @return
	 * @throws MbaException
	 */
	public Message addInventoryTrans(InventoryTransEntity entity) throws MbaException;
	/**
	 * 操作库存：加减
	 * @param entity
	 * @return
	 * @throws MbaException
	 */
	public Message operateInventoryValue(TradesEntity entity) throws MbaException;
	/**
	 * 根据交易单据查询库存
	 * @param entity
	 * @return
	 */
	public InventoryEntity queryInventoryByParam(InventoryHelper helper);
	
}
