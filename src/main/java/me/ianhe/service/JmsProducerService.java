package me.ianhe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.io.Serializable;

/**
 * @author iHelin
 * @since 2017/6/23 09:56
 */
@Service
public class JmsProducerService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    /**
     * 发送文本消息
     *
     * @author iHelin
     * @since 2017/8/31 11:18
     */
    public void sendMessage(Destination destination, final String message) {
        logger.debug("-----生产者发送了一个消息--------");
        jmsTemplate.send(destination, session -> session.createTextMessage(message));
    }

    /**
     * 发送实现了序列化接口的对象
     *
     * @author iHelin
     * @since 2017/8/31 11:17
     */
    public void sendMessage(Destination destination, final Serializable message) {
        logger.debug("-----生产者发送了一个消息--------");
        jmsTemplate.send(destination, session -> session.createObjectMessage(message));
    }
}
