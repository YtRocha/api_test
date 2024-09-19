package com.example.apitest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuração de Webclient
 */
@Configuration
public class WebClienteConfig {
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
