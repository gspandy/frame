package mb.erp.dr.soa.service.impl.balance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.dao.SfProfitShareRatioListMapper;
import mb.erp.dr.soa.service.balance.ProfitShareRatioService;
import mb.erp.dr.soa.vo.SfProfitShareRatioListVo;

import org.springframework.stereotype.Service;

/**
 * 利益分享服务接口
 * @author     余从玉
 * @version    1.0, 2014-11-28
 * @see         SfProfitShareRatioListVo
 * @since       全流通改造
 */
@Service
public class ProfitShareRatioServiceImpl implements ProfitShareRatioService {

	@Resource
	private SfProfitShareRatioListMapper sfProfitShareRatioListMapper;
	
	 /**
     * 读取利益分享比例接口
     * @param orgCodes 组织ID
     * @param businessSource 业务来源
     * @param deliveryMode 配发方式
     * @return
     */
    public List<SfProfitShareRatioListVo> getRatioList(List<String> orgCodes, String businessSource, String deliveryMode){
        List<SfProfitShareRatioListVo> result = new ArrayList<SfProfitShareRatioListVo>();
        for (String orgCode : orgCodes){
        	SfProfitShareRatioListVo ratioInfo = getRatio(orgCode, businessSource, deliveryMode);
        	result.add(ratioInfo );
        }
        return result;
    	
    }

    /**
     * 读取一个组织ID的利益分销比例信息
     * @param orgCode 组织ID
     * @param businessSource 业务来源
     * @param deliveryMode 配发方式
     * @return
     */
    public SfProfitShareRatioListVo getRatio(String orgCode, String businessSource, String deliveryMode){
        SfProfitShareRatioListVo ratioInfo = getRatioByOrg(orgCode, businessSource, deliveryMode);
        if (ratioInfo == null){
            ratioInfo = getRatioByGlobal(orgCode, businessSource, deliveryMode);
        }
        setServerRatio(ratioInfo, orgCode);
        return ratioInfo;
    }
    /**
     * 通过组织ID来读取
     * @param orgCode 组织ID
     * @param businessSource 业务来源
     * @param deliveryMode 配发方式
     * @return
     */
    private SfProfitShareRatioListVo getRatioByOrg(String orgCode, String businessSource, String deliveryMode){
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("orgCode", orgCode);
    	params.put("businessSource", businessSource);
    	params.put("deliveryMode", deliveryMode);
        SfProfitShareRatioListVo ratioInfo = sfProfitShareRatioListMapper.getRatioByOrg(params);

        if (ratioInfo == null){
        	String ownerCode = sfProfitShareRatioListMapper.getOwnerCode(orgCode);
            if (ownerCode == null || orgCode.equals(ownerCode)){//ownerID没找到或者ownerID等于自己ID的说明没有上级
                return null;
            }
            return getRatioByOrg(ownerCode, businessSource, deliveryMode);
        }
        return ratioInfo;
    }
    /**
     * 取全局
     * @param orgCode 组织ID
     * @param businessSource 业务来源
     * @param deliveryMode 配发方式
     * @return
     */
    private SfProfitShareRatioListVo getRatioByGlobal(String orgCode, String businessSource, String deliveryMode){
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("orgCode", orgCode);
    	params.put("businessSource", businessSource);
    	params.put("deliveryMode", deliveryMode);
        return sfProfitShareRatioListMapper.getRatioByGlobal(params);
    }

    /**
     * 设置中间方比例
     * @param ratioInfo
     * @param orgId 组织ID
     */
    private void setServerRatio(SfProfitShareRatioListVo ratioInfo,String orgCode){
        if (ratioInfo == null) return;
        Integer orgId = sfProfitShareRatioListMapper.getOrgIdByCode(orgCode);
        ratioInfo.setBfOrgId(orgId); 
        
        if (ratioInfo.getBusinessSource().equals("2")){
            ratioInfo.setServerRatio(ratioInfo.getDeliveryMode().equals("2") ? 2d : 10d);
        }else {
            ratioInfo.setServerRatio(0d);
        }
    }

}
