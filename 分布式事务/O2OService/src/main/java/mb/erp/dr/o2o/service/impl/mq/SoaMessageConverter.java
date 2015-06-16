package mb.erp.dr.o2o.service.impl.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

/**
 * 对象和消息转换器
 * 
 * @author Weijf
 * @version
 * @see
 * @since
 */

public class SoaMessageConverter implements MessageConverter {

    /**
     * 接收消息时，转换消息体
     **/
    public Object fromMessage(Message message) throws JMSException,
            MessageConversionException {
    	Object msg = null;
        if (message instanceof ObjectMessage) {
            msg =((ObjectMessage) message).getObject();
        }else if (message instanceof ActiveMQTextMessage) {
        	msg =((ActiveMQTextMessage)message).getText();
		}
        return msg;
    }

    /**
     * 发送消息，转换消息体
     **/
    public Message toMessage(Object obj, Session session) throws JMSException,
            MessageConversionException {
    	ObjectMessage message = session.createObjectMessage();
//    	message.setStringProperty("unique", "");
        message.setObject(obj.toString());
        return message;
    }

}
