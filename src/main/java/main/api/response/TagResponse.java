package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TagResponse {
 //   @JsonProperty("tags")
    private ArrayList<TagExternal> tags;



}
