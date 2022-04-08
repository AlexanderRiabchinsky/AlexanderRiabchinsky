package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.model.Posts;
import main.model.Users;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
//@Component
public class PostSearchResponse {
    @JsonProperty("count")
    private int count;
    @JsonProperty("posts")
    private List<Posts> postsList;

    @Data
    private class Posts {
        @JsonProperty("id")
        private int id;
        @JsonProperty("timestamp")
        private String timestamp;
        @JsonProperty("user")
        private Users user;

        @Data
        public class Users {
            @JsonProperty("id")
            private int id;
            @JsonProperty("name")
            private String name;
        }

        @JsonProperty("title")
        private String title = "Заголовок Поста";
        @JsonProperty("announce")
        private String announce = "Текст анонса поста без HTML-тэгов";
        @JsonProperty("likeCount")
        private int likeCount;
        @JsonProperty("dislikeCount")
        private int dislikeCount;
        @JsonProperty("commentCount")
        private int commentCount;
        @JsonProperty("viewCount")
        private int viewCount;
    }
}
