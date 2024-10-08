package com.yj.tech.rabbitmq.config;

import com.yj.tech.utils.logger.LogUtil;
import com.yj.tech.utils.spring.SpringUtil;
import com.yj.tech.utils.verification.ValidateUtils;
import com.yj.tech.rabbitmq.handle.AbstractRabbitCallBack;
import com.yj.tech.rabbitmq.handle.RabbitCallBack;
import com.yj.tech.rabbitmq.service.*;
import com.yj.tech.rabbitmq.util.RabbitUtils;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Map;

@Configuration
@EnableConfigurationProperties(value = {RabbitProperties.class})
@Import(value = {RabbitUtils.class, DelayedMQService.class, DirectMQService.class, FanoutMQService.class, TopicMQService.class, HeadersMQService.class})
public class RabbitConfig {

    public RabbitConfig(ApplicationContext applicationContext) {
        SpringUtil.setContext(applicationContext);
    }

    /**
     * 接受数据自动的转换为Json
     */
    @Bean("messageConverter")
    @ConditionalOnMissingBean(MessageConverter.class)
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean("rabbitTemplate")
    @ConditionalOnMissingBean(RabbitTemplate.class)
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter,
                                         RabbitProperties properties) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setMandatory(ValidateUtils.getOrDefault(properties.getTemplate().getMandatory(), true));
        if (ValidateUtils.isNotEmpty(properties.getTemplate().getReceiveTimeout())) {
            rabbitTemplate.setReceiveTimeout(properties.getTemplate().getReceiveTimeout().toMillis());
        }
        if (ValidateUtils.isNotEmpty(properties.getTemplate().getReplyTimeout())) {
            rabbitTemplate.setReplyTimeout(properties.getTemplate().getReplyTimeout().toMillis());
        }
        // 获取 AbstractRabbitCallBack 类型的 bean
        Map<String, AbstractRabbitCallBack> callBackMap = SpringUtil.getBeansOfType(AbstractRabbitCallBack.class);
        if (ValidateUtils.isNotEmpty(callBackMap)) {
            RabbitCallBack rabbitCallBack = new RabbitCallBack(callBackMap.values());
            // 设置回调处理方法
            rabbitTemplate.setConfirmCallback(rabbitCallBack);
            rabbitTemplate.setReturnsCallback(rabbitCallBack);
        }
        return rabbitTemplate;
    }

    @Bean("rabbitAdmin")
    @ConditionalOnMissingBean(RabbitAdmin.class)
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        LogUtil.debug(getClass(), "RabbitAdmin启动了。。。");
        // 设置启动spring容器时自动加载这个类(这个参数现在默认已经是true，可以不用设置)
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

}
