package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.model.PostComments;
import main.model.Tags;
import main.model.Users;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Data
//@Component
public class PostIDResponse {
    @JsonProperty("id")
    private int id;
    @JsonProperty("timestamp")
    private Date timestamp;
    @JsonProperty("active")
    private boolean active;
    @JsonProperty("user")
    private Users user;

    @Data
    private class Users {
        @JsonProperty("id")
        private int id;
        @JsonProperty("name")
        private String name;
    }

    @JsonProperty("title")
    private String title;
    @JsonProperty("text")
    private String text;
    @JsonProperty("likeCount")
    private int likeCount;
    @JsonProperty("dislikeCount")
    private int dislikeCount;
    @JsonProperty("viewCount")
    private int viewCount;
    @JsonProperty("comments")
    private List<PostComments> comments;

    @Data
    private static class PostComments {
        @JsonProperty("id")
        private int id;
        @JsonProperty("timestamp")
        private String timestamp;
        @JsonProperty("text")
        private String text;
        @JsonProperty("user")
        private Users user;

        @Data
        private static class Users {
            @JsonProperty("id")
            private int id;
            @JsonProperty("name")
            private String name;
            @JsonProperty("photo")
            private String photo;
        }
    }

    @JsonProperty("tags")
    private List<Tags> tags;

}
