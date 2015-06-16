package mb.erp.dr.o2o.service.impl.mq;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;

/**
 * JMS消息发送类
 * 
 * @author Weijf
 * @version
 * @see
 * @since
 */
public class JmsSend {
	@Resource
    private JmsTemplate jmsTemplate;
	/**初始数据队列*/
	@Resource
    private Destination initialDataDestination;
	/**预处理队列*/
	@Resource
    private Destination prepareDestination;
	/**两方调配队列*/
	@Resource
    private Destination t2QueueDestination;
	/**订单队列*/
	@Resource
    private Destination idtQueueDestination;
	/**配货队列*/
	@Resource
    private Destination adnQueueDestination;
	/**出库队列*/
	@Resource
    private Destination gdnQueueDestination;
	/**入库队列*/
	@Resource
    private Destination grnQueueDestination;
	/**退货申请单队列*/
	@Resource
    private Destination bgrQueueDestination;
	/**退货出库队列 */
	@Resource
    private Destination thGdnQueueDestination;
	/**退货单队列 **/
	@Resource
    private Destination scnQueueDestination;
	/**退货入库队列**/
	@Resource
    private Destination thGrnQueueDestination;

	 /**
     * 发送到初始数据队列
     * @param vo
     * @
     */
    public void sendToInitialDataQueue(String sfSchTaskExecOosJson)  {
        // 发送消息
        jmsTemplate.convertAndSend(this.initialDataDestination, sfSchTaskExecOosJson);
    }
    
	/**
	 * 预处理队列
	 * @param vo
	 * @
	 */
    public void send(String sfSchTaskExecOosJson)  {
        // 发送消息
        jmsTemplate.convertAndSend(prepareDestination, sfSchTaskExecOosJson);
    }
    
    /**
     * 发送到两方调配队列
     * @param vo
     * @
     */
    public void sendToT2queue(String baseBizJson)  {
        // 发送消息
        jmsTemplate.convertAndSend(this.t2QueueDestination, baseBizJson);
    }
    
    /**
     * 发送到订单队列
     * @param vo
     * @
     */
    public void sendToIdtQueue(String sfSchTaskExecOosJson)  {
        // 发送消息
        jmsTemplate.convertAndSend(this.idtQueueDestination, sfSchTaskExecOosJson);
    }
    
    /**
     * 发送到配货队列
     * @param vo
     * @
     */
    public void sendToAdnQueue(String adnJson)  {
        // 发送消息
        jmsTemplate.convertAndSend(this.adnQueueDestination, adnJson);
    }
    
    /**
     * 发送到出库队列
     * @param vo
     * @
     */
    public void sendToGdnQueue(String gdnJson)  {
        // 发送消息
        jmsTemplate.convertAndSend(this.gdnQueueDestination, gdnJson);
    }
    
    /**
     * 发送到入库队列
     * @param vo
     * @
     */
    public void sendToGrnQueue(String grnJson)  {
        // 发送消息
        jmsTemplate.convertAndSend(this.grnQueueDestination, grnJson);
    }
    
    /**
     * 发送到退货申请单队列
     * @param sfSchTaskExecOosJson
     */
    public void sendToBgrQueue(String sfSchTaskExecOosJson) {
    	jmsTemplate.convertAndSend(this.bgrQueueDestination, sfSchTaskExecOosJson);
    }
    
    /**
     * 发送到退货出库队列
     * @param sfSchTaskExecOosJson
     */
    public void sendToThgdnQueue(String thGdnJson) {
    	jmsTemplate.convertAndSend(this.thGdnQueueDestination, thGdnJson);
    }
    
    /**
     * 发送到退货单队列
     * @param sfSchTaskExecOosJson
     */
    public void sendToScnQueue(String scnJson) {
    	jmsTemplate.convertAndSend(this.scnQueueDestination, scnJson);
    }
    
    /**
     * 发送到退货入库队列
     * @param sfSchTaskExecOosJson
     */
    public void sendToThgrnQueue(String sfSchTaskExecOosJson) {
    	jmsTemplate.convertAndSend(this.thGrnQueueDestination, sfSchTaskExecOosJson);
    }


}
