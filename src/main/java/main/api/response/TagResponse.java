package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mysql.cj.xdevapi.JsonArray;
import lombok.Data;
import main.model.Tags;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.util.List;

@Data
//@Component
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
