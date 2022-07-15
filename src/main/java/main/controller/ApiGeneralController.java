package main.controller;

import main.api.response.*;
import main.service.ApiGeneralService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGeneralController {

    private final ApiGeneralService generalService;
    private final InitResponse initResponse;

    public ApiGeneralController(ApiGeneralService generalService, InitResponse initResponse) {

        this.generalService = generalService;
        this.initResponse = initResponse;
    }


    @GetMapping("/api/settings")
    public SettingsResponse settings() {
        return generalService.getGlobalSettings();
    }

    @GetMapping("/api/init")
    public InitResponse init() {
        return initResponse;
    }

    @GetMapping("/api/tag")
    public ResponseEntity<TagResponse> apiTag(@RequestParam (required = false) String query) {return ResponseEntity.ok(generalService.getTagResponse(query));}

    @GetMapping("/api/calendar")
    public ResponseEntity<CalendarResponse> calendar(@RequestParam  int year) {return ResponseEntity.ok(generalService.getCalendar(year));
    }
}
