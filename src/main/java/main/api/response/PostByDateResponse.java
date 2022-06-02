package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PostByDateResponse {
//    @JsonProperty("count")
    private int count;
 //   @JsonProperty("posts")
    private List<Posts> postsList;

    @Data
    private static class Posts {
  //      @JsonProperty("id")
        private int id;
 //       @JsonProperty("timestamp")
        private String timestamp;
 //       @JsonProperty("user")
        private Users user;

        @Data
        private static class Users {
  //          @JsonProperty("id")
            private int id;
 //           @JsonProperty("name")
            private String name;
        }

 //       @JsonProperty("title")
        private String title = "Заголовок Поста";
 //       @JsonProperty("announce")
        private String announce = "Текст анонса поста без HTML-тэгов";
  //      @JsonProperty("likeCount")
        private int likeCount;
 //       @JsonProperty("dislikeCount")
        private int dislikeCount;
 //       @JsonProperty("commentCount")
        private int commentCount;
 //       @JsonProperty("viewCount")
        private int viewCount;
    }
}
