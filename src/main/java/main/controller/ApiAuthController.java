package main.controller;

import main.DBConnection;
import main.api.response.AuthCaptchaResponse;
import main.api.response.AuthCheckResponse;
import main.model.Users;
import main.model.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiAuthController {
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/api/auth/check")
    public AuthCheckResponse authCheck() {
        List<Users> usersIterable = usersRepository.findAll();
        AuthCheckResponse authCheckResponse = new AuthCheckResponse();
        authCheckResponse.setResult(true);
        AuthCheckResponse.Users user = new AuthCheckResponse.Users();
        user.setId(1);
        user.setName("Vasya");
        user.setPhoto("not yet");
        user.setEmail("a@a.ru");
        user.setModeration(false);
        user.setModerationCount(0);
        user.setSettings(true);
        ArrayList<AuthCheckResponse.Users> users = new ArrayList<>();
        users.add(user);
        authCheckResponse.setUser(users);
        return authCheckResponse;
    }

    @GetMapping("/api/auth/captcha")
    public AuthCaptchaResponse captchaCheck() {
        AuthCaptchaResponse authCaptchaResponse = new AuthCaptchaResponse();
        return authCaptchaResponse;
    }

    @PostMapping("/api/auth/register")
    public int add(Users user) throws SQLException {
            Users newUser = usersRepository.save(user);
  //          DBConnection.insertUser(user.getId(), user.getIs_moderator(), user.getReg_time(), user.getName(), user.getEmail(), user.getPassword(), user.getCode(), user.getPhoto());
            return newUser.getId();
    }

}
