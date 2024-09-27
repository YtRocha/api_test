package com.example.apitest.controller.valorant;

import com.example.apitest.dto.valorant.AgentDto;
import com.example.apitest.dto.valorant.AgentUpdateDto;
import com.example.apitest.model.valorant.Ability;
import com.example.apitest.model.valorant.AbilitySlot;
import com.example.apitest.model.valorant.Agent;
import com.example.apitest.service.valorant.AgentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AgentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AgentService agentService;

    @Autowired
    ObjectMapper objectMapper;

    private AbilitySlot slot = AbilitySlot.Ability1;

    private Ability ability = new Ability(115L,
            slot,
            "Wingman",
            "EQUIP Wingman. FIRE to send Wingman forward seeking enemies. Wingman unleashes a concussive blast toward the first enemy he sees. ALT FIRE when targeting a Spike site or planted Spike to have Wingman defuse or plant the Spike. To plant, Gekko must have the Spike in his inventory. When Wingman expires he reverts into a dormant globule. INTERACT to reclaim the globule and gain another Wingman charge after a short cooldown.",
            "https://media.valorant-api.com/agents/e370fa57-4757-3604-3648-499e1f642d3f/abilities/ability1/displayicon.png"
    );

    private List<Ability> abilities = List.of(ability);

    private Agent agent_1 = new Agent("e370fa57-4757-3604-3648-499e1f642d3f",
            "Gekko",
            "Gekko the Angeleno leads a tight-knit crew of calamitous creatures. His buddies bound forward, scattering enemies out of the way, with Gekko chasing them down to regroup and go again.",
            "Aggrobot",
            "https://media.valorant-api.com/agents/e370fa57-4757-3604-3648-499e1f642d3f/displayicon.png",
            "https://media.valorant-api.com/agents/e370fa57-4757-3604-3648-499e1f642d3f/fullportrait.png",
            abilities
    );

    private AgentDto agentDto = new AgentDto("e370fa57-4757-3604-3648-499e1f642d3f",
            "Gekko",
            "Gekko the Angeleno leads a tight-knit crew of calamitous creatures. His buddies bound forward, scattering enemies out of the way, with Gekko chasing them down to regroup and go again.",
            "Aggrobot",
            "https://media.valorant-api.com/agents/e370fa57-4757-3604-3648-499e1f642d3f/displayicon.png",
            "https://media.valorant-api.com/agents/e370fa57-4757-3604-3648-499e1f642d3f/fullportrait.png",
            abilities);

    private AgentUpdateDto agentUpdateDto = new AgentUpdateDto("Gekko",
            "Gekko the Angeleno leads a tight-knit crew of calamitous creatures. His buddies bound forward, scattering enemies out of the way, with Gekko chasing them down to regroup and go again.",
            "Aggrobot",
            "https://media.valorant-api.com/agents/e370fa57-4757-3604-3648-499e1f642d3f/displayicon.png",
            "https://media.valorant-api.com/agents/e370fa57-4757-3604-3648-499e1f642d3f/fullportrait.png",
            abilities);

    private String uuid = "e370fa57-4757-3604-3648-499e1f642d3f";

    private List<Agent> agents = List.of(agent_1);
    private List<AgentDto> agentDtos = List.of(agentDto);

    @Test
    void getAll_Sucess() throws Exception{
        mockMvc.perform(get("/api/agents"))
                .andExpect(status().isOk());
    }


    @ParameterizedTest
    @CsvSource({
            "400, 'Erro de validação', 'Ocorreu um erro ao buscar os agents.', 'Erro de validação'",
            "404, 'Agentes não encontrados', 'Ocorreu um erro ao buscar os agents.', 'Agentes não encontrados'",
            "500, 'Internal Server Error', 'Ocorreu um erro ao buscar os agents.', 'Internal Server Error'"
    })
    void getAll_Exception(int status, String errorMessage, String expectedMessage, String expectedDetails) throws Exception {
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.valueOf(status), errorMessage);
        when(agentService.getAll()).thenThrow(exception);

        mockMvc.perform(get("/api/agents"))
                .andExpect(status().is(status))
                .andExpect(jsonPath("$.error").value(expectedDetails))
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.details").value(errorMessage));
    }

    @Test
    void getByName_Sucess() throws Exception{
        when(agentService.getByName(any())).thenReturn(agentDto);

        mockMvc.perform(get("/api/agents/Gekko"))
                .andExpect(status().isOk());
    }


    @ParameterizedTest
    @CsvSource({
            "400, 'Erro de validação', 'Ocorreu um erro ao buscar o agent.', 'Erro de validação'",
            "404, 'Agentes não encontrados', 'Ocorreu um erro ao buscar o agent.', 'Agentes não encontrados'",
            "500, 'Internal Server Error', 'Ocorreu um erro ao buscar o agent.', 'Internal Server Error'"
    })
    void getByName_Exception(int status, String errorMessage, String expectedMessage, String expectedDetails) throws Exception {
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.valueOf(status), errorMessage);
        when(agentService.getByName(any())).thenThrow(exception);

        mockMvc.perform(get("/api/agents/Gekko"))
                .andExpect(status().is(status))
                .andExpect(jsonPath("$.error").value(expectedDetails))
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.details").value(errorMessage));
    }


    @Test
    void create_Sucess() throws Exception{
        doNothing().when(agentService).create(agentDto);

        mockMvc.perform(post("/api/agents")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(agentDto)))
                .andExpect(status().isCreated());

    }

    @ParameterizedTest
    @CsvSource({
            "400, 'Erro de validação', 'Ocorreu um erro ao criar o agent.', 'Erro de validação'",
            "404, 'Agentes não encontrados', 'Ocorreu um erro ao criar o agent.', 'Agentes não encontrados'",
            "500, 'Internal Server Error', 'Ocorreu um erro ao criar o agent.', 'Internal Server Error'"
    })
    void create_Exception(int status, String errorMessage, String expectedMessage, String expectedDetails) throws Exception {
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.valueOf(status), errorMessage);
        doThrow(exception).when(agentService).create(agentDto);

        mockMvc.perform(post("/api/agents")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(agentDto)))
                .andExpect(status().is(status))
                .andExpect(jsonPath("$.error").value(expectedDetails))
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.details").value(errorMessage));
    }

    @Test
    void update_Sucess() throws Exception{
        doNothing().when(agentService).update(agentUpdateDto, uuid);

        mockMvc.perform(put("/api/agents/any")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(agentUpdateDto)))
                .andExpect(status().isNoContent());

    }

    @ParameterizedTest
    @CsvSource({
            "400, 'Erro de validação', 'Ocorreu um erro ao atualizar o agent.', 'Erro de validação'",
            "404, 'Agentes não encontrados', 'Ocorreu um erro ao atualizar o agent.', 'Agentes não encontrados'",
            "500, 'Internal Server Error', 'Ocorreu um erro ao atualizar o agent.', 'Internal Server Error'"
    })
    void update_Exception(int status, String errorMessage, String expectedMessage, String expectedDetails) throws Exception {
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.valueOf(status), errorMessage);
        doThrow(exception).when(agentService).update(any(AgentUpdateDto.class), anyString());

        mockMvc.perform(put("/api/agents/any")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(agentUpdateDto)))
                .andExpect(status().is(status))
                .andExpect(jsonPath("$.error").value(expectedDetails))
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.details").value(errorMessage));
    }


    @Test
    void partialUpdate() {
    }

    @Test
    void delete() {
    }
}