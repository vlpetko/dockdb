package ru.vlpetko.dockdb.consumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class RabbitMQConsumer {

    @RabbitListener(queues = "ru.vlpetko.client")
    public void processMyQueue(String message) {
        System.out.printf("Received from myQueue : %s ", new String(message.getBytes()));
    }
}
