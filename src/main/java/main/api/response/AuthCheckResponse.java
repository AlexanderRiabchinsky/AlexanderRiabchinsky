package main.api.response;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class AuthCheckResponse {
    @JsonProperty("result")
    private boolean result;
    @JsonProperty("user")
    private List<User> user;

    public class User{
        @JsonProperty("id")
        public  int id;
        @JsonProperty("name")
        public String name;
        @JsonProperty("photo")
        public String photo;
        @JsonProperty("email")
        public String email;
        @JsonProperty("moderation")
        public boolean moderation;
        @JsonProperty("moderationCount")
        public int moderationCount;
        @JsonProperty("settings")
        public boolean settings;
    }
}

