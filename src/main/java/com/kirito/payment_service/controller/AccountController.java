package com.kirito.payment_service.controller;

import com.kirito.payment_service.dto.AccountResponseDTO;
import com.kirito.payment_service.dto.CreateAccountRequestDTO;
import com.kirito.payment_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public AccountResponseDTO create(@RequestBody CreateAccountRequestDTO requestDTO) {
        return accountService.createAccount(requestDTO);
    }

    @GetMapping("/{id}")
    public AccountResponseDTO get(@PathVariable Long id) {
        return accountService.getAccount(id);
    }
}
