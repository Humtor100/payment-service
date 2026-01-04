package com.kirito.payment_service.dto;

import lombok.Data;

@Data
public class CreateAccountRequestDTO {
    private long userId;
    private String currency;
}
