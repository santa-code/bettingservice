package com.sporty.bettingservice.service;

import com.sporty.bettingservice.model.Bet;
import com.sporty.bettingservice.repository.BetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BetServiceTest {

    @Mock
    private BetRepository betRepository;

    @InjectMocks
    private BetService betService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSettleBets_betExists() {
        Bet bet = new Bet();
        bet.setBetId("1");
        bet.setSettled(false);

        when(betRepository.findById("1")).thenReturn(Optional.of(bet));

        betService.settleBets("1");


        ArgumentCaptor<Bet> betCaptor = ArgumentCaptor.forClass(Bet.class);
        verify(betRepository).save(betCaptor.capture());

        Bet savedBet = betCaptor.getValue();
        assertTrue(savedBet.isSettled(), "Bet should be settled");
    }

    @Test
    void testSettleBets_betDoesNotExist() {
        // Arrange
        when(betRepository.findById("999")).thenReturn(Optional.empty());

        // Act
        betService.settleBets("999");

        // Assert
        verify(betRepository, never()).save(any(Bet.class));
    }
}