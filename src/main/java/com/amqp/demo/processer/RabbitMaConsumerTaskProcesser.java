package com.amqp.demo.processer;


import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

/**
 * created by panta on 2018/1/22.
 *
 * @author panta
 */
@Component
public class RabbitMaConsumerTaskProcesser extends AbstractMessageProcesser {

    public boolean saveMq(Message message){
        return false;
    }

    @Override
    public boolean processMessage(Message message) {
        int i = 0; int c = 0;
        int b = i/c;
        System.out.println("idea:"+message.getMessageProperties().getMessageId() +new String(message.getBody()));
        return true;
    }
}
