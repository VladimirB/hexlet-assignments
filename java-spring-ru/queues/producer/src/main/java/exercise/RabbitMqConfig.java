package exercise;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String EXCHANGE_ID = "exchange";
    public static final String ROUTING_KEY = "key";
    private static final String QUEUE_ID = "queue";

    @Bean
    Queue queue() {
        return new Queue(QUEUE_ID, false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_ID);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue)
                .to(topicExchange)
                .with(ROUTING_KEY);
    }
}
