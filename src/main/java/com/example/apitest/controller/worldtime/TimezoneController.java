package com.example.apitest.controller.worldtime;

import com.example.apitest.dto.worldtime.TimezoneDto;
import com.example.apitest.service.worldtime.TimezoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * O controller de timezone
 */
@RestController
@RequestMapping("/api/timezone")
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
    public TimezoneController(TimezoneService timezoneService) {this.timezoneService = timezoneService;}

    /**
     * Rota Get /api/timezone que retorna uma lista das timezones disponiveis
     *
     * @return Lista das timezones
     */
    @GetMapping
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
        return new ResponseEntity<>(timezoneDto, HttpStatus.OK);
    }

}
