package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.model.Tags;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostIDResponse {

    private int id;
    private LocalDateTime timestamp;
    private boolean active;

    private UserExternal user;

    private String title;
    private String text;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;

    private PostCommentsExternal comments;


    private List<Tags> tags;

}
