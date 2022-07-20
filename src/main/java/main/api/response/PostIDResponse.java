package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import main.model.Tags;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostIDResponse {

    private int id;
    private long timestamp;
    private boolean active;

    private UserExternal user;

    private String title;
    private String text;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;

    private List<PostCommentsExternal> comments;


    private List<String> tags;

}
