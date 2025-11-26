package org.example.config;

import org.example.events.UserDeleteEvent;
import org.example.events.UserRegisteredEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public KafkaTemplate<String, UserRegisteredEvent> userRegisteredTemplate(
            ProducerFactory<String, UserRegisteredEvent> pf) {
        return new KafkaTemplate<>(pf);
    }

    @Bean
    public KafkaTemplate<String, UserDeleteEvent> userDeleteTemplate(ProducerFactory<String, UserDeleteEvent> pf) {
        return new KafkaTemplate<>(pf);
    }
}
