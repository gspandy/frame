package mb.erp.dr.o2o.service.webservice;

import javax.jws.WebService;
/**
 * 
 * @author cyyu
 *
 */
@WebService
public interface IWsJmsProducer {

	/**
	 * 原始数据队列( B2C发货消息写入队列接口)
	 * @param vo
	 */
	public void initialData(String jsonVo)    ;
	
}
