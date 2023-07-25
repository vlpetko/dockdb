package ru.vlpetko.dockdb.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vlpetko.dockdb.config.props.RabbitMQProperties;

@Configuration
@RequiredArgsConstructor
public class RabbitQueueConfig {

    private final RabbitMQProperties rabbitMQProperties;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(rabbitMQProperties.getHost());
        cachingConnectionFactory.setUsername(rabbitMQProperties.getUsername());
        cachingConnectionFactory.setPassword(rabbitMQProperties.getPassword());
        return cachingConnectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        var admin = new RabbitAdmin((connectionFactory));
        //   admin.setAutoStartup(true);
        return admin;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
