package mb.mba.center.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import mb.mba.center.dao.TaxrateAllEntityMapper;
import mb.mba.center.dao.TaxratePerEntityMapper;
import mb.mba.core.bean.Message;
import mb.mba.core.bean.TaxrateVo;
import mb.mba.core.entity.TaxRateEntity;
import mb.mba.core.entity.TaxrateAllEntity;
import mb.mba.core.entity.TaxratePerEntity;
import mb.mba.core.exception.MbaException;
import mb.mba.core.service.ITaxRateService;

public class TaxRateServiceImpl implements ITaxRateService {

	@Resource
	TaxratePerEntityMapper taxratePerEntityMapper;
	@Resource
	TaxrateAllEntityMapper taxrateAllEntityMapper;

	@Override
	public BigDecimal queryTaxRate(String unitId) throws MbaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaxratePerEntity queryTaxRatePerByUnitIdAndCpdUnitId(String unitId,
			String cpdunitId) throws MbaException {
		return taxratePerEntityMapper.selectByUnitIdAndCpdUnitId(unitId, cpdunitId);
	}

	@Override
	public TaxrateAllEntity queryTaxRateAllByUnitId(String unitId) throws MbaException {
		return taxrateAllEntityMapper.selectByUnitId(unitId);
	}

	@Override
	public List<TaxratePerEntity> queryAllTaxRate(String unitId)
			throws MbaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message addTaxRate(TaxRateEntity dto) throws MbaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message updateTaxRate(TaxRateEntity dto) throws MbaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message addTaxRateAll(TaxrateAllEntity dto) throws MbaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message addTaxRatePer(TaxratePerEntity dto) throws MbaException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取组织税率
	 * @param unitId
	 * @param cpdunitId
	 * @return TaxrateVo
	 * @throws MbaException
	 */
	@Override
	public TaxrateVo queryTaxRateByUnitIdAndCpdUnitId(String unitId,
			String cpdunitId) throws MbaException {
		TaxrateVo vo=new TaxrateVo(false, unitId, cpdunitId);
		TaxratePerEntity per=this.queryTaxRatePerByUnitIdAndCpdUnitId(unitId, cpdunitId);
		if(per==null){
			TaxrateAllEntity all=this.queryTaxRateAllByUnitId(unitId);
			vo.setAll(true);
			vo.setRate1(all.getRate());
			vo.setRate1Sacle(new BigDecimal(100));
		}else{
			BeanUtils.copyProperties(per, vo);
		}
		return vo;
	}

}
