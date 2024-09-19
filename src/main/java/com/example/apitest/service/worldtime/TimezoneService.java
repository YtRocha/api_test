package com.example.apitest.service.worldtime;

import com.example.apitest.model.worldtime.Timezone;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class TimezoneService {
    private final WebClient webClient;

    public TimezoneService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Timezone> getTimezoneDisponiveis() {

        String apiUrl = "http://worldtimeapi.org/api/timezone";

        return webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(Timezone.class);
    }
}
