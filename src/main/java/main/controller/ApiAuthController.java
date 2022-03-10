package main.controller;

import main.api.response.AuthCheckResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiAuthController {
    private final AuthCheckResponse authCheckResponse;

    public ApiAuthController(AuthCheckResponse authCheckResponse) {
        this.authCheckResponse = authCheckResponse;
    }
    @GetMapping("/api/auth/check")
    public AuthCheckResponse authCheck(){
        return authCheckResponse;
    }
}
