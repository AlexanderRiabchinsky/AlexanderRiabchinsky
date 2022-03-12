package main.controller;

import main.api.response.InitResponse;
import main.api.response.PostResponse;
import main.api.response.SettingsResponse;
import main.api.response.TagResponse;
import main.service.SettingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final InitResponse initResponse;
    private final TagResponse tagResponse;

    public ApiGeneralController(SettingsService settingsService, InitResponse initResponse, TagResponse tagResponse) {

        this.settingsService = settingsService;
        this.initResponse = initResponse;
        this.tagResponse = tagResponse;
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
}
