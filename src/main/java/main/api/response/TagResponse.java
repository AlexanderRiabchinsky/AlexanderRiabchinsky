package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mysql.cj.xdevapi.JsonArray;
import lombok.Data;
import main.model.Tags;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.util.List;

@Data
@Component
public class TagResponse {
    @JsonProperty("tags")
    private List<Tags> tags;
    public class Tags{
        String name;
        double weight;

    }

}
