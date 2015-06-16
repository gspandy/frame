package mb.mba.center.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import mb.mba.center.dao.CostAccountingGroupEntityMapper;
import mb.mba.center.dao.CostAccountingPeriodEntityMapper;
import mb.mba.core.bean.Message;
import mb.mba.core.bean.QueryCostAccountingPeriodBean;
import mb.mba.core.constant.MbaDictionaryConstant;
import mb.mba.core.entity.CostAccountingGroupEntity;
import mb.mba.core.entity.CostAccountingPeriodEntity;
import mb.mba.core.entity.TradesEntity;
import mb.mba.core.exception.MbaException;
import mb.mba.core.service.ICostPeriodService;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @描述: 封账服务实现
 * @author czj 
 * @date 2015年6月2日
 * @version 1.0.0
 */
public class CostPeriodServiceImpl implements ICostPeriodService{

	@Resource
	private CostAccountingPeriodEntityMapper costAccountingPeriodEntityMapper; 
	
	@Resource
	private CostAccountingGroupEntityMapper costAccountingGroupEntityMapper;
	
	
	@Override
	public Message checkCostPeriod(TradesEntity entity) throws MbaException{
		Message message = new Message();
		message.setSuccess(true);
		
		String unitCode = null;
		String warehCode = null;
		CostAccountingPeriodEntity accountingPeriodEntity = null;
		
		int count = 0;
		//出库
		if(!StringUtils.isEmpty(entity.getDocType()) 
				&& MbaDictionaryConstant.MBA__STORAGE_TYPE__OUT.equals(entity.getDocType())) {
			unitCode = entity.getVenderCode();
			warehCode = entity.getVenderWarehCode();
		//入库
		}else if(!StringUtils.isEmpty(entity.getDocType()) 
				&& MbaDictionaryConstant.MBA__STORAGE_TYPE__IN.equals(entity.getDocType())) {
			unitCode = entity.getVendeeCode();
			warehCode = entity.getVendeeWarehCode();
		}else {
			message.setSuccess(false);
			message.setMsg("单据类型不符合");
			return message;
		}
		//组织是否有成本组
		count = costAccountingGroupEntityMapper.isHaveCostGroup(unitCode);
		
		if (count > 0) {
			//根据仓库获取成本组
			CostAccountingGroupEntity costAccountingGroupEntity = costAccountingGroupEntityMapper.getByWarehCode(warehCode);
			
			if (costAccountingGroupEntity != null) {
				//获取会计期间
				List<CostAccountingPeriodEntity> accountingPeriodEntities = costAccountingPeriodEntityMapper.getByCostId(costAccountingGroupEntity.getId());
				
				if(CollectionUtils.isNotEmpty(accountingPeriodEntities)) {
					
					for(CostAccountingPeriodEntity period : accountingPeriodEntities){
						Date temp = new Date();
						if (period.getStartDate().getTime() <= temp.getTime() && temp.getTime() <= period.getEndDate().getTime()) {
							accountingPeriodEntity = period;
							break;
						}
					}
					
					if (accountingPeriodEntity != null){
						//会计期间 状态"打开"
						if(!StringUtils.isEmpty(accountingPeriodEntity.getAcStatus()) &&
								accountingPeriodEntity.getAcStatus().equals(MbaDictionaryConstant.MBA__COST_PERIOD_STATUS__OPEN)){
							return message;
						} else {
							message.setSuccess(false);
							message.setMsg("会计期间状态已关闭,无法进行出入库");
						}
					}
				}
			}
		}
		
		return message;
	}
	
	@Override
	public Message addCostPeriod(
			CostAccountingPeriodEntity costAccountingPeriodEntity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Message modifyCostPeriod(
			CostAccountingPeriodEntity costAccountingPeriodEntity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<CostAccountingPeriodEntity> queryCostPeriod(
			QueryCostAccountingPeriodBean queryCostAccountingPeriodBean) {
		// TODO Auto-generated method stub
		return null;
	}

}
