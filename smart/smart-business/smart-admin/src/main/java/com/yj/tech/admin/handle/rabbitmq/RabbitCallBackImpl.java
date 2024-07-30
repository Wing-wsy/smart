package com.yj.tech.admin.handle.rabbitmq;


import com.yj.tech.rabbitmq.handle.AbstractRabbitCallBack;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.stereotype.Component;

/**
 * @author wing
 * @create 2024/7/29
 */
@Component
public class RabbitCallBackImpl extends AbstractRabbitCallBack {
    @Override
    public void saveRabbitCallBack(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("saveRabbitCallBack...");
    }

    @Override
    public void saveReturnedMessage(ReturnedMessage returned) {
        System.out.println("saveReturnedMessage...");
    }
}
