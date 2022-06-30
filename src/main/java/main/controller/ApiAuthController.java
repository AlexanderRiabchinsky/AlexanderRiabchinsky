package main.controller;

import main.api.response.AuthCaptchaResponse;
import main.api.response.AuthCheckResponse;
import main.repositories.UsersRepository;
import main.service.ApiAuthCheckService;
import main.service.CaptchaService;
import main.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiAuthController {
    private final ApiAuthCheckService apiAuthCheckService;
    private final CaptchaService captchaService;
    private final UserRegistrationService userRegistrationService;

    public ApiAuthController(ApiAuthCheckService apiAuthCheckService, CaptchaService captchaService, UserRegistrationService userRegistrationService) {
        this.apiAuthCheckService = apiAuthCheckService;
        this.captchaService = captchaService;
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping("/api/auth/check")
    private AuthCheckResponse apiAuthCheck() {
        return apiAuthCheckService.getAuthCheckResponse();
    }

    @GetMapping("/api/auth/captcha")
    private AuthCaptchaResponse captchaCheck() {return captchaService.getCaptcha();
    }

    @PostMapping("/api/auth/register")
    public int set(){return userRegistrationService.setNewUser();
    }

}
