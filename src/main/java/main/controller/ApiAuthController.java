package main.controller;

import main.api.response.AuthCaptchaResponse;
import main.api.response.AuthCheckResponse;
import main.api.response.UserAuthCheck;
import main.model.Users;
import main.repositories.UsersRepository;
import main.service.ApiAuthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Optional;

@RestController
public class ApiAuthController {
    private final ApiAuthCheckService apiAuthCheckService;
    @Autowired
    private UsersRepository usersRepository;

    public ApiAuthController(ApiAuthCheckService apiAuthCheckService) {
        this.apiAuthCheckService = apiAuthCheckService;
    }

    @GetMapping("/api/auth/check")
    private AuthCheckResponse apiAuthCheck() {
        return apiAuthCheckService.getAuthCheckResponse();
    }

    @GetMapping("/api/auth/captcha")
    public AuthCaptchaResponse captchaCheck() {
        AuthCaptchaResponse authCaptchaResponse = new AuthCaptchaResponse();
        return authCaptchaResponse;
    }

    @PostMapping("/api/auth/register")
    public int add(Users user) throws SQLException {
            Users newUser = usersRepository.save(user);
            return newUser.getId();
    }

}
