package main.controller;

import main.api.response.*;
import main.service.ApiAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiAuthController {
    private final ApiAuthService apiAuthService;
    private final SettingsResponse settingsResponse;

    public ApiAuthController(ApiAuthService apiAuthService, SettingsResponse settingsResponse) {
        this.apiAuthService = apiAuthService;
        this.settingsResponse = settingsResponse;
    }

    @GetMapping("/api/auth/check")
    private AuthCheckResponse apiAuthCheck() {
        return apiAuthService.getAuthCheckResponse();
    }

    @GetMapping("/api/auth/captcha")
    private AuthCaptchaResponse captchaCheck() {return apiAuthService.getCaptcha();
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<RegResponse> register(@RequestBody RegRequest regRequest) {
        if (!settingsResponse.isMultiuserMode()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(apiAuthService.getRegResponse(regRequest));

//        public int set(){return apiAuthService.setNewUser();
    }

}
