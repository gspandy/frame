package mb.mba.center.service.impl;

import java.util.List;

import javax.annotation.Resource;

import mb.mba.center.dao.trades.TradesDtlEntityMapper;
import mb.mba.center.dao.trades.TradesEntityMapper;
import mb.mba.center.dao.trades.TradesTransEntityMapper;
import mb.mba.center.service.proxy.NostroService;
import mb.mba.core.bean.Message;
import mb.mba.core.bean.TaxrateVo;
import mb.mba.core.bean.TradesHelper;
import mb.mba.core.constant.MbaDictionaryConstant;
import mb.mba.core.entity.TradesDtlEntity;
import mb.mba.core.entity.TradesEntity;
import mb.mba.core.entity.TradesTransEntity;
import mb.mba.core.exception.MbaException;
import mb.mba.core.service.IInventoryService;
import mb.mba.core.service.ITaxRateService;
import mb.mba.core.service.ITradeService;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;

/**
 * @描述: 出入库交易服务实现
 * @author sun@mb.com  
 * @date 2015年5月29日
 * @version
 */
public class TradeServiceImpl implements ITradeService {

	@Resource
	TradesEntityMapper tradesEntityMapper;
	@Resource
	TradesDtlEntityMapper tradesDtlEntityMapper;
	@Resource
	TradesTransEntityMapper tradesTransEntityMapper;
	@Resource
	ITaxRateService iTaxRateService;
	@Resource
	IInventoryService iInventoryService;
	@Resource
	NostroService nostroService;
	/**
	 * 入库
	 * @param TradesEntity
	 * @param TradesDtlEntity
	 * @return Message
	 */
	@Override
	public Message tradeInInventory(TradesEntity entity,List<TradesDtlEntity> dtls) throws MbaException {
		Message msg=new Message();
		entity.setDtls(dtls);
		//会计帐
		
		//1.贸易伙伴关系处理
		
		//2.出入库方式处理
		TradesHelper helper=TradesHelper.getTradesHelper(entity);
		//3.批次接口???
		//4.价格逻辑
		if(helper.isCalprice()){
			this.calcprice(entity);
		}
		//5.折让逻辑
		if (helper.isCaldisamount()) {
			this.calcdisamount(entity);
		}
		//6.税率逻辑
		if(helper.isCaltaxrate()){
			this.calctaxrate(entity);
		}
		//7.成本核算逻辑or接口???
		if(helper.isCalunitcost()){
			this.calcunitcost(entity);
		}
		//8.往来账扣减接口
		boolean result=false;
		if(helper.isCalcurrentaccount()){
			//调用接口
			String type=entity.getDocType();
			if(type.equals(MbaDictionaryConstant.MBA__STORAGE_TYPE__IN)){
				
			}else{
				nostroService.reduceBalancePositive(entity.getVendeeCode(), entity.getVenderCode(), entity.getIntaxAmount().doubleValue(), entity.getDocDate(), entity.getDocType(), entity.getDocNum());
			}
			result=true;//扣减成功
		}
		//9.库存扣减接口
		if(result){
			iInventoryService.operateInventoryValue(entity);
		}
		//处理结果回传WMS ???
		//传入SAP控制  ???
		//合并单据逻辑  ???
		//拆分单据逻辑  ???
		
		this.addTradeAndTrans(entity);
		return msg;
	}

	/**
	 * 出库
	 * @param TradesEntity
	 * @param TradesDtlEntity
	 * @return Message
	 */
	@Override
	public Message tradeOutInventory(TradesEntity entity,List<TradesDtlEntity> dtls) throws MbaException {
		entity.setDtls(dtls);
		Message msg=new Message();
		this.addTradeAndTrans(entity);
		return msg;
	}
	
	/**
	 * 获取价格 TODO 调用接口
	 * @param entity
	 * @return Message
	 */
	private Message calcprice(TradesEntity entity) {
		List<TradesDtlEntity> dtls=entity.getDtls();
		//调用接口
		for(TradesDtlEntity dtl:dtls){
			//dtl.setDisrate();
			//dtl.setPrice();
			//tradesDtlEntityMapper.updateDtlsPriceAndDisrate(dtl);
			tradesDtlEntityMapper.updateDtlsPrice(dtl);
		}
		return new Message();
	}
	/**
	 * 成本计算 TODO 调用接口
	 * @param entity
	 * @return Message
	 */
	private Message calcunitcost(TradesEntity entity) {
		//调用接口
		return new Message();
	}

	/**
	 * 折让 TODO 调用接口--价格接口中有????
	 * @param entity
	 * @return Message
	 */
	private Message calcdisamount(TradesEntity entity) {
		List<TradesDtlEntity> dtls=entity.getDtls();
		//调用接口List<MsgListVo> msgList= $settlementPriceRateService.bulkGetSettlementPriceRateByProdIdList(prod_numlist,$gdn.getUnitId(),$gdn.getRcvUnitId(),$gdn.getCurrency());
		for(TradesDtlEntity dtl:dtls){
			//dtl.setDisrate();
			//dtl.setPrice();
			//tradesDtlEntityMapper.updateDtlsPriceAndDisrate(dtl);
			tradesDtlEntityMapper.updateDtlsDisrate(dtl);
		}
		return new Message();
	}
	/**
	 * 税率 TODO 待修改
	 * @param entity
	 * @return Message
	 * @throws MbaException 
	 */
	private Message calctaxrate(TradesEntity entity) throws MbaException {
		List<TradesDtlEntity> dtls=entity.getDtls();
		String unitId=entity.getVendeeCode();
		String cpdunitId=entity.getVenderCode();
		TaxrateVo vo=iTaxRateService.queryTaxRateByUnitIdAndCpdUnitId(unitId, cpdunitId);
		for(TradesDtlEntity dtl:dtls){
			if(!vo.isAll()){
				dtl.setGoodsSaletax(vo.getRate1());
				dtl.setGoodsSaletaxScale(vo.getRate1Sacle());
				dtl.setServiceSaletax(vo.getRate2());
				dtl.setServiceSaletaxScale(vo.getRate2Sacle());
				dtl.setTax3Saletax(vo.getRate3());
				dtl.setTax3SaletaxScale(vo.getRate3Sacle());
			}else{
				dtl.setGoodsSaletax(vo.getRate1());
				dtl.setGoodsSaletaxScale(vo.getRate1Sacle());
			}
			tradesDtlEntityMapper.updateDtlsTaxRate(dtl);
		}
		return new Message();
	}

	//TODO 流水记录信息需要优化
	@Override
	public Message addTradeAndTrans(TradesEntity trades)
			throws MbaException {
		TradesTransEntity entity=new TradesTransEntity();
		BeanUtils.copyProperties(trades, entity);
		entity.setTranQuantity(trades.getQuantity());
		//entity.setBalance(trades.get);交易后的库存量
		entity.setGoodsCode(trades.getBrand());
		entity.setWarehCode(trades.getRelTradeWarehCode());
		tradesEntityMapper.insert(trades);
		tradesDtlEntityMapper.bathinsert(trades.getDtls());
		tradesTransEntityMapper.insert(entity);
		return null;
	}

	@Override
	public List<TradesEntity> queryTradesListByParams(TradesEntity entity,
			RowBounds rb) {
		return tradesEntityMapper.queryTradesList(entity,rb);
	}

	@Override
	public int queryTradesListCountByParams(TradesEntity entity) {
		return tradesEntityMapper.queryTradesListCount(entity);
	}

	@Override
	public List<TradesDtlEntity> queryTradesDtlListByParams(
			TradesEntity entity, RowBounds rb) {
		return tradesDtlEntityMapper.queryTradesDtlList(entity,rb);
	}

	@Override
	public int queryTradesDtlListCountByParams(TradesEntity entity) {
		return tradesDtlEntityMapper.queryTradesDtlListCount(entity);
	}
}
