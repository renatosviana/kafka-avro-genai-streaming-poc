package com.viana.poc.service;

import com.viana.avro.AccountEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AccountEventConsumer {

    private final Map<String, Double> balances = new ConcurrentHashMap<>();

    @KafkaListener(topics = "account-events")
    public void handle(AccountEvent event) {
        String accountId = event.getAccountId();

        balances.putIfAbsent(accountId, 0.0);
        double current = balances.get(accountId);

        double updated = switch (event.getEventType()) {
            case CREDIT -> current + event.getAmount();
            case DEBIT -> current - event.getAmount();
        };

        balances.put(accountId, updated);

        System.out.printf(
          "Event %s for account %s → new balance = %.2f%n",
          event.getEventType(), accountId, updated
        );
    }
}
