package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.model.Posts;
import main.model.Users;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class PostSearchResponse {
    @JsonProperty("count")
    public int count;
    @JsonProperty("posts")
    private List<Posts>postsList;

    public class Posts{
        @JsonProperty("id")
        public  int id;
        @JsonProperty("timestamp")
        public  String timestamp;
        @JsonProperty("user")
        private Users user;
        public class Users{
            @JsonProperty("id")
            public  int id;
            @JsonProperty("name")
            public String name;
        }
        @JsonProperty("title")
        public  String title="Заголовок Поста";
        @JsonProperty("announce")
        public  String announce="Текст анонса поста без HTML-тэгов";
        @JsonProperty("likeCount")
        public  int likeCount;
        @JsonProperty("dislikeCount")
        public  int dislikeCount;
        @JsonProperty("commentCount")
        public  int commentCount;
        @JsonProperty("viewCount")
        public  int viewCount;
    }
}
