package com.sporty.bettingservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class RocketMQProducerService {
    private final RocketMQTemplate rocketMQTemplate;

    public <T> void send(String topic, T message) {
        rocketMQTemplate.convertAndSend(topic, message);
    }
}