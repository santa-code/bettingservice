package com.sporty.bettingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "bets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bet {
    //for simplicity, I assumed all the ids are strings.
    @Id
    private String betId;
    private String userId;
    private String eventId;
    private String eventMarketId;
    private String eventWinnerId;
    private double amount;
    private boolean settled = false;
}