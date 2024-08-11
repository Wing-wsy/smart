package com.yj.tech.admin.controller.test.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author wing
 * @create 2024/8/10
 */
@Component
public class KafkaMessageConsumer {
    @KafkaListener(topics = "admin-messages")
    public void receiveAdminMessage(String message) {

        System.out.println("kafka消费者-Received message: " + message);
        // ...
    }
}
