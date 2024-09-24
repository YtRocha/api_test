package com.example.apitest.controller.worldtime;

import com.example.apitest.dto.worldtime.TimezoneCreateDto;
import com.example.apitest.dto.worldtime.TimezoneDto;
import com.example.apitest.dto.worldtime.TimezoneUpdateDto;
import com.example.apitest.model.worldtime.Timezone;
import com.example.apitest.service.worldtime.TimezoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TimezoneControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private TimezoneService timezoneService;

    @Test
    @DisplayName("Get timezones OK")
    void getTimezones() throws Exception {
        mockMvc.perform(get("/api/timezone"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get timezone OK")
    void getTimezone() throws Exception {
        mockMvc.perform(get("/api/timezone/America/Recife"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Create timezone created")
    void createTimezoneCase1() throws Exception {

        TimezoneCreateDto timezoneCreateDto = new TimezoneCreateDto();
        timezoneCreateDto.setTimezone("America/Recife");

        mockMvc.perform(post("/api/timezone")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(timezoneCreateDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Create timezone bad request")
    void createTimezoneCase2() throws Exception {

        TimezoneCreateDto timezoneCreateDto = new TimezoneCreateDto();
        timezoneCreateDto.setTimezone("America/Reci");

        mockMvc.perform(post("/api/timezone")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(timezoneCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("update timezone No content")
    void updateTimezoneCase1() throws Exception {
        TimezoneCreateDto timezoneCreateDto = new TimezoneCreateDto();
        timezoneCreateDto.setTimezone("America/Recife");

        doNothing().when(timezoneService).updateTimezone(any(), any(TimezoneUpdateDto.class));


        mockMvc.perform(put("/api/timezone/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(timezoneCreateDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("update timezone Bad request")
    void updateTimezoneCase2() throws Exception {
        TimezoneCreateDto timezoneCreateDto = new TimezoneCreateDto();
        timezoneCreateDto.setTimezone("America/Reci");

        doNothing().when(timezoneService).updateTimezone(any(), any(TimezoneUpdateDto.class));


        mockMvc.perform(put("/api/timezone/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(timezoneCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Delete timezone No content")
    void deleteTimezone() throws Exception {
        doNothing().when(timezoneService).deleteTimezone(any());

        mockMvc.perform(delete("/api/timezone/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Get timezone local OK")
    void getAllLocalTimezone() throws Exception {

        List<TimezoneDto> timezoneList = new ArrayList<>();

        when(timezoneService.getAll()).thenReturn(timezoneList);

        mockMvc.perform(get("/api/timezone/local"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("get timezone local por id OK")
    void getLocalTimezoneById() throws Exception {

        TimezoneDto timezoneDto = new TimezoneDto();

        when(timezoneService.getById(any())).thenReturn(timezoneDto);

        mockMvc.perform(get("/api/timezone/local/1"))
                .andExpect(status().isOk());
    }
}