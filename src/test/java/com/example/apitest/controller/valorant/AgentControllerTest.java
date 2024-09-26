package com.example.apitest.controller.valorant;

import com.example.apitest.service.valorant.AgentService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AgentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AgentService agentService;

    @Test
    void getAll_Sucess() throws Exception{
        mockMvc.perform(get("/api/agents"))
                .andExpect(status().isOk());
    }

    @Test
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
    void getByName() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void partialUpdate() {
    }

    @Test
    void delete() {
    }
}