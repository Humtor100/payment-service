package com.kirito.payment_service;

import com.kirito.payment_service.dto.MakePaymentRequestDTO;
import com.kirito.payment_service.entity.Account;
import com.kirito.payment_service.exception.InsufficientBalanceException;
import com.kirito.payment_service.repository.AccountRepository;
import com.kirito.payment_service.repository.TransactionRepository;
import com.kirito.payment_service.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    @DisplayName("Successful transaction test!")
    void makeTransfer_shouldTransferMoney_whenBalanceIsSufficient() {

        Long sourceId = 1L;
        Long targetId = 2L;
        BigDecimal amount = new BigDecimal("100.00");

        Account sender = new Account(sourceId, 100L, new BigDecimal("500.00"), "RUB");
        Account receiver = new Account(targetId, 200L, new BigDecimal("200.00"), "RUB");

        when(accountRepository.findByIdForUpdate(sourceId)).thenReturn(Optional.of(sender));
        when(accountRepository.findByIdForUpdate(targetId)).thenReturn(Optional.of(receiver));

        MakePaymentRequestDTO request = new MakePaymentRequestDTO();
        request.setSourceAccountId(sourceId);
        request.setTargetAccountId(targetId);
        request.setAmount(amount);

        paymentService.makeTransfer(request);

        assertEquals(new BigDecimal("400.00"), sender.getBalance());
        assertEquals(new BigDecimal("300.00"), receiver.getBalance());

        verify(accountRepository, times(1)).save(sender);
        verify(accountRepository, times(1)).save(receiver);
        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Insufficient funds")
    void makeTransfer_shouldThrowException_whenBalanceIsInsufficient() {

        Long sourceId = 1L;
        Long targetId = 2L;
        BigDecimal amount = new BigDecimal("100.00");

        Account sender = new Account(sourceId, 100L, new BigDecimal("10.00"), "RUB");
        Account receiver = new Account(targetId, 200L, new BigDecimal("200.00"), "RUB");

        when(accountRepository.findByIdForUpdate(sourceId)).thenReturn(Optional.of(sender));
        when(accountRepository.findByIdForUpdate(targetId)).thenReturn(Optional.of(receiver));

        MakePaymentRequestDTO request = new MakePaymentRequestDTO();
        request.setSourceAccountId(sourceId);
        request.setTargetAccountId(targetId);
        request.setAmount(amount);

        assertThrows(InsufficientBalanceException.class, () -> {
            paymentService.makeTransfer(request);
        });

        verify(accountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }
}
