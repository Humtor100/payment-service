package com.kirito.payment_service.service.provider;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;

@Component
public class MockPaymentProvider implements PaymentProvider{

    @Override
    public boolean processPayment(String cardNumber, BigDecimal amount) {
        try {
            System.out.println("Connecting to external bank...");
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }

        if (new Random().nextInt(10) < 2) {
            System.out.println("Payment rejected by bank!");
            return false;
        }
        System.out.println("Payment processed successfully.");
        return true;
    }
}
