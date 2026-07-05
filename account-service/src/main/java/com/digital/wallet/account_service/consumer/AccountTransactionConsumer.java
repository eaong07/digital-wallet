package com.digital.wallet.account_service.consumer;

import com.digital.wallet.account_service.event.TransactionEvent;
import com.digital.wallet.account_service.service.AccountManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class AccountTransactionConsumer {
    private final Map<String, AccountManagementService> serviceMap;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(
            topics = "transaction_input_topic",
            groupId = "my-spring-app-group-v2",
            containerFactory = "kafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, String> record) {
        TransactionEvent event = objectMapper.readValue(record.value(), TransactionEvent.class);
        log.info("Received event from user {} for transaction type {}", event.getUserId(), record.key());
        serviceMap
                .get(record.key())
                .updateAccount(event);
    }
}
