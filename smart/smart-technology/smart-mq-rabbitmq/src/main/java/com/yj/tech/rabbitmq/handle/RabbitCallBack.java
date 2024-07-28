package com.yj.tech.rabbitmq.handle;

import com.yj.tech.common.util.LogUtil;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnsCallback;
import com.yj.tech.common.util.verification.ValidateUtils;
import java.util.Collection;

/**
 *  mq消息处理中转类
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
            LogUtil.error(getClass(), "RabbitMQConfig:消息发送失败:correlationData({}),ack(false),cause({})", correlationData, cause);
        }
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
