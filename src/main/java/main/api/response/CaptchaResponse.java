package main.api.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaptchaResponse {
    private String secret;
    private String image;
}
