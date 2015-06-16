package mb.erp.dr.soa.old.service.impl.dubbo;

import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.old.dao.AgentBrandRateListMapper;
import mb.erp.dr.soa.old.service.dubbo.SoaPriceDubboService;

import org.springframework.stereotype.Service;

/**
 * 新erp需要调用到老erp的价格服务
 * 
 * @author     余从玉
 * @version    1.0, 2014-12-9
 * @see         SoaPriceDubboService
 * @since       全流通改造
 */
@Service
public class SoaPriceDubboServiceImpl implements SoaPriceDubboService {

	@Resource
	private AgentBrandRateListMapper agentBrandRateListMapper;
	
	@SuppressWarnings("rawtypes")
	public Double selectObject(Map map) {
		return agentBrandRateListMapper.selectObject(map);
	}

}
