package me.ianhe.service;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;
import java.io.Serializable;

/**
 * @author iHelin
 * @since 2017/6/23 09:56
 */
@Service
public class JMSProducerService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    public void sendMessage(Destination destination, final String message) {
        try {
            logger.debug("-----生产者发送了一个消息，队列：{}--------", ((ActiveMQQueue) destination).getQueueName());
            jmsTemplate.send(destination, session -> session.createTextMessage(message));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Destination destination, final Serializable message) {
        try {
            logger.debug("-----生产者发送了一个消息，队列：{}--------", ((ActiveMQQueue) destination).getQueueName());
            jmsTemplate.send(destination, session -> session.createObjectMessage(message));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
