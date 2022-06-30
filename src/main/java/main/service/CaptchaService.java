package main.service;

import main.api.response.AuthCaptchaResponse;
import org.springframework.stereotype.Service;

@Service
public class CaptchaService {
    public AuthCaptchaResponse getCaptcha(){
        AuthCaptchaResponse captchaResponse = new AuthCaptchaResponse();
        captchaResponse.setSecret(captchaResponse.getSecret());
        captchaResponse.setImage(captchaResponse.getImage());
        return captchaResponse;
    }
}
