package main.controller;

import main.api.response.*;
import main.model.Posts;
import main.repositories.PostsRepository;
import main.model.Tags;
import main.repositories.TagsRepository;
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
        List<Tags> tags = tagsRepository.findAll();
        ArrayList<TagExternal> tags1 = new ArrayList<>();
        for (Tags tag:tags) {
        TagExternal tagExt = new TagExternal();

        tagExt.setName("Lena");
        tagExt.setWeight(0.9);
        tags1.add(tagExt);}
        tagResponse.setTags(tags1);
        return tagResponse;
    }

    @GetMapping("/api/calendar")
    private CalendarResponse calendar() {
        CalendarResponse calendarResponse = new CalendarResponse();
        return calendarResponse;
    }
}
