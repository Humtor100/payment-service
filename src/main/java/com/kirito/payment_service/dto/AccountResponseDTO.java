package com.kirito.payment_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponseDTO {
    private Long id;
    private Long userId;
    private BigDecimal balance;
    private String currency;
}
