package main.service;

import main.api.response.*;
import main.model.CaptchaCodes;
import main.model.Users;
import main.repositories.CaptchaCodesRepository;
import main.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApiAuthService {
    @Autowired
    private UsersRepository usersRepository;
    private CaptchaCodesRepository captchaCodesRepository;

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
    public RegResponse getRegResponse(RegRequest regRequest) {
        RegResponse regResponse = new RegResponse();
        Map<String, String> errors = new HashMap<>();
        List<String> emails = usersRepository.findAll().stream()
                .map(Users::getEmail).collect(Collectors.toList());
        String email = regRequest.getE_mail();
        if (emails.contains(email)) {
            errors.put("email", "Этот e-mail уже зарегистрирован");
        }
        String name = regRequest.getName();
        if (name.length() > MAX_LENGTH || !name.matches("[А-Яа-яA-Za-z]+([А-Яа-яA-Za-z\\s]+)?")) {
            errors.put("name", "Имя указано неверно");
        }
        String password = regRequest.getPassword();
        if (password.length() < PASSWORD_LENGTH) {
            errors.put("password", "Пароль короче 6-ти символов");
        }
        String captcha = regRequest.getCaptcha();
        String secret = regRequest.getCaptcha_secret();
        Optional<CaptchaCodes> optionalCaptcha = captchaCodesRepository.findCaptchaBySecretCode(secret);
        if (optionalCaptcha.isPresent()) {
            if (!optionalCaptcha.get().getCode().equals(captcha)) {
                errors.put("captcha", "Код с картинки введён неверно");
            }
        } else {
            errors.put("captcha", "код устарел");
        }
        if (errors.isEmpty()) {
            regResponse.setResult(true);
            Users user = new Users();
            user.setIsModerator((byte) 0);
            user.setRegTime((java.sql.Date) new Date());
            user.setName(name);
            user.setEmail(email);
            user.setPassword(BCRYPT.encode(password));
            usersRepository.save(user);
        } else {
            regResponse.setResult(false);
            regResponse.setErrors(errors);
        }
        return regResponse;
    }
}
