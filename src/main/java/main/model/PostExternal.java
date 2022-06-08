package main.model;

import lombok.Data;
import main.api.response.PostResponse;

import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

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
}
