package com.yj.tech.admin.controller.test.mq.consumer.routing.topic;

import com.yj.tech.rabbitmq.annotation.RabbitMq;
import com.yj.tech.rabbitmq.consumer.AbstractConsumer;
import com.yj.tech.rabbitmq.entity.RabbitMqModel;
import org.springframework.amqp.core.Message;
import static org.springframework.amqp.core.ExchangeTypes.TOPIC;

/**
 *  topic模式: 订阅模式
 */
@RabbitMq(exchangeTypes = TOPIC, exchange = "testExchangeTopic1", queues = "testQueueTopic1", routingKey = "user.#")
public class TestMqTopicConsumer1 extends AbstractConsumer<String, String> {

    @Override
    public String handleMessage(String body) throws Exception {
        System.out.println("TestMqTopicConsumer1 handleMessage [user.#]......" + body);
        return null;
    }

    @Override
    public void saveLog(String result, Message message, RabbitMqModel rabbitMqModel) {
        // TODO 消费数据成功后，记录日志操作
        System.out.println("TestMqTopicConsumer1 saveLog [user.#]......");
    }
}
