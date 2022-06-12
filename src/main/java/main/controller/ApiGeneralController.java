package main.controller;

import main.api.response.*;
import main.model.Posts;
import main.repositories.PostsRepository;
import main.model.Tags;
import main.repositories.TagsRepository;
import main.service.ApiAuthCheckService;
import main.service.ApiTagService;
import main.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiGeneralController {
    @Autowired
    private TagsRepository tagsRepository;
    @Autowired
    private PostsRepository postsRepository;

    private final SettingsService settingsService;
    private final InitResponse initResponse;
    private final ApiTagService tagResponse;

    public ApiGeneralController(SettingsService settingsService, InitResponse initResponse, ApiTagService tagResponse/*, CalendarResponse calendarResponse*/) {

        this.settingsService = settingsService;
        this.initResponse = initResponse;
        this.tagResponse = tagResponse;
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
    private CalendarResponse calendar() {
        CalendarResponse calendarResponse = new CalendarResponse();
        return calendarResponse;
    }
}
