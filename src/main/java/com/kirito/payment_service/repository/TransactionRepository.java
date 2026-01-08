package com.kirito.payment_service.repository;

import com.kirito.payment_service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    boolean existsByIdempotencyKey(String idempotencyKey);
}

