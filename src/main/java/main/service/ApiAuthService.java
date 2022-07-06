package main.service;

import main.api.response.*;
import main.model.CaptchaCodes;
import main.model.Users;
import main.repositories.CaptchaCodesRepository;
import main.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApiAuthService {
    public static final int PASSWORD_LENGTH = 6;
    public static final int MAX_LENGTH = 255;
    MapperService mapperService;
    @Autowired
    private UsersRepository usersRepository;
    private CaptchaCodesRepository captchaCodesRepository;

    public AuthCheckResponse getAuthCheckResponse() {
        AuthCheckResponse authCheckResponse = new AuthCheckResponse();
        Integer id = 1;

        Optional<Users> user = usersRepository.findById(id);
        authCheckResponse.setResult(user.get().getIsModerator()==1);
        authCheckResponse.setUser(mapperService.convertUserToCheck(user.get()));
//        AuthCheckResponse user = new AuthCheckResponse();
//        UserExternal users = new UserExternal();
//        users.setId(id);
//        users.setName(usersRepository.findNameById(id));
//        users.setPhoto(usersRepository.findPhotoById(id));
//        users.setEmail(usersRepository.findEmailById(id));
//        users.setModeration(true);
//        users.setModerationCount(87);
//        users.setSettings(true);
//        authCheckResponse.setUser(users);
        return authCheckResponse;
    }
    public AuthCaptchaResponse getCaptcha(){
        AuthCaptchaResponse captchaResponse = new AuthCaptchaResponse();
        captchaResponse.setSecret(captchaResponse.getSecret());
        captchaResponse.setImage(captchaResponse.getImage());
        return captchaResponse;
    }

    public RegResponse getRegResponse(RegRequest regRequest) {
        RegResponse regResponse = new RegResponse();
        Map<String, String> errors = new HashMap<>();
        List<String> emails = usersRepository.findAll().stream()
                .map(Users::getEmail).collect(Collectors.toList());
        String email = regRequest.getEmail();
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
        String secret = regRequest.getCaptchaSecret();
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
            user.setPassword("0000");//Доделать после 4 этапа!!!BCRYPT.encode(password));
            usersRepository.save(user);
        } else {
            regResponse.setResult(false);
            regResponse.setErrors(errors);
        }
        return regResponse;
    }
}
