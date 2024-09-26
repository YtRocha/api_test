package com.example.apitest.service.valorant;

import com.example.apitest.dto.valorant.AgentDto;
import com.example.apitest.mapper.valorant.AgentMapper;
import com.example.apitest.model.valorant.Agent;
import com.example.apitest.repository.valorant.AgentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

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

    /**
     * Metodo que busca todos os agents no banco de dados
     *
     * @return lista de agentDto
     */
    public List<AgentDto> getAll() {
        try {

            List<Agent> agents = agentRepository.findAll();

            return agentMapper.toDtoList(agents);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao recuperar agents do " +
                    "banco de dados");
        }
    }

    /**
     * Metodo que retorna agent especifico pelo seu nome
     *
     * @param name
     * @return agentDto
     */
    public AgentDto getByName(String name) {

            Optional<Agent> agent = agentRepository.findByDisplayName(name);

            if (agent.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agente não encontrado");
            }

            return agentMapper.toDto(agent.get());

    }
}
