package com.example.apitest.controller.valorant;

import com.example.apitest.dto.valorant.AgentDto;
import com.example.apitest.service.valorant.AgentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * O controler para agents
 */
@RestController
@RequestMapping("/api/agents")
@Tag(name = "Agents", description = "Operações para a model de Agents de Valorant")
public class AgentController {

    /**
     * Instancia da AgentService
     */
    @Autowired
    private AgentService agentService;

    /**
     * Rota get para consumir dados da api externa
     *
     * @return Lista de agents
     */
    @Operation(summary = "Consumir agents",
            description = "Consome todos os agents da api externa e salva no banco de dados")
    @GetMapping("/externos")
    public Mono<ResponseEntity<?>> getOnlineAgentsAndSave() {
        return agentService.getOnlineAgentsAndSave()
                .map(agentDtos -> {
                    if (agentDtos == null || agentDtos.isEmpty()) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.ok(agentDtos);
                })
                .onErrorResume(e -> {

                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("status", "500");
                    errorResponse.put("error", "Internal Server Error");
                    errorResponse.put("message", "Ocorreu um erro ao buscar ou salvar agent.");
                    errorResponse.put("details", e.getMessage());

                    return Mono.just(ResponseEntity
                            .status(500)
                            .body(errorResponse));
                });
    }

    /**
     * Rota que retorna todos os agents do banco de dados
     *
     * @return lista de agents
     */
    @Operation(summary = "Pegar todos os agents", description = "Retorna todos os agents cadastrados no banco de dados")
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<AgentDto> agentDtos = agentService.getAll();

            return ResponseEntity.ok(agentDtos);
        } catch (ResponseStatusException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", String.valueOf(e.getStatusCode()));
            errorResponse.put("error", e.getReason());
            errorResponse.put("message", "Ocorreu um erro ao buscar os agents.");
            errorResponse.put("details", e.getReason());

            return ResponseEntity.status(e.getStatusCode()).body(errorResponse);

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "500");
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", "Ocorreu um erro inesperado ao buscar os agents.");
            errorResponse.put("details", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
