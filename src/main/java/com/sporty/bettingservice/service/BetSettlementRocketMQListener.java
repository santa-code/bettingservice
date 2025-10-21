package com.sporty.bettingservice.service;

import com.sporty.bettingservice.dto.BetSettlementMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;


@Service
@RocketMQMessageListener(
        topic = "${variables.rocketmq-topics.bet-settlements-topic}",
        consumerGroup = "${rocketmq.consumer.group}"
)
@Slf4j
@RequiredArgsConstructor
public class BetSettlementRocketMQListener implements RocketMQListener<BetSettlementMessage> {
    private final BetService betService;

    public void onMessage(BetSettlementMessage betSettlementMessage) {
        log.info("Event bet settlement received: {}", betSettlementMessage);
        betService.settleBets(betSettlementMessage.betId());
    }
}