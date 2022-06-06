package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.model.UserAuthCheck;

import java.util.List;

@Data
public class AuthCheckResponse {
    private boolean result;
    private UserAuthCheck user;

}

