package main.controller;

import main.api.response.AuthCaptchaResponse;
import main.api.response.AuthCheckResponse;
import main.service.ApiAuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiAuthController {
    private final ApiAuthService apiAuthService;

    public ApiAuthController(ApiAuthService apiAuthService) {
        this.apiAuthService = apiAuthService;
    }

    @GetMapping("/api/auth/check")
    private AuthCheckResponse apiAuthCheck() {
        return apiAuthService.getAuthCheckResponse();
    }

    @GetMapping("/api/auth/captcha")
    private AuthCaptchaResponse captchaCheck() {return apiAuthService.getCaptcha();
    }

    @PostMapping("/api/auth/register")
    public int set(){return apiAuthService.setNewUser();
    }

}
