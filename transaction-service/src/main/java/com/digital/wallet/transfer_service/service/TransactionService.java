package com.digital.wallet.transfer_service.service;

import com.digital.wallet.transfer_service.dto.DebitCreditDto;
import com.digital.wallet.transfer_service.dto.TransactionDto;
import com.digital.wallet.transfer_service.dto.TransferDto;
import com.digital.wallet.transfer_service.entity.Transaction;
import com.digital.wallet.transfer_service.event.TransactionEvent;
import com.digital.wallet.transfer_service.repository.TransactionRepository;
import com.digital.wallet.transfer_service.util.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public void debit(DebitCreditDto debitCreditDto, String userId) {
        log.info("Sending kafka event to transaction service for account debit {}", userId);
        kafkaTemplate.send("transaction_input_topic", "DEBIT",
                TransactionEvent
                        .builder()
                        .userId(userId)
                        .accountId(debitCreditDto.getAccountId())
                        .amount(debitCreditDto.getAmount())
                        .build());
    }

    public void credit(DebitCreditDto debitCreditDto, String userId) {
        log.info("Sending kafka event to transaction service for account credit {}", userId);
        kafkaTemplate.send("transaction_input_topic", "CREDIT",
                TransactionEvent
                        .builder()
                        .userId(userId)
                        .accountId(debitCreditDto.getAccountId())
                        .amount(debitCreditDto.getAmount())
                        .build());
    }

    public void transfer(TransferDto transferDto, String userId) {
        if (wasDailyLimitExceeded(transferDto, userId)) {
            log.error("Amount exceeded daily transfer limit");
            throw new RuntimeException("Amount exceeded daily transfer limit");
        }
        log.info("Sending kafka event to transaction service for transfer {}", userId);
        kafkaTemplate.send("transaction_input_topic", "TRANSFER",
                TransactionEvent
                        .builder()
                        .userId(userId)
                        .receiverId(transferDto.getReceiverId())
                        .senderId(transferDto.getSenderId())
                        .amount(transferDto.getAmount())
                        .build());
    }

    public List<TransactionDto> getHistory(String userId) {
        List<Transaction> transactions = transactionRepository.findAllByUserId(userId);
        return transactions.parallelStream().map(transactionMapper::toDto).toList();
    }

    public TransactionDto getTransaction(String transactionID, String userId) {
        Optional<Transaction> transaction = transactionRepository
                .findByTransactionIdAndUserId(transactionID, userId);
        return transaction.map(transactionMapper::toDto).orElseThrow();
    }

    private boolean wasDailyLimitExceeded(TransferDto transferDto, String userId) {
        List<Transaction> transactions = transactionRepository.findAllByUserIdAndTransactionDateAndType(
                userId,
                java.sql.Date.valueOf(LocalDate.now()),
                "TRANSFER");
        double total = transactions.stream().mapToDouble(Transaction::getAmount).sum() + transferDto.getAmount();
        return total >= 50000d;
    }
}
