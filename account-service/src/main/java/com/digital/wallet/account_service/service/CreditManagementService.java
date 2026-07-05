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
public class CreditManagementService implements AccountManagementService {

    private final AccountRepository accountRepository;

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Override
    @Transactional
    public void updateAccount(TransactionEvent transactionEvent) {
        log.info("Search for account of user {} to credit account", transactionEvent.getUserId());
        Account account = accountRepository
                .findByAccountIdAndUserId(
                        transactionEvent.getAccountId(),
                        transactionEvent.getUserId())
                .orElse(null);
        if (account != null) {
            log.info("Account found for user {}, will now credit the amount", account.getUserId());
            double newBalance = account.getBalance() + transactionEvent.getAmount();
            account.setBalance(newBalance);
            accountRepository.save(account);
            log.info("Amount credited for user {}, Sending response to transaction service", account.getUserId());
            kafkaTemplate.send(
                    "transaction_output_topic",
                    "CREDIT",
                    TransactionResponseEvent
                            .builder()
                            .accountId(transactionEvent.getAccountId())
                            .userId(transactionEvent.getUserId())
                            .amount(transactionEvent.getAmount())
                            .status("SUCCESS")
                            .build());
        } else {
            log.info("Account not found for user {}, will now send failed response to transaction service", account.getUserId());
            kafkaTemplate.send(
                    "transaction_output_topic",
                    "CREDIT", TransactionResponseEvent
                            .builder()
                            .accountId(transactionEvent.getAccountId())
                            .userId(transactionEvent.getUserId())
                            .amount(transactionEvent.getAmount())
                            .status("FAILED")
                            .build());
        }
    }
}
