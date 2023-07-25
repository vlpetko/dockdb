package ru.vlpetko.dockdb.config;

import lombok.RequiredArgsConstructor;
import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import ru.vlpetko.dockdb.config.props.RabbitMQProperties;

@Configuration
@IntegrationComponentScan
@EnableIntegration
@RequiredArgsConstructor
public class RabbitConsumerConfig {

    public static final String QUEUE_NAME = "controllerRequest";

    private final MessageConverter jsonMessageConverter;
    private final RabbitMQProperties rabbitMQProperties;
    private final ConnectionFactory connectionFactory;

    @Bean
    Queue responseQueue() {
        return new Queue(rabbitMQProperties.getQueues().get(QUEUE_NAME).getQueue(), true);
    }

    @Bean
    Exchange responseExchange() {
        return ExchangeBuilder.directExchange(rabbitMQProperties.getQueues().get(QUEUE_NAME).getExchange())
                .durable(true)
                .build();
    }

    @Bean
    Binding gibddResponseBinding() {
        return BindingBuilder
                .bind(responseQueue())
                .to(responseExchange())
                .with(rabbitMQProperties.getQueues().get(QUEUE_NAME).getRoutingkey())
                .noargs();
    }

    @Bean
    public RabbitTemplate responseTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public SimpleMessageListenerContainer responseListenerContainer() {
        SimpleMessageListenerContainer container =
                new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(responseQueue());
        container.setConcurrentConsumers(2);
        container.setDefaultRequeueRejected(false);
        container.setAdviceChain(new Advice[]{responseInterceptor()});
        return container;
    }

    @Bean
    RetryOperationsInterceptor responseInterceptor() {
        return RetryInterceptorBuilder.stateless()
                .maxAttempts(1)
                .backOffOptions(1000, 1, 60000)
                .recoverer(new RejectAndDontRequeueRecoverer())
                .build();
    }
}
