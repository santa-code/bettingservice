package com.sporty.bettingservice.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sporty.bettingservice.config.RocketMQTopics;
import com.sporty.bettingservice.dto.BetSettlementMessage;
import com.sporty.bettingservice.dto.EventOutcome;
import com.sporty.bettingservice.dto.SportMessage;
import com.sporty.bettingservice.model.Bet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventOutcomeKafkaListener {

    private final BetService betService;
    private final RocketMQProducerService rocketProducer;
    private final RocketMQTopics rocketMQTopics;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${variables.kafka-topics.event-outcomes-topic}", groupId = "betting-service-group")
    public void listen(SportMessage<EventOutcome> payload) {

        handleMessage(payload);
    }

    public void handleMessage(SportMessage<EventOutcome> eventOutcomePayload) {
        try {
            EventOutcome outcome = objectMapper.convertValue(eventOutcomePayload.data(), EventOutcome.class);
            log.info("Consumed event outcome for eventId={}, eventName={}, winner={}", outcome.eventId(), outcome.eventName(), outcome.eventId());

            Set<Bet> betsToSettle = betService.findByEventIdAndSettledFalse(outcome.eventId());
            log.info("Found {} bets to settle for event {}", betsToSettle.size(), outcome.eventId());

            for (Bet bet : betsToSettle) {
                boolean win = bet.getEventWinnerId().equals(outcome.eventWinnerId());
                BetSettlementMessage msg = BetSettlementMessage.builder()
                        .betId(bet.getBetId())
                        .userId(bet.getUserId())
                        .eventId(bet.getEventId())
                        .eventWinnerId(bet.getEventWinnerId())
                        .amount(bet.getAmount())
                        .win(win)
                        .build();
                rocketProducer.send(rocketMQTopics.getBetSettlementsTopic(), msg);
            }
        } catch (Exception e) {
            log.error("Failed to process outcome: {}", e.getMessage(), e);
        }
    }
}
