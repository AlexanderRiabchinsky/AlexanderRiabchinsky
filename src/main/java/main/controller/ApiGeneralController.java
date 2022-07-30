package main.controller;

import main.api.request.ModerationRequest;
import main.api.request.ProfileRequest;
import main.api.request.SetCommentRequest;
import main.api.response.*;
import main.service.ApiGeneralService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

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
    public ResponseEntity<CalendarResponse> calendar(@RequestParam  (required = false) String year) {return ResponseEntity.ok(generalService.getCalendar(year));
    }

    @PreAuthorize("hasAuthority('user:write')")
    @PostMapping(value = "/api/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> image(@RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.ok(generalService.saveImage(image));
    }

    @PreAuthorize("hasAuthority('user:write')")
    @PostMapping("/api/comment")
    public ResponseEntity<?> setComment(@RequestBody SetCommentRequest setCommentRequest,
                                                 Principal principal) {
        if (!generalService.checkComment(setCommentRequest).isResult()) {
            return ResponseEntity.badRequest()
                    .body(generalService.getErrorResponse(setCommentRequest));
        }
        return ResponseEntity.ok(generalService.comment(setCommentRequest,principal));
    }

    @PreAuthorize("hasAuthority('user:moderate')")
    @PostMapping("/api/moderation")
    public ResponseEntity<ResultResponse> moderation(@RequestBody ModerationRequest moderationRequest,
                                                     Principal principal) {
        return ResponseEntity.ok(generalService.moderation(moderationRequest,principal));
    }
    @PreAuthorize("hasAuthority('user:write')")
    @PostMapping(value = "/api/profile/my", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RegResponse> updateProfile(@RequestParam MultipartFile photo,
                                                     @RequestBody ProfileRequest profileRequest,
                                                     Principal principal) throws IOException {
        return ResponseEntity.ok(generalService.profile(photo,profileRequest,principal));}

    @PreAuthorize("hasAuthority('user:write')")
    @GetMapping("/api/statistics/my")
    public ResponseEntity<StatisticsResponse> statMy() {
        return ResponseEntity.ok(generalService.statisticsMy());
    }

    @GetMapping("/api/statistics/all")
    public ResponseEntity<StatisticsResponse> statAll() {
        return ResponseEntity.ok(generalService.statisticsAll());
    }

    @PreAuthorize("hasAuthority('user:moderate')")
    @PostMapping("/api/settings")
    public ResponseEntity<ResultResponse> settings(@RequestBody SettingsResponse request,
                                                     Principal principal) {
        return ResponseEntity.ok(generalService.settings(request,principal));
    }
}
