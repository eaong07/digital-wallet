package com.digital.wallet.account_service.service;

import com.digital.wallet.account_service.event.TransactionEvent;
import com.digital.wallet.account_service.event.TransactionResponseEvent;
import com.digital.wallet.account_service.entity.Account;
import com.digital.wallet.account_service.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class DebitManagementService implements AccountManagementService {

    private final AccountRepository accountRepository;

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Override
    @Transactional
    public void updateAccount(TransactionEvent transactionEvent) {
        Account account = accountRepository
                .findByAccountIdAndUserId(
                        transactionEvent.getAccountId(),
                        transactionEvent.getUserId())
                .orElse(null);
        if (isValidDebit(account, transactionEvent)) {
            account.setBalance(account.getBalance() - transactionEvent.getAmount());
            accountRepository.save(account);
            kafkaTemplate.send(
                    "transaction_output_topic",
                    "DEBIT",
                    TransactionResponseEvent.builder()
                            .amount(transactionEvent.getAmount())
                            .accountId(transactionEvent.getAccountId())
                            .userId(transactionEvent.getUserId())
                            .status("SUCCESS")
                            .build());
        } else {
            kafkaTemplate.send(
                    "transaction_output_topic",
                    "DEBIT",
                    TransactionResponseEvent.builder()
                            .amount(transactionEvent.getAmount())
                            .accountId(transactionEvent.getAccountId())
                            .userId(transactionEvent.getUserId())
                            .status("FAILED")
                            .build());
        }
    }

    private boolean isValidDebit(Account account, TransactionEvent transactionEvent) {
        return account != null && account.getBalance() - transactionEvent.getAmount() >= 0d;
    }
}
