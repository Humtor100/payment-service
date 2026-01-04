package com.kirito.payment_service.entity;

import com.kirito.payment_service.model.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp; // Добавить зависимость hibernate-core, она есть в jpa

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Важно для SERIAL
    private Long id; // Лучше использовать обертку Long, а не примитив long

    @Column(name = "source_account_id") // Явное указание имени колонки
    private Long sourceAccountId;

    @Column(name = "target_account_id", nullable = false)
    private Long targetAccountId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency; // Исправлена опечатка

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status; // Тип поля - Enum!

    @CreationTimestamp // Автоматически заполнит время при сохранении
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}