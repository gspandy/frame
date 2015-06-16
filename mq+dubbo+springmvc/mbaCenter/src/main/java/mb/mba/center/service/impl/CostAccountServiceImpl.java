package mb.mba.center.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.mba.center.dao.CostAccountingGroupEntityMapper;
import mb.mba.center.dao.CostAccountingPeriodEntityMapper;
import mb.mba.core.bean.CostAccountBean;
import mb.mba.core.bean.Message;
import mb.mba.core.bean.QueryCostAccountingGroupBean;
import mb.mba.core.constant.MbaDictionaryConstant;
import mb.mba.core.entity.CostAccountingGroupEntity;
import mb.mba.core.entity.CostAccountingPeriodEntity;
import mb.mba.core.entity.TradesEntity;
import mb.mba.core.exception.MbaException;
import mb.mba.core.service.ICostAccountService;
import mb.mba.core.service.ICostPeriodService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @描述: 成本核算服务实现
 * @author czj
 * @date 2015年6月2日
 * @version 1.0.0
 */
public class CostAccountServiceImpl implements ICostAccountService {

	/**
	 * 
	 */
	private static Logger logger = LoggerFactory.getLogger(CostAccountServiceImpl.class);

	@Resource
	private ICostPeriodService costPeriodService;

	@Resource
	private CostAccountingGroupEntityMapper costAccountingGroupEntityMapper;

	@Resource
	private CostAccountingPeriodEntityMapper costAccountingPeriodEntityMapper;
	/**
	 * 出库交易成本核算
	 */
	public Message calculationByDeliverStorage(TradesEntity entity)
			throws MbaException {
		Message message = null;

		// 判断账期是否已封
		message = costPeriodService.checkCostPeriod(entity);

		if (!message.getSuccess()) {

			return message;
		}

		try {
			// 成本组是否活动
			boolean result = checkCostGrpByDelivStroage(entity.getId());
			if (!result) {
				// TODO标志位置为F
			}
			// 获取成本核算方式
			Integer calType = getCalType(entity.getId(),true);
			
			switch(calType){
			//月末加权
			case MbaDictionaryConstant.MBA__CAL_TYPE__0:
				delivAndAddStroageCostAccountType0(entity,true);
				break;
			default:
				break;
			}

		} catch (MbaException e) {
			message.setSuccess(false);
			message.setMsg(e.getMessage());
		}
		
		return message;
	}


	/**
	 * 入库交易成本核算
	 */
	public Message calculationByAddStorage(TradesEntity entity)
			throws MbaException {
		Message message = null;

		// 判断账期是否已封
		message = costPeriodService.checkCostPeriod(entity);

		if (!message.getSuccess()) {

			return message;
		}

		try {
			// 成本组是否活动
			boolean result = checkCostGrpByDelivStroage(entity.getId());
			if (!result) {
				// TODO标志位置为F
			}
			// 获取成本核算方式
			Integer calType = getCalType(entity.getId(),true);
			
			switch(calType){
			//月末加权
			case MbaDictionaryConstant.MBA__CAL_TYPE__0:
				delivAndAddStroageCostAccountType0(entity,true);
				break;
			default:
				break;
			}

		} catch (MbaException e) {
			message.setSuccess(false);
			message.setMsg(e.getMessage());
		}
		
		return message;
	}

	/**
	 * 手动触发
	 */
	@Override
	public Message calculationByManual(CostAccountBean accountBean){
		Message message = new Message();
		//数据检查
		try {
			message = calculationCheck(accountBean);
		} catch (MbaException e) {
			
		}
		//是否调用异步消息接口
		
		return null;
	}

	@Override
	public Message addCostAccountingGroup(
			CostAccountingGroupEntity costAccountingGroupEntity) {
		return null;
	}
	
	
	@Override
	public Message modifyCostAccountingGroup(
			CostAccountingGroupEntity costAccountingGroupEntity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Message deleteCostAccountingGroup(
			CostAccountingGroupEntity costAccountingGroupEntity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<CostAccountingGroupEntity> queryCostAccountingGroup(
			QueryCostAccountingGroupBean queryCostAccountingGroupBean) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 出入库月末加权
	 * @param entity 出入库单据
	 * @param flag true:出库单 false:入库单
	 */
	private void delivAndAddStroageCostAccountType0(TradesEntity entity,boolean flag) throws MbaException{
		//查询仓库的出入库方式是否符合
		boolean result = false;
		try {
			//出库单
			if (flag) {
				result = isDelivTypeConfirm(entity.getId(),MbaDictionaryConstant.MBA__CAL_TYPE__0);
			} else {
				result = idAddTypeConfirm(entity.getId(),MbaDictionaryConstant.MBA__CAL_TYPE__0);
			}
			if (result) {
				//出库单成本核算
				if (flag) {
					
				//入库单成本核算	
				} else {
					
				}
			}
			
		} catch (Exception e) {
			throw new MbaException("","出入库单据月末加权成本核算发生异常");
		}
		
	}

	/**
	 * 查询出库单的出入库方式是否满足成本核算
	 * @param id
	 * @param calType
	 * @return
	 */
	private boolean isDelivTypeConfirm(Long id, int calType) {
		boolean flag = false;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("type", calType);
		
		Integer result = costAccountingGroupEntityMapper.isDelivTypeConfirm(map);
		
		if(result != null && result == 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 查询出库单的出入库方式是否满足成本核算
	 * @param id
	 * @param calType
	 * @return
	 */
	private boolean idAddTypeConfirm(Long id, int calType) {
		boolean flag = false;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("type", calType);
		
		Integer result = costAccountingGroupEntityMapper.isAddTypeConfirm(map);
		
		if(result != null && result == 1) {
			flag = true;
		}
		return flag;
	}


	/**
	 * 根据出库单ID判断成本组是否活动
	 * 
	 * @param id
	 * @return
	 * @throws MbaException
	 */
	private boolean checkCostGrpByDelivStroage(Long id) throws MbaException {
		boolean result = true;
		try {
			CostAccountingGroupEntity entity = costAccountingGroupEntityMapper
					.getCostGrpByDelivId(id);
			if (entity == null) {
				result = false;
				logger.info("出库单" + id + "成本组未设置");
			}
		} catch (Exception e) {
			throw new MbaException("E001", "出库交易判断成本组是否活动发生异常");
		}
		return result;
	}
	
	/**
	 * 根据出入库单Id查询成本核算方式
	 * @param id 出入库单Id
	 * @param flag true:出库单  false:入库单
	 * @return
	 */
	private Integer getCalType(Long id, boolean flag) throws MbaException{
		CostAccountingGroupEntity entity = null;
		Integer result = 999;
		try {
			if (flag) {
				entity = costAccountingGroupEntityMapper.getCostGrpByDelivId(id);
			} else {
				entity = costAccountingGroupEntityMapper.getCostGrpByAddId(id);
			}
			
			if (entity == null) {
				logger.info("出入库单" + id + "成本组未设置,成本核算方式获取失败");
			} else {
				result = entity.getCostacCode();
			}
		} catch (Exception e) {
			throw new MbaException("","出入库交易获取成本核算方式发生异常");
		}
		
		return result;
	}
	
	/**
	 * 成本核算数据预检查
	 * @param accountBean
	 * @return boolean true:成功 false:失败
	 * @throws MbaException 
	 */
	private Message calculationCheck(CostAccountBean accountBean) throws MbaException {
		Message message = new Message(true);
		CostAccountingGroupEntity groupEntity = accountBean.getCostAccountingGroupEntity();
		CostAccountingPeriodEntity periodEntity = accountBean.getCostAccountingPeriodEntity();
		try {
			groupEntity = costAccountingGroupEntityMapper.selectValidCostGrpById(groupEntity.getId());
			if (null == groupEntity || groupEntity.getCostacCode() != MbaDictionaryConstant.MBA__CAL_TYPE__0){
				message.setSuccess(false);
				message.setMsg("成本组不存在或成本核算方式不属于月末加权");
				return message;
			}
			periodEntity = costAccountingPeriodEntityMapper.selectValidPeriodById(periodEntity.getId());
			//会计期间不存在、成本已结转
			if (null == periodEntity 
					|| MbaDictionaryConstant.MBA__COST_CHECKOUT_STATUS__YES.equals(periodEntity.getAcCheckout())){
				message.setSuccess(false);
				message.setMsg("会计期间不存在或会计期间内成本已结转");
				return message;
			}
		} catch (Exception e) {
			throw new MbaException("", "月末加权成本核算数据预检查时发生异常");
		}
		return message;
	}
}
