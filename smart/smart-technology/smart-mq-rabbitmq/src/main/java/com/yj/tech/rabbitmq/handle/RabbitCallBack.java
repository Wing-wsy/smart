package com.yj.tech.rabbitmq.handle;

import com.yj.tech.utils.logger.LogUtil;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnsCallback;
import com.yj.tech.utils.verification.ValidateUtils;
import java.util.Collection;

/**
 *  mq消息处理中转类
 *
 *  1）【ReturnCallback】机制则是为了解决消息无法路由到指定队列的问题。‌通过实现ReturnCallback接口，‌可以在消息发送
 *  失败时（‌如路由不到队列时）‌触发回调。这需要在配置文件中添加配置spring.rabbitmq.publisher-returns=true以开启ReturnCallback功能
 *
 *  2）【ConfirmCallback】在消息成功从生产者发送到RabbitMQ Broker后执行。‌ConfirmCallback机制确保消息已经被正确地发送到RabbitMQ Broker并被处理，
 *  ‌从而避免了消息丢失或重复发送的情况。‌当配置spring.rabbitmq.publisher-confirms=true时，‌ConfirmCallback被触发，‌表示消息已经到达服务器
 */
public class RabbitCallBack implements ReturnsCallback, ConfirmCallback {

    /** 处理器集合 */
    private static Collection<AbstractRabbitCallBack> rabbitCallBackList;

    public RabbitCallBack(Collection<AbstractRabbitCallBack> rabbitCallBackList) {
        RabbitCallBack.rabbitCallBackList = rabbitCallBackList;
    }

    /** 消息发送后，无论发送失败或是成功，都会回调该方法 */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            // 消息未到达队列，可处理重试逻辑或记录日志
            LogUtil.error(getClass(), "RabbitMQConfig:消息发送失败:correlationData({}),ack(false),cause({})", correlationData, cause);
        }
        // 消息成功到达队列
        if (ValidateUtils.isNotEmpty(RabbitCallBack.rabbitCallBackList)) {
            // 执行处理器集合中的saveRabbitCallBack方法
            for (AbstractRabbitCallBack abstractRabbitCallBack : RabbitCallBack.rabbitCallBackList) {
                abstractRabbitCallBack.saveRabbitCallBack(correlationData, ack, cause);
            }
        }
    }

    /** mq消息丢失会回调该方法 */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        LogUtil.error(getClass(), "RabbitMQConfig:消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",
                returned.getExchange(), returned.getRoutingKey(), returned.getReplyCode(), returned.getReplyText(), returned.getMessage());
        if (ValidateUtils.isNotEmpty(RabbitCallBack.rabbitCallBackList)) {
            // 执行处理器集合中的saveReturnedMessage方法
            for (AbstractRabbitCallBack abstractRabbitCallBack : RabbitCallBack.rabbitCallBackList) {
                abstractRabbitCallBack.saveReturnedMessage(returned);
            }
        }
    }
}
