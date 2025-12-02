package com.viana.poc.service;

import com.viana.avro.AccountEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AccountEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAccountEvent(AccountEvent event) {
        // key = accountId → ordering per account
        String key = event.getAccountId();
        kafkaTemplate.send("account-events", key, event);
    }
}
