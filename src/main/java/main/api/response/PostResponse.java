package main.api.response;

import lombok.Data;
import main.controller.PostExternal;

import java.util.ArrayList;

@Data
public class PostResponse {

    private int count;
    //   @JsonProperty("posts")
    private ArrayList<PostExternal> posts;

//    @Data
//    public static class Posts {
//        private int id;
//        private LocalDateTime timestamp;
//        private UserExternal user;

//        @Data
//        public static class Users {
//            private int id;
//            private String name;
//        }

//        private String title = "Заголовок Поста";
//        private String announce = "Текст анонса поста без HTML-тэгов";
//        private int likeCount;
//        private int dislikeCount;
//        private int commentCount;
//        private int viewCount;
//    }
}
