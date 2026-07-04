package com.digital.wallet.transfer_service.service;

import com.digital.wallet.transfer_service.dto.TransactionDto;
import com.digital.wallet.transfer_service.entity.Transaction;
import com.digital.wallet.transfer_service.event.TransactionResponseEvent;
import com.digital.wallet.transfer_service.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TransactionResponseService {
    private final TransactionRepository transactionRepository;

    public void updateTransaction(String type, TransactionResponseEvent event) {
        transactionRepository.save(Transaction
                .builder()
                .receiver(event.getReceiverId())
                .sender(event.getSenderId())
                .accountId(event.getAccountId())
                .type(type)
                .status(event.getStatus())
                .userId(event.getUserId())
                .amount(event.getAmount())
                .build());
    }
}
