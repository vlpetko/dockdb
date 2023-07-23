package ru.vlpetko.dockdb.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitMQProperties {

    private String host;
    private int port;
    private String password;
    private String username;
    private Map<String, RabbitQueue> queues;

    @Data
    public static class RabbitQueue {
        private String exchange;
        private String queue;
        private String routingkey;
    }
}
