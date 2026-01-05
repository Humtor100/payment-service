package com.kirito.payment_service.service;

import com.kirito.payment_service.dto.MakePaymentRequestDTO;
import com.kirito.payment_service.entity.Account;
import com.kirito.payment_service.entity.Transaction;
import com.kirito.payment_service.exception.AccountNotFoundException;
import com.kirito.payment_service.exception.InsufficientBalanceException;
import com.kirito.payment_service.model.TransactionStatus;
import com.kirito.payment_service.repository.AccountRepository;
import com.kirito.payment_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void makeTransfer(MakePaymentRequestDTO request) {

        Account sourceAccount = accountRepository.findByIdForUpdate(request.getSourceAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Sender account not found"));

        Account targetAccount = accountRepository.findByIdForUpdate(request.getTargetAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Receiver account not found"));

        if (sourceAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient funds");
        }

        BigDecimal newSourceBalance = sourceAccount.getBalance().subtract(request.getAmount());
        BigDecimal newTargetBalance = targetAccount.getBalance().add(request.getAmount());

        sourceAccount.setBalance(newSourceBalance);
        targetAccount.setBalance(newTargetBalance);

        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        // Создаем и сохраняем запись о транзакции (Чек)
        Transaction transaction = new Transaction();
        transaction.setSourceAccountId(sourceAccount.getId());
        transaction.setTargetAccountId(targetAccount.getId());
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(sourceAccount.getCurrency());
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(transaction);
    }
}