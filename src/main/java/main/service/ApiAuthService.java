package main.service;

import main.api.response.AuthCaptchaResponse;
import main.api.response.AuthCheckResponse;
import main.api.response.UserAuthCheck;
import main.model.Users;
import main.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApiAuthService {
    @Autowired
    private UsersRepository usersRepository;

    public AuthCheckResponse getAuthCheckResponse() {
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
        users.setModerationCount(88);
        users.setSettings(true);
        authCheckResponse.setUser(users);
        return authCheckResponse;
    }
    public AuthCaptchaResponse getCaptcha(){
        AuthCaptchaResponse captchaResponse = new AuthCaptchaResponse();
        captchaResponse.setSecret(captchaResponse.getSecret());
        captchaResponse.setImage(captchaResponse.getImage());
        return captchaResponse;
    }
    public int setNewUser(){
        Users newUser = new Users();
        newUser.setIsModerator(newUser.getIsModerator());
        newUser.setRegTime(newUser.getRegTime());
        newUser.setName(newUser.getName());
        newUser.setEmail(newUser.getEmail());
        newUser.setPassword(newUser.getPassword());
        newUser.setCode(newUser.getCode());
        newUser.setPhoto(newUser.getPhoto());
        usersRepository.save(newUser);
        return newUser.getId();
    }
}
