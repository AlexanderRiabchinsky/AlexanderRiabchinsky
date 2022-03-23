package main.controller;

import main.DBConnection;
import main.api.response.AuthCaptchaResponse;
import main.api.response.AuthCheckResponse;
import main.model.Users;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Date;

@RestController
public class ApiAuthController {
    private final AuthCheckResponse authCheckResponse;
    private final AuthCaptchaResponse authCaptchaResponse;

    public ApiAuthController(AuthCheckResponse authCheckResponse, AuthCaptchaResponse authCaptchaResponse) {
        this.authCheckResponse = authCheckResponse;
        this.authCaptchaResponse = authCaptchaResponse;
    }
    @GetMapping("/api/auth/check")
    public AuthCheckResponse authCheck(){
        return authCheckResponse;
    }
    @GetMapping("/api/auth/captcha")
    public AuthCaptchaResponse captchaCheck(){return authCaptchaResponse;}
    @PostMapping("/api/auth/register")
    public int add(Users user) throws SQLException {
DBConnection.insertUser(user.getId(), user.getIs_moderator(), user.getReg_time(),user.getName(), user.getEmail(), user.getPassword(), user.getCode(), user.getPhoto());
return user.getId();
    }

}
