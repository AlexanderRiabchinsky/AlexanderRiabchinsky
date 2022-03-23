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
@Component
public class PostIDResponse {
    @JsonProperty("id")
    public  int id;
    @JsonProperty("timestamp")
    public Date timestamp;
    @JsonProperty("active")
    public boolean active;
    @JsonProperty("user")
    public  Users user;
    public class Users{
        @JsonProperty("id")
        public int id;
        @JsonProperty("name")
        public String name;
    }
    @JsonProperty("title")
    public  String title;
    @JsonProperty("text")
    public  String text;
    @JsonProperty("likeCount")
    public  int likeCount;
    @JsonProperty("dislikeCount")
    public  int dislikeCount;
    @JsonProperty("viewCount")
    public  int viewCount;
    @JsonProperty("comments")
    private List<PostComments> comments;
    public class PostComments{
        @JsonProperty("id")
        public  int id;
        @JsonProperty("timestamp")
        public  String timestamp;
        @JsonProperty("text")
        public  String text;
        @JsonProperty("user")
        public Users user;
        public class Users{
            @JsonProperty("id")
            public int id;
            @JsonProperty("name")
            public String name;
            @JsonProperty("photo")
            public String photo;
        }
    }
    @JsonProperty("tags")
    private List<Tags>tags;

}
