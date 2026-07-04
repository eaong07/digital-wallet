package com.digital.wallet.transfer_service.consumer;

import com.digital.wallet.transfer_service.event.TransactionResponseEvent;
import com.digital.wallet.transfer_service.service.TransactionResponseService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
public class TransactionResponseConsumer {
    private final TransactionResponseService transactionResponseService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @KafkaListener(topics = "transaction_output_topic")
    public void consumer(ConsumerRecord<String, String> record){
        TransactionResponseEvent event = objectMapper.readValue(record.value(), TransactionResponseEvent.class);
        transactionResponseService.updateTransaction(record.key(), event);
    }
}
