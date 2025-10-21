package com.sporty.bettingservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sporty.bettingservice.config.RocketMQTopics;
import com.sporty.bettingservice.dto.EventOutcome;
import com.sporty.bettingservice.dto.SportMessage;
import com.sporty.bettingservice.model.Bet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.mockito.Mockito.*;

class EventOutcomeKafkaListenerTest {

    @Mock
    BetService betService;

    @Mock
    RocketMQProducerService rocketProducer;

    @Mock
    RocketMQTopics rocketMQTopics;

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    EventOutcomeKafkaListener listener;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleMessage_sendsBetSettlementMessages() {
        // Mock event outcome
        EventOutcome outcome = new EventOutcome("football-match-1", "Match_Results", "winner");
        SportMessage<EventOutcome> payload = mock(SportMessage.class);
        when(payload.data()).thenReturn(outcome);
        when(objectMapper.convertValue(payload.data(), EventOutcome.class)).thenReturn(outcome);
        // Mock bets to settle
        Bet bet1 = new Bet("1", "user-2", "football-match-1", "winner", "fc-barcelona", 100.0, false);
        Bet bet2 = new Bet("2", "user-1", "football-match-1", "winner", "fc-barcelona", 50.0, false);
        when(betService.findByEventIdAndSettledFalse("football-match-1")).thenReturn(Set.of(bet1, bet2));

        // Mock topic
        when(rocketMQTopics.getBetSettlementsTopic()).thenReturn("bet-settlements");

        // Run the method
        listener.handleMessage(payload);

        // Verify messages sent twice
        verify(rocketProducer, times(2)).send(any(), any());

    }
}