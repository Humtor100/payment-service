package com.kirito.payment_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositRequestDTO {
    private Long accountId;
    private BigDecimal amount;
    private String cardNumber;
}
