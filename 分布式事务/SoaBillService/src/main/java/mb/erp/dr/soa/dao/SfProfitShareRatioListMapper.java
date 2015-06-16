package mb.erp.dr.soa.dao;

import java.util.Map;

import mb.erp.dr.soa.vo.SfProfitShareRatioListVo;

/**
 * 利益分享dao
 * @author     余从玉
 * @version    1.0, 2014-11-28
 * @see         SfProfitShareRatioListVo
 * @since       全流通改造
 */
public interface SfProfitShareRatioListMapper {

	/**
	 * 根据全局取利益分享比例
	 * @param orgCode
	 * @param businessSource
	 * @param deliveryMode
	 * @return
	 */
    public SfProfitShareRatioListVo getRatioByGlobal(Map<String, Object> params);
    
    /**
     * 根据组织编码得到利益分享比例
     * @param orgCode
     * @param businessSource
     * @param deliveryMode
     * @return
     */
    public SfProfitShareRatioListVo getRatioByOrg(Map<String, Object> params );
    
    /**
     * 取上级组织code
     * @param orgCode
     * @return
     */
    public String getOwnerCode(String orgCode);
    
    /**
     * 根据组织code返回组织id
     * @param orgCode
     * @return
     */
    public Integer getOrgIdByCode(String orgCode);
    
    

}