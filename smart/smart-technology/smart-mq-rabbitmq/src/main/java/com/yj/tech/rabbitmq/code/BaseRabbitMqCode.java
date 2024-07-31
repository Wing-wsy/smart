package com.yj.tech.rabbitmq.code;

public interface BaseRabbitMqCode {

  String DLX_ROUTING_KEY = "x-dead-letter-routing-key";

  String DLX_EXCHANGE_KEY = "x-dead-letter-exchange";

  String DLX_TTL = "x-message-ttl";

  String DLX_PREFIX = "dlx_";

  String DLX_ROUTING_DEFAULT = "dlx_routingkey_default";

  String QUEUE_MODE = "x-queue-mode";
}
