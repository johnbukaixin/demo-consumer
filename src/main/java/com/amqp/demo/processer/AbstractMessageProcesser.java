package com.amqp.demo.processer;

import org.springframework.amqp.core.Message;

/**
 * created by panta on 2018/1/22.
 *
 * @author panta
 */
public abstract class AbstractMessageProcesser {

    public abstract boolean processMessage(Message message);

}
