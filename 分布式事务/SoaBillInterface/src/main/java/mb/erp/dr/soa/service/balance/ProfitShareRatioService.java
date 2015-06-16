package mb.erp.dr.soa.service.balance;

import java.util.List;

import mb.erp.dr.soa.vo.SfProfitShareRatioListVo;

/**
 * 利益分享服务接口
 * @author     余从玉
 * @version    1.0, 2014-11-28
 * @see         SfProfitShareRatioListVo
 * @since       全流通改造
 */
public interface ProfitShareRatioService {

	/**
	 * 批量获得利益分享比例
	 * 
	 * @param orgCode 组织CODE集合
	 * @param businessSource 业务来源
	 * @param deliveryMode 配发方式
	 * @return
	 */
	public List<SfProfitShareRatioListVo> getRatioList(List<String> orgCode, String businessSource, String deliveryMode);
	
	/**
	 * 获得利益分享比例
	 * 
	 * @param orgCode 组织CODE
	 * @param businessSource 业务来源
	 * @param deliveryMode 配发方式
	 * @return
	 */
	public SfProfitShareRatioListVo getRatio(String orgCode, String businessSource, String deliveryMode);
}
