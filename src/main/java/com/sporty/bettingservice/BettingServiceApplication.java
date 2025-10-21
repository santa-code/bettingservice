package com.sporty.bettingservice;

import com.sporty.bettingservice.model.Bet;
import com.sporty.bettingservice.repository.BetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BettingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BettingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner seed(BetRepository repo) {
        return args -> {
            // test data
            repo.save(new Bet("1", "user-1", "football-match-1", "winner", "fc-barcelona", 100.0, false));
            repo.save(new Bet("2", "user-2", "football-match-1", "goalscorer", "fc-barcelona", 50.0, false));
            repo.save(new Bet("3", "user-3", "football-match-2", "goalscorer", "chelsea", 75.0, false));
            repo.save(new Bet("4", "user-2", "football-match-3", "winner", "real-madrid", 100.0, false));

        };
    }


}
