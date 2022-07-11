package main.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import lombok.AllArgsConstructor;
import main.api.response.CaptchaResponse;
import main.model.CaptchaCodes;
import main.repositories.CaptchaCodesRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
@EnableScheduling
public class CaptchaService {
    public static final long HOUR_IN_MILLISECONDS = 3_600_000;
    private CaptchaCodesRepository captchaCodesRepository;

    public CaptchaResponse getCaptchaCode() {
        CaptchaResponse captchaResponse = new CaptchaResponse();
        Cage cage = new GCage();
        String secret = UUID.randomUUID().toString();
        String code = cage.getTokenGenerator().next();
        String image = "data:image/png;base64, " + Base64.getEncoder().encodeToString(cage.draw(code));
        CaptchaCodes captchaCode = new CaptchaCodes();
        captchaCode.setCode(code);
        captchaCode.setSecretCode(secret);
        captchaCode.setTime(new Date());
        captchaCodesRepository.save(captchaCode);
        captchaResponse.setSecret(secret);
        captchaResponse.setImage(image);
        return captchaResponse;
    }
    @Scheduled(fixedRate = HOUR_IN_MILLISECONDS)
    public void deleteOldCaptchas(){
        captchaCodesRepository.deleteAll(captchaCodesRepository.findOldCaptchas());
    }
}
