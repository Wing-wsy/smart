package com.yj.tech.rabbitmq.handle;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 *  mq消息处理器抽象类
 *  具体处理方式：由使用者继承该类并提供具体实现
 */
public abstract class AbstractRabbitCallBack {

    /**
     * 发送消息到mq，无论是否成功发送到达mq队列都执行该方法
     * 只要发送到了交换机，交换机收到了 ack 都为 true（发送到了交换机，交换机按照路由找不到队列，ack 也是为true）；
     */
    public abstract void saveRabbitCallBack(CorrelationData correlationData, boolean ack, String cause);

    /**
     * 保存mq消息丢失方法
     */
    public abstract void saveReturnedMessage(ReturnedMessage returned);
}
