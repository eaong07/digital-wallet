package com.digital.wallet.account_service.service;

import com.digital.wallet.account_service.event.TransactionEvent;
import com.digital.wallet.account_service.event.TransactionResponseEvent;
import com.digital.wallet.account_service.entity.Account;
import com.digital.wallet.account_service.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class CreditManagementService implements AccountManagementService {

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
        if (account != null) {
            double newBalance = account.getBalance() + transactionEvent.getAmount();
            account.setBalance(newBalance);
            accountRepository.save(account);
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
