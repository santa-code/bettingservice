package com.sporty.bettingservice.repository;

import com.sporty.bettingservice.model.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


@Repository
public interface BetRepository extends JpaRepository<Bet, String> {
    @Query("SELECT b FROM Bet b WHERE b.eventId = :eventId AND b.settled = false")
    Optional<Set<Bet>> findByEventIdAndSettledFalse(String eventId);
}
