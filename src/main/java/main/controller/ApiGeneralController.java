package main.controller;

import main.api.response.*;
import main.service.SettingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final InitResponse initResponse;

    public ApiGeneralController(SettingsService settingsService, InitResponse initResponse/*, TagResponse tagResponse, CalendarResponse calendarResponse*/) {

        this.settingsService = settingsService;
        this.initResponse = initResponse;
    }

    @GetMapping("/api/settings")
    private SettingsResponse settings() {
        return settingsService.getGlobalSettings();
    }

    @GetMapping("/api/init")
    private InitResponse init() {
        return initResponse;
    }

    @GetMapping("/api/tag")
    private TagResponse tags() {
        TagResponse tagResponse = new TagResponse();
        TagResponse.Tags tags = new TagResponse.Tags();
        tags.setName("Lena");
        tags.setWeight(0.9);
        ArrayList<TagResponse.Tags> tags1 = new ArrayList<>();
        tags1.add(tags);
        tagResponse.setTags(tags1);
        return tagResponse;
    }

    @GetMapping("/api/calendar")
    private CalendarResponse calendar() {
        CalendarResponse calendarResponse = new CalendarResponse();
        return calendarResponse;
    }
}
