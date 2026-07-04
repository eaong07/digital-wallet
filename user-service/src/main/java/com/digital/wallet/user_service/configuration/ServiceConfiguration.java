package com.digital.wallet.user_service.configuration;

import com.digital.wallet.user_service.repository.UserRepository;
import com.digital.wallet.user_service.service.UserService;
import com.digital.wallet.user_service.util.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.digital.wallet.user_service.repository")
@Configuration
public class ServiceConfiguration {
    @Bean
    UserService userService(UserRepository userRepository,
                            UserMapper userMapper){
        return new UserService(userRepository, userMapper);
    }
}
