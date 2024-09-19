package com.example.apitest.controller.worldtime;

import com.example.apitest.service.worldtime.TimezoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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

}
