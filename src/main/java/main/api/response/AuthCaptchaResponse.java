package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AuthCaptchaResponse {
    @JsonProperty("secret")
    public String secret;
    @JsonProperty("image")
    public String image;
}
