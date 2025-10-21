package com.sporty.bettingservice.dto;

import lombok.Builder;

@Builder
public record BetSettlementMessage(String betId, String userId, String eventId, Object eventWinnerId, double amount,
                                   boolean win) {
}
