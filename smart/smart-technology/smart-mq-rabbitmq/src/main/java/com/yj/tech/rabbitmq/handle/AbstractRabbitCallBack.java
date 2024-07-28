package com.yj.tech.rabbitmq.handle;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 *  mq消息处理器抽象类
 *  具体处理方式：由使用者继承该类并提供具体实现
 */
public abstract class AbstractRabbitCallBack {

    /**
     * 保存mq消费成功或失败后方法
     */
    public abstract void saveRabbitCallBack(CorrelationData correlationData, boolean ack, String cause);

    /**
     * 保存mq消息丢失方法
     */
    public abstract void saveReturnedMessage(ReturnedMessage returned);
}
