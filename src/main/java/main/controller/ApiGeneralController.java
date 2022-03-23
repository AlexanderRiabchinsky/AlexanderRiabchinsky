package main.controller;

import main.api.response.*;
import main.service.SettingsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final InitResponse initResponse;
    private final TagResponse tagResponse;
    private final CalendarResponse calendarResponse;

    public ApiGeneralController(SettingsService settingsService, InitResponse initResponse, TagResponse tagResponse, CalendarResponse calendarResponse) {

        this.settingsService = settingsService;
        this.initResponse = initResponse;
        this.tagResponse = tagResponse;
        this.calendarResponse = calendarResponse;
    }

    @GetMapping("/api/settings")
//    private ResponseEntity<SettingsResponse>settings(){
//        return new  ResponseEntity<>(settingsService.getGlobalSettings(), HttpStatus.BAD_REQUEST);
//    }
    private SettingsResponse settings(){
        return settingsService.getGlobalSettings();
    }

    @GetMapping("/api/init")
    private InitResponse init() {
        return initResponse;
    }
    @GetMapping("/api/tag")
    private TagResponse tags(){
        return tagResponse;
    }
    @GetMapping("/api/calendar")
    private CalendarResponse calendar(){return calendarResponse;}
}
