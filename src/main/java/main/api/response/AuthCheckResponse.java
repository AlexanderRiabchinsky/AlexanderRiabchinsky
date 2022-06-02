package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AuthCheckResponse {
 //   @JsonProperty("result")
    private boolean result;
//    @JsonProperty("user")
    private List<Users> user;

    @Data
    public static class Users {
 //       @JsonProperty("id")
        private int id;
 //       @JsonProperty("name")
        private String name;
 //       @JsonProperty("photo")
        private String photo;
 //       @JsonProperty("email")
        private String email;
//        @JsonProperty("moderation")
        private boolean moderation;
//        @JsonProperty("moderationCount")
        private int moderationCount;
//        @JsonProperty("settings")
        private boolean settings;
    }
}

