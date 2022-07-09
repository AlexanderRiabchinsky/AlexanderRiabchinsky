package main.controller;

import main.api.response.*;
import main.service.ApiAuthService;
import main.service.CaptchaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ApiAuthController {
    private final ApiAuthService apiAuthService;
    private final SettingsResponse settingsResponse;
    private final CaptchaService captchaService;

    public ApiAuthController(ApiAuthService apiAuthService, SettingsResponse settingsResponse, CaptchaService captchaService) {
        this.apiAuthService = apiAuthService;
        this.settingsResponse = settingsResponse;
        this.captchaService = captchaService;
    }

    @GetMapping("/api/auth/check")
    public ResponseEntity<AuthCheckResponse> apiAuthCheck() {return ResponseEntity.ok(apiAuthService.getAuthCheckResponse());
    }

    @GetMapping("/api/auth/captcha")
    public ResponseEntity<CaptchaResponse> captchaCheck() {return ResponseEntity.ok(captchaService.getCaptchaCode());
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<RegResponse> register(@RequestBody RegRequest regRequest) {
        if (!settingsResponse.isMultiuserMode()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(apiAuthService.getRegResponse(regRequest));
    }

}
