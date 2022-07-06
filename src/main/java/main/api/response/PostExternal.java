package main.api.response;

import lombok.Data;
import main.model.Posts;
import main.api.response.UserExternal;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class PostExternal extends Posts {
    @Id
    private int id;
    private LocalDateTime timestamp;
    private UserExternal user;
    private String title;
    private String announce;
    private int likeCount;
    private int dislikeCount;
    private int commentCount;
    private int viewCount;
    private boolean active;
}
