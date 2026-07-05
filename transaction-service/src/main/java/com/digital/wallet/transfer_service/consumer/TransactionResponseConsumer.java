package com.digital.wallet.transfer_service.consumer;

import com.digital.wallet.transfer_service.event.TransactionResponseEvent;
import com.digital.wallet.transfer_service.service.TransactionResponseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
@Slf4j
public class TransactionResponseConsumer {
    private final TransactionResponseService transactionResponseService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "transaction_output_topic")
    @Transactional
    public void consumer(ConsumerRecord<String, String> record) {
        TransactionResponseEvent event = objectMapper.readValue(record.value(), TransactionResponseEvent.class);
        log.info("Consumed response event from account service {}", event.getUserId());
        transactionResponseService.updateTransaction(record.key(), event);
    }
}
