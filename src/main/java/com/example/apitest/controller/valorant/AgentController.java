package com.example.apitest.controller.valorant;

import com.example.apitest.dto.valorant.AgentDto;
import com.example.apitest.model.valorant.Agent;
import com.example.apitest.service.valorant.AgentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
@Tag(name = "Agents", description = "Operações para a model de Agents de Valorant")
public class AgentController {

    private AgentService agentService;

    @GetMapping
    public Mono<ResponseEntity<?>> getOnlineAgentsAndSave() {
        return agentService.getOnlineAgentsAndSave()
                .map(agentDtos -> {
                    if (agentDtos == null || agentDtos.isEmpty()) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.ok(agentDtos);
                })
                .onErrorResume(e -> {
                    return Mono.just(ResponseEntity
                            .status(500)
                            .body(null));
                });
    }

}
