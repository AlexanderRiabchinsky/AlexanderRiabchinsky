package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.model.Posts;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Data
//@Component
public class CalendarResponse {
    @JsonProperty("years")
    public int years;
    @JsonProperty("posts")
    private List<Posts> posts;

    @Component
    public class Posts {
        @JsonProperty("time")
        private Date time;
        @JsonProperty("view_count")
        private int view_count;
    }

}