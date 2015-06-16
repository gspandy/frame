package mb.mba.core.service;

import java.math.BigDecimal;
import java.util.List;

import mb.mba.core.bean.Message;
import mb.mba.core.bean.TaxrateVo;
import mb.mba.core.entity.TaxRateEntity;
import mb.mba.core.entity.TaxrateAllEntity;
import mb.mba.core.entity.TaxratePerEntity;
import mb.mba.core.exception.MbaException;

/**
 * 税率服务
 */
public interface ITaxRateService {

	/**
	 * 获取组织税率
	 * @param unitId
	 * @return
	 */
	public BigDecimal queryTaxRate(String unitId) throws MbaException;
	
	/**
	 * 获取组织税率
	 * @param unitId
	 * @param cpdunitId
	 * @return
	 * @throws MbaException
	 */
	public TaxrateVo queryTaxRateByUnitIdAndCpdUnitId(String unitId,String cpdunitId) throws MbaException;
	
	public TaxratePerEntity queryTaxRatePerByUnitIdAndCpdUnitId(String unitId,String cpdunitId) throws MbaException;
	
	public TaxrateAllEntity queryTaxRateAllByUnitId(String unitId) throws MbaException;
	/**
	 * 获取组织所有税率信息
	 * @param unitId
	 * @return
	 */
	public List<TaxratePerEntity> queryAllTaxRate(String unitId) throws MbaException;
	
	/**
	 * 增加税率种类
	 * @param dto
	 * @return
	 */
	public Message addTaxRate(TaxRateEntity dto) throws MbaException;
	
	/**
	 * 修改税率种类
	 * @param dto
	 * @return
	 */
	public Message updateTaxRate(TaxRateEntity dto) throws MbaException;
	/**
	 * 增加该组织的全局税率
	 * @param dto
	 * @return
	 * @throws MbaException
	 */
	public Message addTaxRateAll(TaxrateAllEntity dto) throws MbaException;
	/**
	 * 增加该组织对贸易伙伴的单独税率
	 * @param dto
	 * @return
	 * @throws MbaException
	 */
	public Message addTaxRatePer(TaxratePerEntity dto) throws MbaException;
}
