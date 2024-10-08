package com.example.apitest.controller.worldtime;

import com.example.apitest.dto.worldtime.TimezoneCreateDto;
import com.example.apitest.dto.worldtime.TimezoneDto;
import com.example.apitest.dto.worldtime.TimezoneUpdateDto;
import com.example.apitest.service.worldtime.TimezoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.List;

/**
 * O controller de timezone
 */
@RestController
@RequestMapping("/api/timezone")
@Tag(name = "Timezone", description = "Operações para a model de timezones")
public class TimezoneController {

    /**
     * Instancia da service de timezone
     */
    @Autowired
    public TimezoneService timezoneService;

    /**
     * O Construtor
     *
     * @param timezoneService
     */
    public TimezoneController(TimezoneService timezoneService) {
        this.timezoneService = timezoneService;
    }

    /**
     * Rota Get /api/timezone que retorna uma lista das timezones disponiveis
     *
     * @return Lista das timezones
     */
    @GetMapping
    @Operation(summary = "Listar possiveis timezones",
            description = "Retorna a lista das timezones disponiveis na api externa")
    public List<String> getTimezones() {
        return timezoneService.getTimezones();
    }

    /**
     * Rota Get que retorna a timezone escolhida e salva no banco de dados
     *
     * @param zone
     * @param zone2
     * @param zone3
     * @return timezone selecionada
     */
    @RequestMapping(value = {
            "/{zone}",
            "/{zone}/{zone_2}",
            "/{zone}/{zone_2}/{zone_3}"
    }, method = RequestMethod.GET)
    @Operation(summary = "Pegar timezone externa",
            description = "Retorna a timezone escolhida da api externa e salva a timezone no banco de dados")
    public ResponseEntity<TimezoneDto> getTimezone(
            @PathVariable("zone") String zone,
            @PathVariable(value = "zone_2", required = false) String zone2,
            @PathVariable(value = "zone_3", required = false) String zone3) {


        String fullZone = zone;
        if (zone2 != null) {
            fullZone += "/" + zone2;
        }
        if (zone3 != null) {
            fullZone += "/" + zone3;
        }

        // Chama o serviço com a zona completa
        TimezoneDto timezoneDto = timezoneService.getTimezone(fullZone);

        if (timezoneDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Timezone não encontrada");
        }

        return new ResponseEntity<>(timezoneDto, HttpStatus.OK);
    }

    /**
     * Rota para criar uma timezone no banco de dados com base no zoneid recebido no body da requisição
     *
     * @param timezoneCreateDto
     * @return HttpStatus Created
     */
    @PostMapping
    @Operation(summary = "Criar nova timezone",
            description = "Cria uma nova timezone no banco de dados com base no ZoneId recebido no body")
    public ResponseEntity<Void> createTimezone(@RequestBody TimezoneCreateDto timezoneCreateDto) {
        try {

            ZoneId zoneId = ZoneId.of(timezoneCreateDto.getTimezone());

            timezoneService.createTimezone(timezoneCreateDto);

            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (DateTimeException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Timezone invalida");
        }

    }

    /**
     * Rota para atualizar por id uma timezone para outra
     *
     * @param timezoneId
     * @param timezoneUpdateDto
     * @return No_content
     */
    @PutMapping("/{timezoneId}")
    @Operation(summary = "Atualizar timezone",
            description = "Atualiza uma timezone existente no banco de dados com base no ID e nos novos dados enviados")
    public ResponseEntity<Void> updateTimezone(@PathVariable("timezoneId") Long timezoneId,
                                               @RequestBody TimezoneUpdateDto timezoneUpdateDto) {
        try {

            ZoneId zoneId = ZoneId.of(timezoneUpdateDto.getTimezone());

            timezoneService.updateTimezone(timezoneId, timezoneUpdateDto);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (DateTimeException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Timezone invalida");
        }

    }

    /**
     * Rota que deleta timezone pelo id
     *
     * @param timezoneId
     * @return No_content
     */
    @DeleteMapping("/{timezoneId}")
    @Operation(summary = "Deletar timezone",
            description = "Deleta uma timezone existente no banco de dados com base no ID fornecido")
    public ResponseEntity<Void> deleteTimezone(@PathVariable("timezoneId") Long timezoneId) {

        timezoneService.deleteTimezone(timezoneId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Rota que retorna todas as timezones do banco de dados
     *
     * @return lista de timezones
     */
    @GetMapping("/local")
    @Operation(summary = "Listar timezones locais",
            description = "Retorna a lista de todas as timezones salvas no banco de dados")
    public ResponseEntity<List<TimezoneDto>> getAllLocalTimezone() {
        List<TimezoneDto> timezones = timezoneService.getAll();
        return new ResponseEntity<>(timezones, HttpStatus.OK);
    }

    /**
     * Rota que pega timezone do banco de dados pelo id
     *
     * @param timezoneId
     * @return timezone
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/local/{timezoneId}")
    @Operation(summary = "Pegar timezone local por ID",
            description = "Retorna uma timezone específica do banco de dados com base no ID fornecido")
    public TimezoneDto getLocalTimezoneById(@PathVariable("timezoneId") Long timezoneId) {

        return timezoneService.getById(timezoneId);

    }

}
