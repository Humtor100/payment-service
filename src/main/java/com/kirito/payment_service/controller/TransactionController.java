package com.kirito.payment_service.controller;

import com.kirito.payment_service.dto.MakePaymentRequestDTO;
import com.kirito.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class TransactionController {

    private final PaymentService paymentService;

    @PostMapping("/transfer")
    public String makeTransfer(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestBody MakePaymentRequestDTO requestDTO) {
        paymentService.makeTransfer(requestDTO, idempotencyKey);
        return "Transfer succesfull!";
    }
}
