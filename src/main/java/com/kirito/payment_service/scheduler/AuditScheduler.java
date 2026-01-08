package com.kirito.payment_service.scheduler;

import com.kirito.payment_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AuditScheduler {

    private final AccountRepository accountRepository;

    @Scheduled(fixedRate = 60000)
    public void reportTotalBalance() {
        BigDecimal total = accountRepository.getTotalBalance();
        System.out.println("AUDIT: Total money in the bank: " + total + " RUB");
    }
}
