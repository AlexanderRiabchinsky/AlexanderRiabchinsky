package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import main.model.Users;

import javax.persistence.Id;

@Getter @Setter
public class UserExternal {
    @Id
    private int id;
    private String name;
    private String photo;
    @JsonProperty("e_mail")
    private String email;
    private boolean moderation;
    @JsonProperty("moderation_count")
    private long moderationCount;
    private boolean settings;
}
