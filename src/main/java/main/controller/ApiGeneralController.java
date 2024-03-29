package main.controller;

import main.api.request.ModerationRequest;
import main.api.request.ProfileRequest;
import main.api.request.SetCommentRequest;
import main.api.response.*;
import main.service.ApiGeneralService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<TagResponse> apiTag(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(generalService.getTagResponse(query));
    }

    @GetMapping("/api/calendar")
    public ResponseEntity<CalendarResponse> calendar(@RequestParam(required = false) String year) {
        return ResponseEntity.ok(generalService.getCalendar(year));
    }

    @PreAuthorize("hasAuthority('user:write')")
    @PostMapping(value = "/api/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> image(@RequestParam MultipartFile image) throws IOException {
        if (!generalService.checkImage(image)) {
            return ResponseEntity.badRequest().body(generalService.getImageError(image));
        }
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
        return ResponseEntity.ok(generalService.comment(setCommentRequest, principal));
    }

    @PreAuthorize("hasAuthority('user:moderate')")
    @PostMapping("/api/moderation")
    public ResponseEntity<ResultResponse> moderation(@RequestBody ModerationRequest moderationRequest,
                                                     Principal principal) {
        return ResponseEntity.ok(generalService.moderation(moderationRequest, principal));
    }

    @PreAuthorize("hasAuthority('user:write')")
    @PostMapping(value = "/api/profile/my", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<RegResponse> editProfile(
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "removePhoto", required = false) int removePhoto,
            Principal principal) throws IOException {
        System.out.println(removePhoto);
        return ResponseEntity
                .ok(generalService.editImage(principal, photo, name, email, password, removePhoto));
    }

    @PreAuthorize("hasAuthority('user:write')")
    @PostMapping("/api/profile/my")
    public ResponseEntity<RegResponse> editProfile(@RequestBody ProfileRequest profileRequest,
                                                   Principal principal) throws IOException {
        return ResponseEntity.ok(generalService.profile(profileRequest, principal));
    }

    @PreAuthorize("hasAuthority('user:write')")
    @GetMapping("/api/statistics/my")
    public ResponseEntity<StatisticsResponse> statMy(Principal principal) {
        return ResponseEntity.ok(generalService.statistics(principal));
    }

    @GetMapping("/api/statistics/all")
    public ResponseEntity<StatisticsResponse> statAll(Principal principal) {
        if (!generalService.statisticIsPublic() && (!generalService.checkIfModerator(principal))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(generalService.statistics(null));
    }

    @PreAuthorize("hasAuthority('user:moderate')")
    @PutMapping("/api/settings")
    public ResponseEntity settings(@RequestBody SettingsResponse request) {
        generalService.settings(request);
        return ResponseEntity.ok().body(null);
    }
}
