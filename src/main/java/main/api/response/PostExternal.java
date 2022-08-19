package main.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class PostExternal {
    @Id
    private int id;
    private long timestamp;
    private UserExternal user;
    private String title;
    private String announce;
    @JsonIgnore
    private String text;
    private int likeCount;
    private int dislikeCount;
    private int commentCount;
    private int viewCount;
    private boolean active;
}
