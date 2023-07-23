package ru.vlpetko.dockdb.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import ru.vlpetko.dockdb.config.props.RabbitMQProperties;

@Configuration
@RequiredArgsConstructor
@IntegrationComponentScan
@EnableIntegration
public class RabbitConfig {

    public static final String QUEUE_NAME = "controllerRequest";

    private final RabbitMQProperties rabbitMQProperties;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername(rabbitMQProperties.getUsername());
        cachingConnectionFactory.setPassword(rabbitMQProperties.getPassword());
        cachingConnectionFactory.setVirtualHost(rabbitMQProperties.getHost());
        return cachingConnectionFactory;
    }
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue myQueue() {
        return new Queue(rabbitMQProperties.getQueues().get(QUEUE_NAME).getQueue(), true);
    }

    @Bean
    DirectExchange myExchange() {
        return ExchangeBuilder.directExchange(rabbitMQProperties.getQueues().get(QUEUE_NAME).getExchange())
                .durable(true)
                .build();
    }

    @Bean
    Binding myBinding() {
        return BindingBuilder
                .bind(myQueue())
                .to(myExchange())
                .with(rabbitMQProperties.getQueues().get(QUEUE_NAME).getRoutingkey());
    }
}
