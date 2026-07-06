package com.digital.wallet.transfer_service.repository;

import com.digital.wallet.transfer_service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findAllByUserId(String userId);
    Optional<Transaction> findByTransactionIdAndUserId(String transactionId, String userId);
    List<Transaction> findAllByUserIdAndTransactionDateAndType(String userId, Date transactionDate, String type);
}
