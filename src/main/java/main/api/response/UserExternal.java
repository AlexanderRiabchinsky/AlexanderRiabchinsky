package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
public class UserExternal {
    @Id
    private int id;
    @Column(name = "reg_time")
    private long regTime;
    private String password;
    private String name;
    private String photo;
    @JsonProperty("e_mail")
    private String email;
    private boolean moderation;
    @JsonProperty("moderation_count")
    private long moderationCount;
    private boolean settings;
}
