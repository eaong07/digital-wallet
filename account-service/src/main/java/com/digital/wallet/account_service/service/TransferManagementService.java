package com.digital.wallet.account_service.service;

import com.digital.wallet.account_service.event.TransactionEvent;
import com.digital.wallet.account_service.event.TransactionResponseEvent;
import com.digital.wallet.account_service.entity.Account;
import com.digital.wallet.account_service.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public class TransferManagementService implements AccountManagementService {

    private final AccountRepository accountRepository;

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Override
    @Transactional
    public void updateAccount(TransactionEvent transactionEvent) {
        log.info("Search for account of users {} and {} to transfer account", transactionEvent.getSenderId(), transactionEvent.getReceiverId());
        Account receiver = accountRepository.findById(transactionEvent.getReceiverId()).orElse(null);
        Account sender = accountRepository
                .findByAccountIdAndUserId(
                        transactionEvent.getSenderId(),
                        transactionEvent.getUserId())
                .orElse(null);
        if (!isValidTransfer(receiver, sender, transactionEvent)) {
            log.info("Account not found for users {} and {}, will now send transaction failed response event", sender.getUserId(), receiver.getUserId());
            kafkaTemplate.send(
                    "transaction_output_topic",
                    "TRANSFER",
                    TransactionResponseEvent
                            .builder()
                            .receiverId(transactionEvent.getReceiverId())
                            .senderId(transactionEvent.getSenderId())
                            .status("FAILED")
                            .userId(transactionEvent.getUserId())
                            .amount(transactionEvent.getAmount())
                            .build());
        } else {
            log.info("Account found for users {} and {}, will now transfer the amount", transactionEvent.getSenderId(), transactionEvent.getReceiverId());
            receiver.setBalance(receiver.getBalance() + transactionEvent.getAmount());
            sender.setBalance(sender.getBalance() - transactionEvent.getAmount());
            accountRepository.saveAll(Arrays.asList(receiver, sender));
            log.info("Amount transferred for users {} and {}, Sending response to transaction service", transactionEvent.getSenderId(), transactionEvent.getReceiverId()
            );
            kafkaTemplate.send(
                    "transaction_output_topic",
                    "TRANSFER",
                    TransactionResponseEvent
                            .builder()
                            .receiverId(receiver.getAccountId())
                            .senderId(sender.getAccountId())
                            .status("SUCCESS")
                            .userId(transactionEvent.getUserId())
                            .amount(transactionEvent.getAmount())
                            .build());
        }
    }

    private boolean isValidTransfer(
            Account receiver,
            Account sender,
            TransactionEvent transactionEvent
    ) {
        return sender != null
                && receiver != null
                && sender.getBalance() - transactionEvent.getAmount() > 0;

    }
}
