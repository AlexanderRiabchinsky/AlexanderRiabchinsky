package main.controller;

import main.api.response.*;
import main.service.ApiGeneralService;
import org.springframework.web.bind.annotation.GetMapping;
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
    private SettingsResponse settings() {
        return generalService.getGlobalSettings();
    }

    @GetMapping("/api/init")
    private InitResponse init() {
        return initResponse;
    }

    @GetMapping("/api/tag")
    private TagResponse apiTag() {
        return generalService.getTagResponse();
    }

    @GetMapping("/api/calendar")
    private CalendarResponse calendar() {return generalService.getCalendarResponse();
    }
}
