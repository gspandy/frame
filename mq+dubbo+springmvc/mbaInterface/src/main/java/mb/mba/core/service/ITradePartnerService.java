package mb.mba.core.service;

import java.util.List;

import mb.mba.core.bean.Message;
import mb.mba.core.entity.TradePartnerDto;
import mb.mba.core.exception.MbaException;

/**
 * 贸易伙伴关系服务
 */
public interface ITradePartnerService {

	/**
	 * 建立组织间贸易伙伴关系
	 * @param unitId
	 * @param cpdUnitId
	 * @return
	 */
	public Message buildTradePartner(String unitId, String cpdUnitId) throws MbaException;

	
	/**
	 * 移除贸易伙伴关系
	 * @param unitId
	 * @param cpdUnitId
	 * @return
	 */
	public Message removeTradePartner(String unitId, String cpdUnitId) throws MbaException;

	
	/**
	 * 查询当前组织的所有贸易伙伴
	 * @param unitId
	 * @return
	 */
	public List<TradePartnerDto> queryTradePartner(String unitId) throws MbaException;
	
}
