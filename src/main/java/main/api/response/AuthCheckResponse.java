package main.api.response;

import lombok.Data;

@Data
public class AuthCheckResponse {
    private boolean result;
    private UserExternal user;

}

