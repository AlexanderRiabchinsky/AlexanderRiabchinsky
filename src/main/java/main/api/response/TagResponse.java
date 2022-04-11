package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class TagResponse {
    @JsonProperty("tags")
    private List<Tags> tags;

    @Data
    public static class Tags {
        @JsonProperty("name")
        private String name;
        @JsonProperty("weight")
        private double weight;
    }

}
