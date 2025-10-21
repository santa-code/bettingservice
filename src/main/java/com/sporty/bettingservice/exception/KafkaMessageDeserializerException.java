package com.sporty.bettingservice.exception;

public class KafkaMessageDeserializerException extends RuntimeException {


    public KafkaMessageDeserializerException(String failedToDeserializeSportMessage, Exception e) {
        super(failedToDeserializeSportMessage, e);
    }
}
