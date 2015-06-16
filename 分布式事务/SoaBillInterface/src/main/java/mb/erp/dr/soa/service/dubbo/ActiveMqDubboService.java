package mb.erp.dr.soa.service.dubbo;

import mb.erp.dr.soa.vo.SfSchTaskExecOosVo;

/**
 * 老erp需要调用到新erp的队列dubbo服务
 * 
 * @author     余从玉
 * @version    1.0, 2014-12-9
 * @see         ActiveMqDubboService
 * @since       全流通改造
 */
public interface ActiveMqDubboService {

	/**
	 * 持久化mq消息体
	 * @param vo
	 * @return
	 */
	public Integer saveSfSchTaskExecOos(SfSchTaskExecOosVo vo);
	
}
