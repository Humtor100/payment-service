package com.kirito.payment_service.service;

import com.kirito.payment_service.dto.AccountResponseDTO;
import com.kirito.payment_service.dto.CreateAccountRequestDTO;
import com.kirito.payment_service.dto.DepositRequestDTO;
import com.kirito.payment_service.entity.Account;
import com.kirito.payment_service.entity.UserEntity;
import com.kirito.payment_service.exception.AccountNotFoundException;
import com.kirito.payment_service.repository.AccountRepository;
import com.kirito.payment_service.repository.UserRepository;
import com.kirito.payment_service.service.provider.PaymentProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PaymentProvider paymentProvider;
    private final PaymentService paymentService;
    private final UserRepository userRepository;

    public void deposit(DepositRequestDTO request) {
        boolean paymentSuccess = paymentProvider.processPayment(request.getCardNumber(), request.getAmount());
        if (!paymentSuccess) {
            throw new RuntimeException("Bank rejected transaction");
        }
        paymentService.processDeposit(request.getAccountId(), request.getAmount());
    }

    public AccountResponseDTO createAccount(CreateAccountRequestDTO request) {

        UserEntity currentUser = getCurrentUser();

        Account account = new Account();

        account.setUserId(currentUser.getId());

        account.setBalance(BigDecimal.ZERO);
        account.setCurrency(request.getCurrency());

        Account savedAccount = accountRepository.save(account);

        return mapToDTO(savedAccount);
    }

    public AccountResponseDTO getAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + accountId));

        UserEntity currentUser = getCurrentUser();

        if (account.getUserId() != currentUser.getId()) {
            throw new AccessDeniedException("Это не ваш счет! Доступ запрещен.");
        }

        return mapToDTO(account);
    }

    private UserEntity getCurrentUser() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private AccountResponseDTO mapToDTO(Account account) {
        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setId(account.getId());
        dto.setUserId(account.getUserId());
        dto.setBalance(account.getBalance());
        dto.setCurrency(account.getCurrency());
        return dto;
    }
}