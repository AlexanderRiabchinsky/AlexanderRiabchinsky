package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthCaptchaResponse {
    private String secret;
    private String image;
}
