package org.example.service;

import org.example.events.UserDeleteEvent;
import org.example.events.UserRegisteredEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducerService {
    private final KafkaTemplate<String, UserRegisteredEvent> userRegisteredTemplate;
    private final KafkaTemplate<String, UserDeleteEvent> userDeleteTemplate;
    private static final String USER_REGISTERED_TOPIC = "user_registered";
    private static final String USER_DELETE_TOPIC = "user_delete";

    public UserEventProducerService(
            KafkaTemplate<String, UserRegisteredEvent> userRegisteredTemplate,
            KafkaTemplate<String, UserDeleteEvent> userDeleteTemplate) {
        this.userRegisteredTemplate = userRegisteredTemplate;
        this.userDeleteTemplate = userDeleteTemplate;
    }

    public void sendUserRegisteredEvent(UserRegisteredEvent event) {
        userRegisteredTemplate.send(USER_REGISTERED_TOPIC, event.userId().toString(), event);
    }

    public void sendUserDeleteEvent(UserDeleteEvent event) {
        userDeleteTemplate.send(USER_DELETE_TOPIC, event.userId().toString(), event);
    }
}
