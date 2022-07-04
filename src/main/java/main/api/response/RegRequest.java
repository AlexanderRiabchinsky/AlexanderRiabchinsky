package main.api.response;

import lombok.Data;

@Data
public class RegRequest {
    String e_mail;
    String password;
    String name;
    String captcha;
    String captcha_secret;
}
