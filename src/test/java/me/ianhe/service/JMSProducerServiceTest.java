package me.ianhe.service;

import me.ianhe.model.MailModel;
import me.ianhe.utils.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;

/**
 * @author iHelin
 * @since 2017/8/11 09:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class JMSProducerServiceTest {

    @Autowired
    private JMSProducerService producerService;

    @Autowired
    @Qualifier("mailQueue")
    private Destination destination;

    @Test
    public void testSendMessage() throws Exception {
        MailModel mail = new MailModel("ahaqhelin@163.com;1018954240@qq.com", "何霖", "测试一下", "你号码");
        producerService.sendMessage(destination, JSON.toJson(mail));
    }

}