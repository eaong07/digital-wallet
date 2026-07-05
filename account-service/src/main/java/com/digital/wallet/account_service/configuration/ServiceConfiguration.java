package com.digital.wallet.account_service.configuration;

import com.digital.wallet.account_service.consumer.AccountTransactionConsumer;
import com.digital.wallet.account_service.repository.AccountRepository;
import com.digital.wallet.account_service.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "com.digital.wallet.account_service.repository")
public class ServiceConfiguration {

    @Bean("CREDIT")
    public AccountManagementService credit(
            AccountRepository accountRepository,
            KafkaTemplate<Object, Object> kafkaTemplate
    ) {
        return new CreditManagementService(accountRepository, kafkaTemplate);
    }

    @Bean("DEBIT")
    public AccountManagementService debit(
            AccountRepository accountRepository,
            KafkaTemplate<Object, Object> kafkaTemplate
    ) {
        return new DebitManagementService(accountRepository, kafkaTemplate);
    }

    @Bean("TRANSFER")
    public AccountManagementService transfer(
            AccountRepository accountRepository,
            KafkaTemplate<Object, Object> kafkaTemplate
    ) {
        return new TransferManagementService(accountRepository, kafkaTemplate);
    }

    @Bean
    public AccountTransactionConsumer accountTransactionConsumer(Map<String, AccountManagementService> accountManagementServiceMap) {
        return new AccountTransactionConsumer(accountManagementServiceMap);
    }

    @Bean
    public AccountService accountService(AccountRepository accountRepository) {
        return new AccountService(accountRepository);
    }

}
