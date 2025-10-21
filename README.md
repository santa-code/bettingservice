# Sports Betting Settlement Trigger Service

This is a simple backend service that simulates sports betting event outcome handling and bet settlement using **Kafka** and **RocketMQ**. The service starts with some test data added at runtime inside the `BettingServiceApplication` class, which can be extended as needed.

---

## Features

1. **Publish Event Outcomes to Kafka**
    - API endpoint to publish a sports event outcome.
    - Event outcome fields:
        - `eventId`
        - `eventName`
        - `eventWinnerId`
    - Published to Kafka topic: `event-outcomes`.

2. **Kafka Consumer**
    - Listens to the `event-outcomes` topic.
    - Matches event outcomes to bets in the database for settlement.

3. **Bet Settlement Logic**
    - Database contains bets with:
        - `betId`
        - `userId`
        - `eventId`
        - `eventMarketId`
        - `eventWinnerId`
        - `betAmount`
    - Checks which bets can be settled based on incoming event outcomes.

4. **RocketMQ Producer**
    - Sends messages to `bet-settlements` topic after identifying bets to settle.

5. **RocketMQ Consumer**
    - Listens to `bet-settlements` and processes bet settlement.

---

## Prerequisites
1. **Java 21**

2. **Install RocketMQ**
    - Follow the quick start guide: [RocketMQ Quickstart with Docker](https://rocketmq.apache.org/docs/quickStart/02quickstartWithDocker)
    - **Important:** Configure broker's IP address before starting the broker:
      ```bash
      echo "brokerIP1=127.0.0.1" > broker.conf
      ```
    - Start the RocketMQ broker after configuration.

3. **Install Kafka**
    - Follow the quick start guide: [Kafka Quickstart](https://kafka.apache.org/quickstart)
    - By default, Kafka bootstrap server runs on port `9092`.

4. **Configure Application**
    - Update `application.yaml` with your local ports for kafka & RocketMQ
---

## Running the Service

## Build and run the service (using Maven):
 `mvn spring-boot:run`

## Testing the API
To publish a sports event outcome:
   ```
   curl --location 'http://localhost:8989/api/v1/outcomes' \
   --header 'Content-Type: application/json' \
   --data '{
       "eventId":"football-match-2",
       "eventWinnerId":"chelsea",
       "eventName":"test"
   }
   ```

Publish the outcome to Kafka (event-outcomes topic).

Kafka consumer will match the outcome to bets.

RocketMQ producer sends settlement messages to bet-settlements.

RocketMQ consumer processes the settlement.

## Extending the Data 

The service starts with sample bets and outcomes for testing. You can extend these in `BettingServiceApplication` by adding more events, outcomes, and bets.
