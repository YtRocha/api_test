package com.example.apitest.service.worldtime;

import com.example.apitest.dto.worldtime.TimezoneCreateDto;
import com.example.apitest.dto.worldtime.TimezoneUpdateDto;
import com.example.apitest.model.worldtime.Timezone;
import com.example.apitest.repository.worldtime.TimezoneRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimezoneServiceTest {

    @Mock
    private TimezoneRepository timezoneRepository;

    @Autowired
    @InjectMocks
    private TimezoneService timezoneService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Deve criar a timezone quando tudo estiver OK")
    void createTimezone() {
        TimezoneCreateDto timezoneCreateDto = new TimezoneCreateDto();

        timezoneCreateDto.setTimezone("America/Recife");

        timezoneService.createTimezone(timezoneCreateDto);

        verify(timezoneRepository, times(1)).save(any());

    }

    @Test
    @DisplayName("Deve atualizar timezone quando tudo estiver ok")
    void updateTimezoneCase1() {
        Long id = 1L;
        TimezoneUpdateDto timezoneUpdateDto = new TimezoneUpdateDto();
        timezoneUpdateDto.setTimezone("America/Recife");


        Timezone timeZone = new Timezone(1L, "", "WET", 1, 1, OffsetDateTime.now(), OffsetDateTime.now(), 1L, 1);


        Optional<Timezone> optionalTimeZone = Optional.of(timeZone);

        when(timezoneRepository.findById(id)).thenReturn(optionalTimeZone);

        timezoneService.updateTimezone(id, timezoneUpdateDto);

        verify(timezoneRepository, times(1)).save(any());

    }

    @Test
    @DisplayName("Deve retornar uma excepction ao tentar atualizar")
    void updateTimezoneCase2() {
        Long id = 1L;
        TimezoneUpdateDto timezoneUpdateDto = new TimezoneUpdateDto();
        timezoneUpdateDto.setTimezone("America/Recife");

        when(timezoneRepository.findById(id)).thenReturn(Optional.empty());

        Exception thrown = Assertions.assertThrows(ResponseStatusException.class, () -> {
            timezoneService.updateTimezone(id, timezoneUpdateDto);
        });

        Assertions.assertEquals("404 NOT_FOUND \"Timezone não encontrado\"", thrown.getMessage());
    }

    @Test
    @DisplayName("Deve deletar timezone quando tudo estiver ok")
    void deleteTimezoneCase1() {

        Long id = 1L;
        TimezoneUpdateDto timezoneUpdateDto = new TimezoneUpdateDto();
        timezoneUpdateDto.setTimezone("America/Recife");

        Timezone timeZone = new Timezone(1L, "", "WET", 1, 1, OffsetDateTime.now(), OffsetDateTime.now(), 1L, 1);

        Optional<Timezone> optionalTimeZone = Optional.of(timeZone);

        when(timezoneRepository.findById(id)).thenReturn(optionalTimeZone);

        timezoneService.deleteTimezone(id);

        verify(timezoneRepository, times(1)).deleteById(any());

    }

    @Test
    @DisplayName("Deve lançar exception ao tentar deletar timezone")
    void deleteTimezoneCase2() {
        Long id = 1L;

        when(timezoneRepository.findById(id)).thenReturn(Optional.empty());

        Exception thrown = Assertions.assertThrows(ResponseStatusException.class, () -> {
            timezoneService.deleteTimezone(id);
        });

        Assertions.assertEquals("404 NOT_FOUND \"Timezone não encontrado\"", thrown.getMessage());

    }

}