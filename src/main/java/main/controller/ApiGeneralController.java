package main.controller;

import main.api.response.*;
import main.model.Posts;
import main.repositories.PostsRepository;
import main.model.Tags;
import main.repositories.TagsRepository;
import main.service.ApiAuthCheckService;
import main.service.ApiCalendarService;
import main.service.ApiTagService;
import main.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final InitResponse initResponse;
    private final ApiTagService tagResponse;
    private final ApiCalendarService calendarResponse;

    public ApiGeneralController(SettingsService settingsService, InitResponse initResponse, ApiTagService tagResponse, ApiCalendarService calendarResponse) {

        this.settingsService = settingsService;
        this.initResponse = initResponse;
        this.tagResponse = tagResponse;
        this.calendarResponse = calendarResponse;
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
    private TagResponse apiTag() {
        return tagResponse.getTagResponse();
    }

    @GetMapping("/api/calendar")
    private CalendarResponse calendar() {return calendarResponse.getCalendarResponse();
    }
}
