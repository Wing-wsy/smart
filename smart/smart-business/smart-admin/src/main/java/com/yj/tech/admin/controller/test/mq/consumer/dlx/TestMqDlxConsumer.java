package com.yj.tech.admin.controller.test.mq.consumer.dlx;

import com.yj.tech.rabbitmq.annotation.RabbitMq;
import com.yj.tech.rabbitmq.annotation.RabbitMqRetry;
import com.yj.tech.rabbitmq.consumer.AbstractConsumer;
import com.yj.tech.rabbitmq.entity.RabbitMqModel;
import org.springframework.amqp.core.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  直连模式: 一个生产者对应一个消费者
 */
//@RabbitMq(routingKey = "testQueueTTL1_routing_key", queues = "testQueueTTL1",exchange = "testExchangeTTL1", dlxClazz = TestDlxMqDirect.class)
//// 设置重试次数
//@RabbitMqRetry(retryNumber = 5, initialInterval = 1000, multiplier = 2)
public class TestMqDlxConsumer extends AbstractConsumer<String, String> {

    @Override
    public String handleMessage(String body) throws Exception {
        System.out.println("handleMessage......" + body + ":date=" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
        /*try {
            // 处理消息的逻辑
            int i = 1/0;
        } catch (Exception e) {
            // 重试逻辑
            throw new AmqpRejectAndDontRequeueException("重试逻辑触发");
        }*/
        return null;
    }

    @Override
    public void saveLog(String result, Message message, RabbitMqModel rabbitMqModel) {
        // TODO 消费数据成功后，记录日志操作
        System.out.println("saveLog......");
    }
}
