package mb.mba.center.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import mb.mba.center.dao.InventoryEntityMapper;
import mb.mba.center.dao.InventoryTransEntityMapper;
import mb.mba.core.bean.InventoryHelper;
import mb.mba.core.bean.Message;
import mb.mba.core.constant.MbaDictionaryConstant;
import mb.mba.core.entity.InventoryEntity;
import mb.mba.core.entity.InventoryTransEntity;
import mb.mba.core.entity.TradesDtlEntity;
import mb.mba.core.entity.TradesEntity;
import mb.mba.core.exception.MbaException;
import mb.mba.core.service.IInventoryService;

import org.springframework.beans.BeanUtils;

public class InventoryServiceImpl implements IInventoryService {
	@Resource
	InventoryEntityMapper inventoryEntityMapper;
	@Resource
	InventoryTransEntityMapper inventoryTransEntityMapper;

	@Override
	public Message addInventoryTrans(InventoryTransEntity entity)
			throws MbaException {
		Message msg = new Message();
		int m = inventoryTransEntityMapper.insert(entity);
		msg.setSuccess(m == 1 ? true : false);
		return msg;
	}

	@Override
	public InventoryEntity queryInventoryByParam(InventoryHelper helper) {
		return inventoryEntityMapper.queryInventory(helper);
	}
	/**
	 * 操作库存
	 * @param trade 出入库单据，内含明细
	 * @return Message
	 */
	@Override
	public Message operateInventoryValue(TradesEntity trade)
			throws MbaException {
		Message msg = new Message(true);
		if(!checkTradeForOperateInventoryValue(trade)){
			msg.setSuccess(false);
			msg.setMsg("帐务中心：出入库单据数据异常-->");
			msg.setCode("error");
			return msg;
		}
		String type=trade.getDocType();//出库or入库
		String brand=trade.getBrand();//三方品牌??
		if(type.equals(MbaDictionaryConstant.MBA__STORAGE_TYPE__IN)){
			//加
			this.addInventoryValue(trade);
		}else if(type.equals(MbaDictionaryConstant.MBA__STORAGE_TYPE__OUT)){
			//减
			this.subInventoryValue(trade);
		}else{
			msg.setSuccess(false);
			msg.setMsg("帐务中心：不正确的单据类型-->"+type);
			msg.setCode("error");
		}
		return msg;
	}

	/**
	 * 加
	 * @param trade
	 * @param entity
	 * @return
	 * @throws MbaException
	 */
	private Message addInventoryValue(TradesEntity trade)throws MbaException {
		Message msg = new Message(true);
		List<TradesDtlEntity> dtls=trade.getDtls();
		for(TradesDtlEntity dtl:dtls){
			InventoryHelper helper=InventoryHelper.getInventoryHelper(trade,dtl);
			//库存存在？
			InventoryEntity entity=this.queryInventoryByParam(helper);
			if(entity!=null){
				helper.setId(entity.getId());
				inventoryEntityMapper.updateInventoryValue(helper);
			}else{
				entity=this.genInventoryEntityForInsert(helper);
				inventoryEntityMapper.insert(entity);
			}
		}
		return msg;
	}
	
	/**
	 * 减
	 * @param trade
	 * @param entity
	 * @return
	 * @throws MbaException
	 */
	private Message subInventoryValue(TradesEntity trade)throws MbaException {
		Message msg = new Message(true);
		List<TradesDtlEntity> dtls=trade.getDtls();
		for(TradesDtlEntity dtl:dtls){
			InventoryHelper helper=InventoryHelper.getInventoryHelper(trade,dtl);
			//库存存在？
			InventoryEntity entity=this.queryInventoryByParam(helper);
			if(entity!=null){
				//减法结果为负？
				if(!checkSubValueNegative(entity, helper)){
					helper.setId(entity.getId());
				    inventoryEntityMapper.updateInventoryValue(helper);
				}else{
					msg.setCode("error");
					msg.setMsg("帐务中心：多货主库存扣减结果为负-->记账仓："+helper.getAcwarehCode()+" 库存仓："+helper.getInventoryPlace());
					msg.setSuccess(false);
				}
			}else{
				msg.setCode("error");
				msg.setMsg("帐务中心：多货主库存扣减结果为负-->记账仓："+helper.getAcwarehCode()+" 库存仓："+helper.getInventoryPlace());
				msg.setSuccess(false);
			}
		}
		return msg;
	}
	
	/**
	 * 减法结果为负:true=负,false=非负
	 * 功能描述： 检查库存减法结果是否为负
	 * @author:sunjm
	 * @2015年6月4日  
	 * @param:
	 * @version
	 */
	private boolean checkSubValueNegative(InventoryEntity entity,InventoryHelper helper){
		if(entity==null)return true;
		if(entity.getInventoryQuantity().compareTo(helper.getInventoryQuantity())<0)return true;
		if(entity.getPurchOntheway().compareTo(helper.getPurchOntheway())<0)return true;
		if(entity.getAlloOntheway().compareTo(helper.getAlloOntheway())<0)return true;
		return false;
	}
	/**
	 * true=检查通过,false=不通过
	 * 功能描述： 检查库存扣减的参数
	 * @author:sunjm
	 * @2015年6月4日  
	 * @param:
	 * @version
	 */
	private boolean checkTradeForOperateInventoryValue(TradesEntity entity) {
		if(entity==null||entity.getVendeeWarehCode()==null||entity.getVenderWarehCode()==null)return false;
		if(entity.getDtls()==null)return false;
		List<TradesDtlEntity> dtls=entity.getDtls();
		for(TradesDtlEntity dtl:dtls){
			if(dtl==null||dtl.getQuantity()==null||dtl.getQuantity()==null)return false;
		}
		return true;
	}
	/**
	 * 
	 * 功能描述： 生成需要的实体类
	 * @author:sunjm
	 * @2015年6月4日  
	 * @param:
	 * @version
	 */
	private InventoryEntity genInventoryEntityForInsert(InventoryHelper helper){
		InventoryEntity entity=new InventoryEntity();
		BeanUtils.copyProperties(helper, entity);
		entity.setCreateOper("wo");
		entity.setCreateTime(new Date());
		entity.setWarehCode(helper.getInventoryPlace());
		return entity;
	}
}
