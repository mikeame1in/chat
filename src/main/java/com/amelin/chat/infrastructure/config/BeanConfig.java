package com.amelin.chat.infrastructure.config;

import com.amelin.chat.application.usecase.ChatServiceImpl;
import com.amelin.chat.application.exception.ChatServiceValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public ChatServiceImpl chatRegistrationServiceImpl() {
        return new ChatServiceImpl(
                new ChatServiceValidatorImpl());
    }
}
