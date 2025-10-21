package com.sporty.bettingservice.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sporty.bettingservice.dto.SportMessage;
import com.sporty.bettingservice.exception.KafkaMessageDeserializerException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

//used in the application yaml
public class KafkaMessageDeserializer implements Deserializer<SportMessage<Object>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // Nothing to configure
    }

    @Override
    public SportMessage<Object> deserialize(String topic, byte[] data) {
        try {
            if (data == null) return null;
            return objectMapper.readValue(data, objectMapper.getTypeFactory()
                    .constructParametricType(SportMessage.class, Object.class));
        } catch (Exception e) {
            throw new KafkaMessageDeserializerException("Failed to deserialize SportMessage", e);
        }
    }

    @Override
    public void close() {
        // Nothing to close
    }
}
