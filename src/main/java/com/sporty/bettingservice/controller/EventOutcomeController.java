package com.sporty.bettingservice.controller;


import com.sporty.bettingservice.config.KafkaTopics;
import com.sporty.bettingservice.dto.ApiResponse;
import com.sporty.bettingservice.dto.EventOutcome;
import com.sporty.bettingservice.service.KafkaProducerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/outcomes")
@AllArgsConstructor
public class EventOutcomeController {

    private final KafkaProducerService publisher;
    private final KafkaTopics kafkaTopics;

    @PostMapping
    public ResponseEntity<ApiResponse> publishOutcome(@RequestBody EventOutcome outcome) {
        publisher.send(kafkaTopics.getEventOutcomesTopic(), outcome.eventId(), outcome);

        //Better is to define static responses and reuse them over the APIs.
        //However, due to the time limit, I created it here.
        ApiResponse response = new ApiResponse(
                "Outcome published successfully",
                Map.of("eventId", outcome.eventId())
        );

        return ResponseEntity.accepted().body(response);
    }
}
