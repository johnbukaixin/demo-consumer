package com.amqp.demo.contants;

/**
 * created by panta on 2018/1/22.
 *
 * @author panta
 */
public class MQConstants {

    /**
     * 你的业务交换机名称
     */
    public static final String BUSINESS_EXCHANGE = "business.exchange";
    /**你的业务队列名称*/
    public static final String BUSINESS_QUEUE = "business.queue";
    /**你的业务key*/
    public static final String BUSINESS_KEY = "business.key";


    public static final String MQ_PRODUCER_RETRY_KEY = "mq.producer.retry.key";
    public static final String MQ_CONSUMER_RETRY_COUNT_KEY = "mq.consumer.retry.count.key";
    /**死信队列配置*/
    public static final String DLX_EXCHANGE = "dlx.exchange";
    public static final String DLX_QUEUE = "dlx.queue";
    public static final String DLX_ROUTING_KEY = "dlx.routing.key";
}
