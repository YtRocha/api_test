package com.example.apitest.service.worldtime;

import com.example.apitest.model.worldtime.Timezone;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * A service para Timezone
 */
@Service
public class TimezoneService {

    /**
     * Instancia de webclient para realizar as chamadas de api externa para a api de timezone
     */
    private final WebClient webClient;

    /**
     * O Construtor
     *
     * @param webClient
     */
    public TimezoneService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * função que solicita a api externa as timezones disponiveis
     *
     * @return Lista de strings de timezones disponiveis
     */
    public List<String> getTimezones() {
        Mono<List<String>> timezonesMono = webClient.get()
                .uri("https://worldtimeapi.org/api/timezone")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                });
        return timezonesMono.block();
    }
}
