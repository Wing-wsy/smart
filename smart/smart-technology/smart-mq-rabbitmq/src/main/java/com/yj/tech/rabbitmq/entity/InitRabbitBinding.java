package com.yj.tech.rabbitmq.entity;

import com.yj.tech.utils.spring.SpringUtil;
import com.yj.tech.utils.verification.ValidateUtils;
import com.yj.tech.constant.code.BaseRabbitMqCode;
import com.yj.tech.rabbitmq.annotation.RabbitMq;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class InitRabbitBinding implements Serializable {
    /**
     * 队列名
     */
    String queue;
    /**
     * 交换器名
     */
    String exchange;
    /**
     * 路由键名
     */
    String routingKey;

    Map<String, Object> args;

    public InitRabbitBinding(RabbitMq rabbitMq, String queueName, boolean isInitDlxMap, boolean isAddDlxPrefix) {
        // 队列名
        this.queue = this.getName(queueName, isAddDlxPrefix);
        // 交换机名
        this.exchange = this.getName(rabbitMq.exchange(), isAddDlxPrefix);
        // 路由
        this.routingKey = this.getName(rabbitMq.routingKey(), isAddDlxPrefix);
        this.args = this.initArgs(rabbitMq, isInitDlxMap);
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }

    private String getName(String name, boolean isAddDlxPrefix) {
        if (ValidateUtils.isEmpty(name)) {
            return name;
        }
        name = SpringUtil.getElValue(name,ValidateUtils.getElDefaultValue(name));
        return isAddDlxPrefix ? BaseRabbitMqCode.DLX_PREFIX + name : name;
    }

    /**
     * 绑定死信队列参数
     *
     * @param rabbitMq     MQ注解
     * @param isInitDlxMap 是否初始化死信队列参数
     * @return 死信队列参数
     */
    private Map<String, Object> initArgs(RabbitMq rabbitMq, boolean isInitDlxMap) {
        boolean dlx = !void.class.equals(rabbitMq.dlxClazz()) || rabbitMq.delay() != 0L;

        if (!dlx || !isInitDlxMap) {
            return null;
        }
        Map<String, Object> args = new HashMap<>(3);
        args.put(BaseRabbitMqCode.DLX_EXCHANGE_KEY, BaseRabbitMqCode.DLX_PREFIX + rabbitMq.exchange());

        if (ValidateUtils.isNotEmpty(rabbitMq.routingKey())) {
            args.put(BaseRabbitMqCode.DLX_ROUTING_KEY, BaseRabbitMqCode.DLX_PREFIX + rabbitMq.routingKey());
        } else {
            args.put(BaseRabbitMqCode.DLX_ROUTING_KEY, BaseRabbitMqCode.DLX_ROUTING_DEFAULT);
        }
        /**
         * x-message-ttl 在创建队列时设置的消息TTL，表示消息在队列中最多能存活多久（ms）；
         * Expiration 发布消息时设置的消息TTL，消息自产生后的存活时间（ms）；
         * x-delay 由rabbitmq_delayed_message_exchange插件提供TTL，从交换机延迟投递到队列的时间（ms）；
         */
        if (rabbitMq.delay() != 0L && !rabbitMq.isDelayExchange()) {
            args.put(BaseRabbitMqCode.DLX_TTL, rabbitMq.delay());
        }
        if(rabbitMq.lazy()){
            args.put(BaseRabbitMqCode.QUEUE_MODE, "lazy");
        }
        return args;
    }
}
