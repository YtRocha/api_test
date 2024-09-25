package com.example.apitest.service.valorant;

import com.example.apitest.dto.valorant.AgentDto;
import com.example.apitest.mapper.valorant.AgentMapper;
import com.example.apitest.model.valorant.Agent;
import com.example.apitest.repository.valorant.AgentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AgentService {


    private final WebClient webClient;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private AgentMapper agentMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public AgentService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://valorant-api.com/v1").build();
    }

    public Mono<List<AgentDto>> getOnlineAgentsAndSave() {
        return webClient.get()
                .uri("/agents?isplayablecharacter=true")
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    try {
                        JsonNode root = objectMapper.readTree(response);
                        JsonNode dataNode = root.get("data");

                        List<AgentDto> agentDtos = objectMapper
                                .readerForListOf(AgentDto.class)
                                .readValue(dataNode);

                        List<Agent> agents = agentMapper.toEntityList(agentDtos);

                        agentRepository.saveAll(agents);

                        return Mono.just(agentDtos);

                    } catch (Exception e) {
                        return Mono.error(new RuntimeException("Erro ao processar o JSON", e));
                    }
                });
    }
}
