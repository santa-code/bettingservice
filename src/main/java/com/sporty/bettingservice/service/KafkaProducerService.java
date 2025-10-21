package com.sporty.bettingservice.service;

import com.sporty.bettingservice.dto.SportMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, SportMessage<Object>> kafkaTemplate;

    public <T> void send(String topic, String key, T message) {
        SportMessage<Object> sportMessage = new SportMessage<>(message);
        kafkaTemplate.send(topic, key, sportMessage);
    }
}

