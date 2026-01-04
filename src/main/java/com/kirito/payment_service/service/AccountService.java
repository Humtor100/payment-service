package com.kirito.payment_service.service;

import com.kirito.payment_service.dto.AccountResponseDTO;
import com.kirito.payment_service.dto.CreateAccountRequestDTO;
import com.kirito.payment_service.entity.Account;
import com.kirito.payment_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository; // Лучше private

    public AccountResponseDTO createAccount(CreateAccountRequestDTO request) {
        Account account = new Account();
        // Мы НЕ устанавливаем account.setId(), база сделает это сама
        account.setUserId(request.getUserId());
        account.setBalance(BigDecimal.ZERO);
        account.setCurrency(request.getCurrency());

        // Сохраняем и получаем обратно сущность уже с ID
        Account savedAccount = accountRepository.save(account);

        // Превращаем Entity в DTO и возвращаем
        return mapToDTO(savedAccount);
    }

    public AccountResponseDTO getAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));

        return mapToDTO(account);
    }

    // Вспомогательный метод для маппинга
    private AccountResponseDTO mapToDTO(Account account) {
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setId(account.getId());
        dto.setUserId(account.getUserId());
        dto.setBalance(account.getBalance());
        dto.setCurrency(account.getCurrency());
        return dto;
    }
}