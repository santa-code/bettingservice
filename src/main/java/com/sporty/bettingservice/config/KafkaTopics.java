package com.sporty.bettingservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("variables.kafka-topics")
public class KafkaTopics {

    private String eventOutcomesTopic;
}
