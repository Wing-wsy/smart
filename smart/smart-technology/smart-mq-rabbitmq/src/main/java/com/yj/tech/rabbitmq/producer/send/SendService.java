package com.yj.tech.rabbitmq.producer.send;

import com.yj.tech.rabbitmq.entity.BaseMq;

public interface SendService<T extends BaseMq> {

  /**
   * 发送消息
   */
  void send(T data) throws Exception;

  /**
   * 发送延缓信息
   */
  void sendDelay(T data, long delay) throws Exception;

  /**
   * 发送消息,并设置消息过期时间
   */
  void sendExpiration(T data, long expiration) throws Exception;
}
