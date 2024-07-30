package com.yj.tech.admin.controller.test.mq.producer;

import com.yj.tech.common.util.verification.ValidateUtils;
import com.yj.tech.rabbitmq.entity.RabbitMqModel;
import com.yj.tech.rabbitmq.util.RabbitUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * mq消息发送类
 * 生产者发送的消息，只能发送到交换机，交换机来决定要发给哪个队列，生产者无法决定到哪个队列
 */
@Component
public class TestMqDirectProducer {

    @Autowired
    private RabbitUtils rabbitUtils;

    public void sendMsg(String exchange, String routingKey, Object msg) throws Exception {
        if (ValidateUtils.isEmpty(exchange)) {
            return;
        }
        RabbitMqModel rabbitMqModel = new RabbitMqModel<String>();
        rabbitMqModel.setExchange(exchange);
        rabbitMqModel.setRoutingKey(ValidateUtils.isEmpty(routingKey) ? "" : routingKey);
        rabbitMqModel.setBody(msg);
        rabbitUtils.send(rabbitMqModel);

    }
}
