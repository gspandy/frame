package mb.mba.core.service;

import mb.mba.core.entity.AddStorageDto;
import mb.mba.core.entity.DeliverStorageDto;
import mb.mba.core.entity.PretreatmentDto;
import mb.mba.core.exception.MbaException;

/**
 * 预处理
 * 通过wms单据类型生成账务中心出入库交易信息 
 *
 */
public interface IPrepareService {
	
	/**
	 * 预处理 
	 * 通过wms入库单据 找到货主并生成账务中心入库交易信息
	 * @param AddStorageDto
	 * @return
	 */
	public AddStorageDto prepareAddStorage(PretreatmentDto dto) throws MbaException;
	
	/**
	 * 预处理
	 * 通过wms出库单据 找到货主并生成状物中心出库交易信息
	 * @param dto
	 * @return
	 */
	public DeliverStorageDto prepareDeliverStorage(PretreatmentDto dto) throws MbaException;
}
