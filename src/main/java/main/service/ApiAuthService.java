package main.service;

import lombok.AllArgsConstructor;
import main.api.response.*;
import main.model.CaptchaCodes;
import main.model.User;
import main.repositories.CaptchaCodesRepository;
import main.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApiAuthService {
    public static final int PASSWORD_LENGTH = 6;
    public static final int MAX_LENGTH = 255;
    public MapperService mapperService;

    private final UserRepository userRepository;
    private CaptchaCodesRepository captchaCodesRepository;
    public static final PasswordEncoder BCRYPT = new BCryptPasswordEncoder(12);

    public AuthCheckResponse getLoginResponse(String email) {
        AuthCheckResponse authCheckResponse = new AuthCheckResponse();
        main.model.User currentUser  = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        UserExternal userResponse = new UserExternal();
        userResponse.setEmail(currentUser.getEmail());
        userResponse.setName(currentUser.getName());
        userResponse.setModeration(currentUser.getIsModerator()==1);
        userResponse.setId(currentUser.getId());

        authCheckResponse.setResult(true);
        authCheckResponse.setUser(userResponse);

        return authCheckResponse;
    }
    public ResultResponse getLogoutResponse(){
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setResult(true);
        return resultResponse;
    }

    public RegResponse getRegResponse(RegRequest regRequest) {
        RegResponse regResponse = new RegResponse();
        Map<String, String> errors = new HashMap<>();
        List<String> emails = userRepository.findAll().stream()
                .map(User::getEmail).collect(Collectors.toList());
        String email = regRequest.getEmail();
        if (emails.contains(email)) {
            errors.put("email", "???????? e-mail ?????? ??????????????????????????????");
        }
        String name = regRequest.getName();
        if (name.length() > MAX_LENGTH || !name.matches("[??-????-??A-Za-z]+([??-????-??A-Za-z\\s]+)?")) {
            errors.put("name", "?????? ?????????????? ??????????????");
        }
        String password = regRequest.getPassword();
        if (password.length() < PASSWORD_LENGTH) {
            errors.put("password", "???????????? ???????????? 6-???? ????????????????");
        }
        String captcha = regRequest.getCaptcha();
        String secret = regRequest.getCaptchaSecret();
        Optional<CaptchaCodes> optionalCaptcha = captchaCodesRepository.findCaptchaBySecretCode(secret);
        if (optionalCaptcha.isPresent()) {
            if (!optionalCaptcha.get().getCode().equals(captcha)) {
                errors.put("captcha", "?????? ?? ???????????????? ???????????? ??????????????");
            }
        } else {
            errors.put("captcha", "?????? ??????????????");
        }
        if (errors.isEmpty()) {
            regResponse.setResult(true);
            User user = new User();
            user.setIsModerator((byte) 0);
            user.setRegTime( (new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            user.setName(name);
            user.setEmail(email);
            user.setPassword(BCRYPT.encode(password));
            userRepository.save(user);
        } else {
            regResponse.setResult(false);
            regResponse.setErrors(errors);
        }
        return regResponse;
    }
}
