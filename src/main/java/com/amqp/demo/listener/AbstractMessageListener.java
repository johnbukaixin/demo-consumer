package com.amqp.demo.listener;


import com.amqp.demo.contants.MQConstants;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * created by panta on 2018/1/22.
 *
 * @author panta
 */
public abstract class AbstractMessageListener implements ChannelAwareMessageListener {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 消费次数
     */
    private Integer maxConsumerCount = 3;

    @Autowired
    private Jackson2JsonMessageConverter messageConverter;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 接收消息，子类必须实现该方法
     *
     * @param message          消息对象
     * @param messageConverter 消息转换器
     */
    public abstract void receiveMessage(Message message, MessageConverter messageConverter);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        MessageProperties messageProperties = message.getMessageProperties();
        Long deliveryTag = messageProperties.getDeliveryTag();
        Long consumerCount = redisTemplate.opsForHash().increment(MQConstants.MQ_CONSUMER_RETRY_COUNT_KEY,
                messageProperties.getMessageId(), 1);

        logger.info("当前消息ID:{} 消费次数：{}", messageProperties.getMessageId(), consumerCount);

        try {
            receiveMessage(message, messageConverter);
            // 成功的回执
            channel.basicAck(deliveryTag, false);
            // 如果消费成功，将Redis中统计消息消费次数的缓存删除
            redisTemplate.opsForHash().delete(MQConstants.MQ_CONSUMER_RETRY_COUNT_KEY,
                    messageProperties.getMessageId());
        } catch (Exception e) {
            logger.error("RabbitMQ 消息消费失败，" + e.getMessage(), e);
            if (consumerCount >= maxConsumerCount) {
                // 入死信队列
                channel.basicReject(deliveryTag, false);
            } else {
                // 重回到队列，重新消费
                channel.basicNack(deliveryTag, false, true);
            }
        }
    }

    public Integer getMaxConsumerCount() {
        return maxConsumerCount;
    }

    public void setMaxConsumerCount(Integer maxConsumerCount) {
        this.maxConsumerCount = maxConsumerCount;
    }
}
