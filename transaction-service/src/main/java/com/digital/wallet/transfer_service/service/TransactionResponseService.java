package com.digital.wallet.transfer_service.service;

import com.digital.wallet.transfer_service.dto.TransactionDto;
import com.digital.wallet.transfer_service.entity.Transaction;
import com.digital.wallet.transfer_service.event.TransactionResponseEvent;
import com.digital.wallet.transfer_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class TransactionResponseService {
    private final TransactionRepository transactionRepository;

    public void updateTransaction(String type, TransactionResponseEvent event) {
        log.info("Saving Transaction by user {} with status {}", event.getUserId(), event.getStatus());
        transactionRepository.save(Transaction
                .builder()
                .receiver(event.getReceiverId())
                .sender(event.getSenderId())
                .accountId(event.getAccountId())
                .type(type)
                .status(event.getStatus())
                .userId(event.getUserId())
                .amount(event.getAmount())
                .transactionDate(java.sql.Date.valueOf(LocalDate.now()))
                .build());
    }
}
