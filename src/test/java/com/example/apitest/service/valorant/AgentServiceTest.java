package com.example.apitest.service.valorant;

import com.example.apitest.dto.valorant.AgentDto;
import com.example.apitest.dto.valorant.AgentUpdateDto;
import com.example.apitest.mapper.valorant.AgentMapper;
import com.example.apitest.model.valorant.Ability;
import com.example.apitest.model.valorant.AbilitySlot;
import com.example.apitest.model.valorant.Agent;
import com.example.apitest.repository.valorant.AgentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentServiceTest {

    @Mock
    private AgentRepository agentRepository;

    @Mock
    private AgentMapper agentMapper;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    @Autowired
    private AgentService agentService;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void getAll_Sucess() {
        when(agentRepository.findAll()).thenReturn(agents);
        when(agentMapper.toDtoList(any())).thenReturn(agentDtos);
        List<AgentDto> result = agentService.getAll();

        assertEquals(agentDtos, result);

    }

    @Test
    void getAll_Exception() {
        when(agentRepository.findAll()).thenThrow(new RuntimeException("Erro ao acessar o banco de dados"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            agentService.getAll();
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Erro ao recuperar agents do banco de dados", exception.getReason());
    }

    @Test
    void getByName_Sucess() {
        when(agentRepository.findByDisplayName(any())).thenReturn(Optional.of(agent_1));
        when(agentMapper.toDto(any())).thenReturn(agentDto);

        AgentDto result = agentService.getByName(any());

        assertEquals(agentDto, result);
    }

    @Test
    void getByName_NotFound() {
        when(agentRepository.findByDisplayName(any())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            agentService.getByName(any());
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Agent não encontrado", exception.getReason());
    }

    @Test
    void create_Sucess() {
        when(agentMapper.toEntity(agentDto)).thenReturn(agent_1);
        when(agentRepository.save(any())).thenReturn(agent_1);

        agentService.create(agentDto);

        verify(agentMapper).toEntity(agentDto);
        verify(agentRepository).save(agent_1);
    }

    @Test
    void create_Exception() {
        when(agentRepository.save(any())).thenThrow(new RuntimeException("Erro ao acessar o banco de dados"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            agentService.create(any());
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Erro ao criar agent no banco de dados", exception.getReason());
    }

    @Test
    void update_Sucess() {
        when(agentRepository.findById(any())).thenReturn(Optional.of(agent_1));
        when(agentMapper.UpdateDtoToEntity(agentUpdateDto)).thenReturn(agent_1);

        agentService.update(agentUpdateDto, uuid);

        verify(agentMapper).UpdateDtoToEntity(agentUpdateDto);
        verify(agentRepository).save(agent_1);
    }

    @Test
    void update_NotFound() {
        when(agentRepository.findById(any())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            agentService.update(any(), uuid);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Agent não encontrado", exception.getReason());

    }

    @Test
    void partialUpdate_Sucess() {
        when(agentRepository.findById(any())).thenReturn(Optional.of(agent_1));

        agentService.partialUpdate(agentUpdateDto, uuid);

        verify(agentRepository).save(agent_1);

    }

    @Test
    void partialUpdate_NotFound() {
        when(agentRepository.findById(any())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            agentService.partialUpdate(any(), uuid);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Agent não encontrado", exception.getReason());

    }

    @Test
    void delete_Sucess() {
        when(agentRepository.findById(any())).thenReturn(Optional.of(agent_1));

        agentService.delete(uuid);

        verify(agentRepository).delete(agent_1);

    }

    @Test
    void delete_NotFound() {
        when(agentRepository.findById(any())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            agentService.delete(uuid);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Agent não encontrado", exception.getReason());

    }
}