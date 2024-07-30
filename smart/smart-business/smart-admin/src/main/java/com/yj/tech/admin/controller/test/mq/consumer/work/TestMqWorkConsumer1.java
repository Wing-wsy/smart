package com.yj.tech.admin.controller.test.mq.consumer.work;

import com.yj.tech.rabbitmq.annotation.RabbitMq;
import com.yj.tech.rabbitmq.consumer.AbstractConsumer;
import com.yj.tech.rabbitmq.entity.RabbitMqModel;
import org.springframework.amqp.core.Message;

/**
 *  工作模式: 一个生产者对应多个消费者（多个消费者监听同一个队列，一起消费，全部消费者消费消息总数=生产者生产消息数量）
 */
@RabbitMq(queues = "testQueueWork1",exchange = "testExchangeWork1")
public class TestMqWorkConsumer1 extends AbstractConsumer<String, String> {

    @Override
    public String handleMessage(String body) throws Exception {
        System.out.println("TestMqWorkConsumer1 handleMessage......" + body);
        return null;
    }

    @Override
    public void saveLog(String result, Message message, RabbitMqModel rabbitMqModel) {
        // TODO 消费数据成功后，记录日志操作
        System.out.println("TestMqWorkConsumer1 saveLog......");
    }
}
