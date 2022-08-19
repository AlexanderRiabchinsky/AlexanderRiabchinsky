package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthCheckResponse {
    private boolean result;
    @JsonProperty("user")
    private UserExternal user;

}

