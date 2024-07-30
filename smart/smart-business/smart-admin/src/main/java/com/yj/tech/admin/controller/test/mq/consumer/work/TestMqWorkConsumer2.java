package com.yj.tech.admin.controller.test.mq.consumer.work;

import com.yj.tech.rabbitmq.annotation.RabbitMq;
import com.yj.tech.rabbitmq.consumer.AbstractConsumer;
import com.yj.tech.rabbitmq.entity.RabbitMqModel;
import org.springframework.amqp.core.Message;

/**
 * @author wing
 * @create 2024/7/29
 */
@RabbitMq(queues = "testQueueWork1",exchange = "testExchangeWork1")
public class TestMqWorkConsumer2 extends AbstractConsumer<String, String> {

    @Override
    public String handleMessage(String body) throws Exception {
        System.out.println("TestMqWorkConsumer2 handleMessage......" + body);
        return null;
    }

    @Override
    public void saveLog(String result, Message message, RabbitMqModel rabbitMqModel) {
        // TODO 消费数据成功后，记录日志操作
        System.out.println("TestMqWorkConsumer2 saveLog......");
    }
}
