package com.sporty.bettingservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("variables.rocketmq-topics")
public class RocketMQTopics {

    private String betSettlementsTopic;
}
