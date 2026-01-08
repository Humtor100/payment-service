package com.kirito.payment_service.service.provider;

import java.math.BigDecimal;

public interface PaymentProvider {
    boolean processPayment(String cardNumber, BigDecimal amount);
}