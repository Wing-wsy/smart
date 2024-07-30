package com.yj.tech.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.yj.tech.common.exception.BaseException;
import com.yj.tech.common.util.LogUtil;
import com.yj.tech.common.util.json.JackJsonUtils;
import com.yj.tech.common.util.verification.ValidateUtils;
import com.yj.tech.rabbitmq.annotation.RabbitMq;
import com.yj.tech.rabbitmq.annotation.RabbitMqRetry;
import com.yj.tech.rabbitmq.code.MqErrorCode;
import com.yj.tech.rabbitmq.entity.RabbitMqModel;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import java.nio.charset.StandardCharsets;

/**
 * RabbitMq消费器
 */
public abstract class AbstractConsumer<T, R> extends MessageListenerAdapter {

    /**
     * 重试次数（如果这个值设置为 int 最大值，那么不再支持重试功能，即使使用了@RabbitMqRetry 配置了重试次数也不再生效）
     */
    private final int retryNumber = 1;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        MessageProperties messageProperties = message.getMessageProperties();
        long deliveryTag = messageProperties.getDeliveryTag();
        String correlationId = messageProperties.getHeader("spring_returned_message_correlation");
        boolean isAutoAck = getIsAutoAck();
        try {
            // 消费者内容
            String json = new String(message.getBody(), StandardCharsets.UTF_8);
            LogUtil.debug(getClass(), "线程名:{},AbstractConsumer:消费者消息: {}", Thread.currentThread().getName(), json);
            RabbitMqModel rabbitMqModel = JackJsonUtils.conversionClass(json, RabbitMqModel.class);
            // 判断是否重复消费
            if (checkMessageKey(messageProperties,rabbitMqModel)) {
                throw new BaseException(MqErrorCode.MESSAGE_REPEAT_CONSUMPTION);
            }
            // 消费消息
            R result = this.handleMessage((T) rabbitMqModel.getBody());
            if (!isAutoAck) {
                // 手动确认消息
                channel.basicAck(deliveryTag, false);
            }
            // 保存消费成功消息
            saveLog(result, message, rabbitMqModel);
        } catch (Throwable e) {
            // 消费失败次数不等于空并且失败次数大于某个次数,不处理直接return,并记录到数据库
            LogUtil.error(getClass(), "AbstractConsumer:消费报错 异常为:", e);
            // 将消费失败的记录保存到数据库或者不处理也可以
            this.saveFailMessage(message, e);
            // 保存重试失败次数达到retryNumber上线后拒绝此消息入队列并删除redis
            saveFailNumber(messageProperties, channel, deliveryTag, correlationId);
            throw e;
        } finally {
            // 删除判断重复消费Key
            deleteCheckMessageKey(messageProperties);
        }
    }

    /**
     * 记录失败次数并决定是否拒绝此消息
     */
    public void saveFailNumber(MessageProperties messageProperties, Channel channel, long deliveryTag, String correlationId) throws Exception {
        Integer lock = messageProperties.getHeader("retryNumber");
        Integer actualLock = ValidateUtils.isEmpty(lock) ? 1 : lock + 1;
        LogUtil.error(getClass(), "rabbitMQ 失败记录:消费者correlationId为:{},deliveryTag为:{},失败次数为:{}", correlationId, deliveryTag, actualLock);
        // @RabbitMqRetry 配置的重试次数
        int retryNumber = getRetryNumber();
        if (retryNumber <= this.retryNumber || actualLock >= retryNumber) {
            if (retryNumber <= this.retryNumber) {
                LogUtil.error(getClass(), "rabbitMQ 失败记录:因记录不需要重试因此直接拒绝此消息,消费者correlationId为:{},消费者设置重试次数为:{}", correlationId, retryNumber);
            } else {
                LogUtil.error(getClass(), "rabbitMQ 失败记录:已满足重试次数,删除redis消息并且拒绝此消息,消费者correlationId为:{},重试次数为:{}", correlationId, actualLock);
            }
            channel.basicNack(messageProperties.getDeliveryTag(), false, false);
        } else {
            LogUtil.error(getClass(), "rabbitMQ 失败记录:因记录重试次数还未达到重试上限，还将继续进行重试,消费者correlationId为:{},消费者设置重试次数为:{},现重试次数为:{}", correlationId, retryNumber, actualLock);
            messageProperties.setHeader("retryNumber", actualLock);
        }
    }

    /**
     * 获取重试次数，默认为1
     */
    public int getRetryNumber() {
        RabbitMqRetry rabbitMqRetry = getClass().getAnnotation(RabbitMqRetry.class);
        return ValidateUtils.isEmpty(rabbitMqRetry) ? retryNumber : rabbitMqRetry.retryNumber();
    }

    /**
     * 获取是否是自动确认
     */
    public boolean getIsAutoAck() {
        RabbitMq rabbitMq = getClass().getAnnotation(RabbitMq.class);
        return ValidateUtils.isNotEmpty(rabbitMq) && ValidateUtils.equalsIgnoreCase(AcknowledgeMode.AUTO.toString(), rabbitMq.mode().toString());
    }

    /**
     * 消费方法
     * @param body 请求数据
     */
    public abstract R handleMessage(T body) throws Exception;

    /**
     * 保存消费失败的消息
     *
     * @param message mq所包含的信息
     * @param e       异常
     */
    public void saveFailMessage(Message message, Throwable e) {
        // TODO 根据业务进行判断
    }

    /**
     * 判断是否重复消费
     */
    public boolean checkMessageKey(MessageProperties messageProperties,RabbitMqModel rabbitMqModel) {
        // TODO 根据业务进行判断
        return false;
    }

    /**
     * 删除判断重复消费Key
     */
    public void deleteCheckMessageKey(MessageProperties messageProperties) {

    }

    /**
     * 保存消费成功消息
     */
    public abstract void saveLog(R result, Message message, RabbitMqModel rabbitMqModel);

}
