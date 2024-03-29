package exercise;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String QUEUE_ID = "queue";

    @Bean
    Queue queue() {
        return new Queue(QUEUE_ID, false);
    }
}
