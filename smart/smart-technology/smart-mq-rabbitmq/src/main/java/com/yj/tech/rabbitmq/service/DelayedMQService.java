package com.yj.tech.rabbitmq.service;

import com.yj.tech.rabbitmq.annotation.RabbitMq;
import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMq延迟队列注册
 */
@Service("delayedMQService")
public class DelayedMQService extends AbstractMQService {

    @Override
    protected AbstractExchange initExchange(String exchangeName, RabbitMq rabbitMq) {
        Map<String, Object> props = new HashMap<>(1);
        //延迟交换器类型
        props.put("x-delayed-type", rabbitMq.exchangeTypes());
        return new CustomExchange(exchangeName, "x-delayed-message", true, false, props);
    }

    @Override
    protected Binding initBinding(Queue queue, AbstractExchange exchange, String routingKey, RabbitMq rabbitMq) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }
}
