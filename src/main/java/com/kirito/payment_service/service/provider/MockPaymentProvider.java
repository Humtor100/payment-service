package com.kirito.payment_service.service.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;

@Component
@Slf4j
public class MockPaymentProvider implements PaymentProvider{

    @Override
    public boolean processPayment(String cardNumber, BigDecimal amount) {
        try {
            log.info("Connecting to external bank... {}", cardNumber);
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }

        if (new Random().nextInt(10) < 2) {
            log.warn("Payment rejected by bank for amount {}", amount);
            return false;
        }
        log.info("Payment processed successfully");
        return true;
    }
}
