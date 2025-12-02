package com.viana.poc.controller;

import com.viana.poc.service.AccountEventProducer;
import com.viana.avro.AccountEvent;
import com.viana.avro.EventType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountEventProducer producer;

    public AccountController(AccountEventProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/{accountId}/credit")
    public ResponseEntity<String> credit(@PathVariable String accountId,
                                         @RequestParam double amount) {
        try {
            AccountEvent event = AccountEvent.newBuilder()
                    .setEventId(UUID.randomUUID().toString())
                    .setAccountId(accountId)
                    .setAmount(amount)
                    .setEventType(EventType.CREDIT)
                    .setEventTime(System.currentTimeMillis())
                    .build();

            producer.sendAccountEvent(event);

            return ResponseEntity.ok("Credit - Event sent");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending event: " + ex.getMessage());
        }
    }


    @PostMapping("/{accountId}/debit")
    public ResponseEntity<String> debit(@PathVariable String accountId,
                      @RequestParam double amount) {

        try {
            AccountEvent event = AccountEvent.newBuilder()
                    .setEventId(UUID.randomUUID().toString())
                    .setAccountId(accountId)
                    .setAmount(amount)
                    .setEventType(EventType.DEBIT)
                    .setEventTime(System.currentTimeMillis())
                    .build();

            producer.sendAccountEvent(event);

            return ResponseEntity.ok("Debit - Event sent");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending event: " + ex.getMessage());
        }
    }
}
