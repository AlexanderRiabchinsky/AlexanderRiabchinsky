package main.api.response;

import lombok.Data;

@Data
public class CaptchaResponse {
    String secret;
    String image;
}
