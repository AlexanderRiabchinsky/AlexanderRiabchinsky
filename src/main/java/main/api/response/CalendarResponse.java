package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class CalendarResponse {
    @JsonProperty("years")
    private int years;
    @JsonProperty("posts")
    private List<Posts> posts;

    @Data
    private static class Posts {
        @JsonProperty("time")
        private Date time;
        @JsonProperty("view_count")
        private int view_count;
    }
}
