package com.digital.wallet.customer_service.configuration;

import com.digital.wallet.customer_service.repository.CustomerRepository;
import com.digital.wallet.customer_service.service.CustomerService;
import com.digital.wallet.customer_service.util.CustomerMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.digital.wallet.customer_service.repository")
@Configuration
public class ServiceConfiguration {
    @Bean
    CustomerService userService(CustomerRepository customerRepository,
                                CustomerMapper customerMapper){
        return new CustomerService(customerRepository, customerMapper);
    }
}
