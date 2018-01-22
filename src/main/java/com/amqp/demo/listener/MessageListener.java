package com.amqp.demo.listener;

import com.amqp.demo.processer.RabbitMaConsumerTaskProcesser;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * created by panta on 2018/1/22.
 *
 * @author panta
 */
@Component
public class MessageListener extends AbstractMessageListener {

    @Autowired
    private RabbitMaConsumerTaskProcesser processer;
    @Override
    public void receiveMessage(Message message, MessageConverter messageConverter) {
        processer.processMessage(message);
    }
}
