package ru.vlpetko.dockdb.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
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
    private final ConnectionFactory connectionFactory;
    private final MessageConverter jsonMessageConverter;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(rabbitMQProperties.getQueues().get(QUEUE_NAME).getExchange());
        rabbitTemplate.setRoutingKey(rabbitMQProperties.getQueues().get(QUEUE_NAME).getRoutingkey());
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Queue myQueue() {
        return new Queue(rabbitMQProperties.getQueues().get(QUEUE_NAME).getQueue(), true);
    }

}
