package com.amelin.chat.infrastructure.config;

import com.amelin.chat.application.usecase.ChatServiceImpl;
import com.amelin.chat.application.exception.ChatServiceValidatorImpl;
import com.amelin.chat.infrastructure.websocket.MessageSenderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class BeanConfig {
    @Bean
    public ChatServiceImpl chatRegistrationServiceImpl() {
        return new ChatServiceImpl(
                new ChatServiceValidatorImpl());
    }
}
