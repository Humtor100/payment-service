package com.kirito.payment_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MakePaymentRequestDTO {
    private Long sourceAccountId;
    private Long targetAccountId;
    private BigDecimal amount;
}
