package com.digital.wallet.account_service.service;

import com.digital.wallet.account_service.event.TransactionEvent;
import com.digital.wallet.account_service.event.TransactionResponseEvent;
import com.digital.wallet.account_service.entity.Account;
import com.digital.wallet.account_service.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
@Slf4j
public class DebitManagementService implements AccountManagementService {

    private final AccountRepository accountRepository;

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Override
    @Transactional
    public void updateAccount(TransactionEvent transactionEvent) {
        log.info("Search for account of user {} to debit account", transactionEvent.getUserId());
        Account account = accountRepository
                .findByAccountIdAndUserId(
                        transactionEvent.getAccountId(),
                        transactionEvent.getUserId())
                .orElse(null);
        if (isValidDebit(account, transactionEvent)) {
            log.info("Account found for user {}, will now debit the amount", account.getUserId());
            account.setBalance(account.getBalance() - transactionEvent.getAmount());
            accountRepository.save(account);
            log.info("Amount debited for user {}, Sending response to transaction service", account.getUserId());
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
            log.info("Account not found for user {}, will now send failed response to transaction service", account.getUserId());
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
