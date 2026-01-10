package com.kirito.payment_service.service.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;

@Component
@Slf4j
public class MockPaymentProvider implements PaymentProvider {

    @Value("${payment-service.provider.delay}")
    private long simulationDelay;

    @Value("${payment-service.provider.failure-rate}")
    private int failureRate;

    @Override
    public boolean processPayment(String cardNumber, BigDecimal amount) {
        try {
            log.info("Connecting to external bank... {}", cardNumber);
            Thread.sleep(simulationDelay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }

        if (new Random().nextInt(10) < failureRate) {
            log.warn("Payment rejected by bank for amount {}", amount);
            return false;
        }
        log.info("Payment processed successfully");
        return true;
    }
}
