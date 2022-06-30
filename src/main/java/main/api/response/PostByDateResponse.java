package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostByDateResponse {
    private int count;
    private ArrayList<PostExternal> posts;
}
