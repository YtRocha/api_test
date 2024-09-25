package com.example.apitest.controller.valorant;

import com.example.apitest.service.valorant.AgentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
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

}
