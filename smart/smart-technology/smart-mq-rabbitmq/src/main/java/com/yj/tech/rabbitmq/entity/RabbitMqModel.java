package com.yj.tech.rabbitmq.entity;

import java.util.Map;

/**
 * 基础发送MQ基类
 */
public class RabbitMqModel<T> extends BaseMq<T> {

    private static final long serialVersionUID = -5799719724717056583L;
    /**
     * 交换机
     */
    private String exchange;
    /**
     * 路由规则
     */
    private String routingKey;
    /**
     * 设置头部属性
     */
    private Map<String, Object> headers;

    /**
     * 消息是否持久
     */
    private boolean messagePersistent = true;

    public boolean getMessagePersistent() {
        return messagePersistent;
    }

    public void setMessagePersistent(boolean messagePersistent) {
        this.messagePersistent = messagePersistent;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public RabbitMqModel() {
        super();
    }

    public RabbitMqModel(T body) {
        super(body);
    }

    public RabbitMqModel(String exchange, T body) {
        super(body);
        this.exchange = exchange;
    }

    public RabbitMqModel(String exchange, String routingKey, T body) {
        super(body);
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public RabbitMqModel(String exchange, String routingKey, T body, boolean messagePersistent) {
        super(body);
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.messagePersistent = messagePersistent;
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

}
