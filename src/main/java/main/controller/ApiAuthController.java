package main.controller;

import main.api.response.AuthCaptchaResponse;
import main.api.response.AuthCheckResponse;
import main.model.UserAuthCheck;
import main.model.Users;
import main.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Optional;

@RestController
public class ApiAuthController {
    @Autowired
    private UsersRepository usersRepository;
//    @Autowired
//    private UserAuthCheckRepository userAuthCheckRepository;

    @GetMapping("/api/auth/check")
    public AuthCheckResponse authCheck() {
   //     List<UserAuthCheck> usersIterable = userAuthCheckRepository.findAll();
        AuthCheckResponse authCheckResponse = new AuthCheckResponse();
        Integer id = 1;

        Optional<Users> userAuthCheck = usersRepository.findById(id);
        authCheckResponse.setResult(true);
        AuthCheckResponse user = new AuthCheckResponse();
        UserAuthCheck users = new UserAuthCheck();
        users.setId(id);
        users.setName(usersRepository.findNameById(id));
        users.setPhoto(usersRepository.findPhotoById(id));
        users.setEmail(usersRepository.findEmailById(id));
        users.setModeration(true);
        users.setModerationCount(101);
        users.setSettings(true);
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
