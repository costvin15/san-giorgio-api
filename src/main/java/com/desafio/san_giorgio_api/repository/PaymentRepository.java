package com.desafio.san_giorgio_api.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.desafio.san_giorgio_api.repository.entities.PaymentEntity;

import jakarta.transaction.Transactional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE payments SET amount_paid = :amountPaid, payment_status = :paymentStatus WHERE payment_id = :paymentId", nativeQuery = true)
    void updateAmountPaid(String paymentId, BigDecimal amountPaid, String paymentStatus);
}
