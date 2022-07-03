package main.api.response;

import lombok.Data;
import main.model.Posts;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class PostExternal2 extends Posts {
    @Id
    private int id;
    private int isActive;
    private LocalDateTime timestamp;
    private UserExternal user;
    private String title;
    private String announce;
    private int likeCount;
    private int dislikeCount;
    private int commentCount;
    private int viewCount;

    public int setActive(boolean b) {
        if(b){return 1;}
        return -1;
    }
}
