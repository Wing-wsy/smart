package com.yj.tech.admin.controller.test.mq.consumer.dlx;

import com.yj.tech.rabbitmq.annotation.RabbitMq;
import com.yj.tech.rabbitmq.consumer.AbstractConsumer;
import com.yj.tech.rabbitmq.entity.RabbitMqModel;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wing
 * @create 2024/7/30
 */
@Component
@RabbitMq(routingKey = "dlx_testQueueTTL1_routing_key", queues = "dlx_testQueueTTL1",exchange = "dlx_testExchangeTTL1")
public class TestDlxMqDirect extends AbstractConsumer<String, String> {
    @Override
    public String handleMessage(String body) throws Exception {
        System.out.println("死信队列 handleMessage......" + body + ":date=" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
        return null;
    }

    @Override
    public void saveLog(String result, Message message, RabbitMqModel rabbitMqModel) {
        System.out.println("死信队列 saveLog......" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));

    }
}
