package com.example.apitest.service.worldtime;

import com.example.apitest.dto.worldtime.TimezoneCreateDto;
import com.example.apitest.dto.worldtime.TimezoneDto;
import com.example.apitest.model.worldtime.Timezone;
import com.example.apitest.repository.worldtime.TimezoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriUtils;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.temporal.IsoFields;
import java.util.List;

/**
 * A service para Timezone
 */
@Service
public class TimezoneService {

    /**
     * Instancia de webclient para realizar as chamadas de api externa para a api de timezone
     */
    private final WebClient webClient;

    @Autowired
    private TimezoneRepository timezoneRepository;

    /**
     * O Construtor
     *
     * @param webClient
     */
    public TimezoneService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * função que solicita a api externa as timezones disponiveis
     *
     * @return Lista de strings de timezones disponiveis
     */
    public List<String> getTimezones() {
        Mono<List<String>> timezonesMono = webClient.get()
                .uri("https://worldtimeapi.org/api/timezone")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                });
        return timezonesMono.block();
    }

    public TimezoneDto getTimezone(String zone) {


        Mono<TimezoneDto> timezoneMono = webClient.get()
                .uri("https://worldtimeapi.org/api/timezone/" + zone)
                .retrieve()
                .bodyToMono(TimezoneDto.class);

        TimezoneDto timezoneDto = timezoneMono.block();
        Timezone timezone = new Timezone();

        timezone.setTimezone(timezoneDto.getTimezone());
        timezone.setDatetime(timezoneDto.getDatetime());
        timezone.setUnixtime(timezoneDto.getUnixtime());
        timezone.setUtc_datetime(timezoneDto.getUtc_datetime());
        timezone.setUtc_offset(timezoneDto.getUtc_offset());
        timezone.setDay_of_week(timezoneDto.getDay_of_week());
        timezone.setDay_of_year(timezoneDto.getDay_of_year());
        timezone.setWeek_number(timezoneDto.getWeek_number());

        timezoneRepository.save(timezone);
        return timezoneDto;
    }

    /**
     * Cria no banco de dados um novo timezone usando o zoneId e as funções de dateTime do java
     *
     * @param timezoneCreateDto
     */
    public void createTimezone(TimezoneCreateDto timezoneCreateDto) {

        Timezone timezone = new Timezone();

        ZoneId zoneId = ZoneId.of(timezoneCreateDto.getTimezone());
        ZoneOffset offset = zoneId.getRules().getOffset(Instant.now());
        String utcOffset = offset.getId();

        timezone.setTimezone(timezoneCreateDto.getTimezone());
        timezone.setUtc_offset(utcOffset);
        timezone.setDay_of_week(LocalDate.now(zoneId).getDayOfWeek().getValue());
        timezone.setDay_of_year(LocalDate.now(zoneId).getDayOfYear());
        OffsetDateTime datetime = OffsetDateTime.now(zoneId);
        timezone.setDatetime(datetime);
        timezone.setUtc_datetime(datetime.withOffsetSameInstant(ZoneOffset.UTC));
        timezone.setUnixtime(Instant.now().getEpochSecond());
        timezone.setWeek_number(LocalDate.now(zoneId).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));

        timezoneRepository.save(timezone);
    }
}
