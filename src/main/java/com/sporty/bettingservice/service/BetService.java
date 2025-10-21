package com.sporty.bettingservice.service;

import com.sporty.bettingservice.model.Bet;
import com.sporty.bettingservice.repository.BetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class BetService {
    private final BetRepository betRepository;

    public Set<Bet> findByEventIdAndSettledFalse(String eventId) {
        return betRepository.findByEventIdAndSettledFalse(eventId).orElse(Set.of());
    }

    public void settleBets(String betId) {
        betRepository.findById(betId).ifPresent(
                bet -> {
                    bet.setSettled(true);
                    betRepository.save(bet);
                });
    }
}
