package mb.erp.dr.o2o.service.impl.webservice;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import mb.erp.dr.o2o.service.impl.producer.JmsProduceService;
import mb.erp.dr.o2o.service.webservice.IWsJmsProducer;

/**
 * 
 * @author cyyu
 *
 */
@WebService
@SOAPBinding(style=Style.RPC)
public class WsJmsProducer implements IWsJmsProducer {
	
	@Resource
	private JmsProduceService jmsProducerService;
	/**
	 * 将C#的json对象转化成java对象，保存进队列
	 */
	public void initialData(String jsonVo)  {
		jmsProducerService.initialData(jsonVo);
	}

}
