package com.yj.tech.admin.controller.test.mq.consumer.fanout;

import com.yj.tech.rabbitmq.annotation.RabbitMq;
import com.yj.tech.rabbitmq.consumer.AbstractConsumer;
import com.yj.tech.rabbitmq.entity.RabbitMqModel;
import org.springframework.amqp.core.Message;

import static org.springframework.amqp.core.ExchangeTypes.FANOUT;

/**
 *  fanout模式: 广播模式（消费者都能拿到消息。实现一条消息被多个消费者消费）
 *  使用fanout类型交换器时，不需要设置任何路由键，因为这种类型的交换器会将接收到的消息广播到所有绑定到它的队列。
 *  如果需要设置路由键，通常是在direct或者topic类型的交换器下进行操作。
 *
 *  注意：交换机名称都要一样 testExchangeFanout1，队列名称不一样： testQueueFanout2
 */
//@RabbitMq(exchangeTypes = FANOUT, exchange = "testExchangeFanout1", queues = "testQueueFanout2", consumersPerQueue = 2)
public class TestMqFanoutConsumer2 extends AbstractConsumer<String, String> {

    @Override
    public String handleMessage(String body) throws Exception {
        System.out.println("TestMqFanoutConsumer2 handleMessage......" + body);
        return null;
    }

    @Override
    public void saveLog(String result, Message message, RabbitMqModel rabbitMqModel) {
        // TODO 消费数据成功后，记录日志操作
        System.out.println("TestMqFanoutConsumer2 saveLog......");
    }
}
