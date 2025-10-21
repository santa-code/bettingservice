package com.sporty.bettingservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sporty.bettingservice.dto.SportMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

//used in the application yaml
@Slf4j
public class KafkaMessageSerializer implements Serializer<SportMessage<Object>> {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, SportMessage<Object> eventMessage) {
        try {
            return objectMapper.writeValueAsBytes(eventMessage);
        } catch (JsonProcessingException e) {
            log.error("Problem occurred during serializing EventMessage");
        }
        return new byte[0];
    }

}