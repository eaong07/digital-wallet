package com.digital.wallet.transfer_service.configuration;

import com.digital.wallet.transfer_service.consumer.TransactionResponseConsumer;
import com.digital.wallet.transfer_service.repository.TransactionRepository;
import com.digital.wallet.transfer_service.service.TransactionResponseService;
import com.digital.wallet.transfer_service.service.TransactionService;
import com.digital.wallet.transfer_service.util.TransactionMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.core.KafkaTemplate;

@EnableJpaRepositories(basePackages = "com.digital.wallet.transfer_service.repository")
@Configuration
public class ServiceConfiguration {
    @Bean
    public TransactionService transactionService(TransactionRepository repository,
                                                 TransactionMapper transactionMapper,
                                                 KafkaTemplate<Object, Object> kafkaTemplate) {
        return new TransactionService(repository, transactionMapper, kafkaTemplate);
    }

    @Bean
    public TransactionResponseConsumer transactionResponseConsumer(
            TransactionResponseService transactionResponseService
    ) {
        return new TransactionResponseConsumer(transactionResponseService);
    }

    @Bean
    public TransactionResponseService transactionResponseService(TransactionRepository repository){
        return new TransactionResponseService(repository);
    }
}
