package com.yj.tech.rabbitmq.service;

import com.google.common.base.Splitter;
import com.yj.tech.common.util.verification.ValidateUtils;
import com.yj.tech.rabbitmq.annotation.RabbitMq;
import com.yj.tech.rabbitmq.entity.InitRabbitBinding;
import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

import java.util.List;

public abstract class AbstractMQService {

    public static String SERVICE_NAME = "MQService";

    public Queue initBinding(RabbitMq rabbitMq, String queueName, RabbitAdmin admin, boolean isInitDlxMap, boolean isAddDlxPrefix) {
        InitRabbitBinding initRabbitBinding = new InitRabbitBinding(rabbitMq, queueName, isInitDlxMap, isAddDlxPrefix);
        // 初始化队列
        Queue queue = new Queue(initRabbitBinding.getQueue(), rabbitMq.isPersistence(), false, false, initRabbitBinding.getArgs());
        // 绑定队列
        admin.declareQueue(queue);
        // 绑定交换机
        AbstractExchange exchange = initExchange(initRabbitBinding.getExchange(), rabbitMq);
        admin.declareExchange(exchange);
        // 绑定（兼容direct使用路由模式，多个路由使用 , 隔开，如：“info,error”）
        String routingKey = initRabbitBinding.getRoutingKey();
        if (ValidateUtils.isNotEmpty(routingKey)) {
            Splitter splitter = Splitter.on(",");
            // 使用工具类按指定字符拆分
            List<String> strings = splitter.splitToList(routingKey);
            for (String routkey : strings) {
                admin.declareBinding(this.initBinding(queue, exchange, routkey, rabbitMq));
            }
        } else {
            admin.declareBinding(this.initBinding(queue, exchange, routingKey, rabbitMq));
        }
        return queue;
    }


    /**
     * 初始化交换机
     */
    protected abstract AbstractExchange initExchange(String exchange, RabbitMq rabbitMq);

    /**
     * 初始化绑定
     */
    protected abstract Binding initBinding(Queue queue, AbstractExchange exchange, String routingKey, RabbitMq rabbitMq);
}
