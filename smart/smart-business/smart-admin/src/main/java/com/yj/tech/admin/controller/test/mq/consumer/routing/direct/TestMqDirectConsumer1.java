package com.yj.tech.admin.controller.test.mq.consumer.routing.direct;

import com.yj.tech.rabbitmq.annotation.RabbitMq;
import com.yj.tech.rabbitmq.consumer.AbstractConsumer;
import com.yj.tech.rabbitmq.entity.RabbitMqModel;
import org.springframework.amqp.core.Message;

import static org.springframework.amqp.core.ExchangeTypes.DIRECT;
import static org.springframework.amqp.core.ExchangeTypes.TOPIC;

/**
 *  direct模式: 直连模式
 */
@RabbitMq(exchangeTypes = DIRECT, exchange = "testExchangeDirect1", queues = "testQueueDirect1", routingKey = "info,error")
public class TestMqDirectConsumer1 extends AbstractConsumer<String, String> {

    @Override
    public String handleMessage(String body) throws Exception {
        System.out.println("TestMqDirectConsumer1 handleMessage [info,error]......" + body);
        return null;
    }

    @Override
    public void saveLog(String result, Message message, RabbitMqModel rabbitMqModel) {
        // TODO 消费数据成功后，记录日志操作
        System.out.println("TestMqDirectConsumer1 saveLog [info,error]......");
    }
}
