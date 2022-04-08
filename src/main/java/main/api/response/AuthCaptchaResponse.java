package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
//@Component
public class AuthCaptchaResponse {
    @JsonProperty("secret")
    private String secret;
    @JsonProperty("image")
    private String image;
}
