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

/**
 * A service para agents
 */
@Service
public class AgentService {


    /**
     * Instancia webclient
     */
    private final WebClient webClient;

    /**
     * Instancia do repositorio para agent
     */
    @Autowired
    private AgentRepository agentRepository;

    /**
     * Instancia do mapper de agent
     */
    @Autowired
    private AgentMapper agentMapper;

    /**
     * Instancia de objectMapper
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Construtor que recebe o webclient.builder
     *
     * @param webClientBuilder
     */
    public AgentService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://valorant-api.com/v1").build();
    }

    /**
     * Solicita os agents na api externa e salva no banco de dados
     *
     * @return mono com lista de agentDto
     */
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
