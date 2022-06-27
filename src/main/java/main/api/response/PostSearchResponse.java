package main.api.response;

import lombok.Data;
import java.util.ArrayList;

@Data
public class PostSearchResponse {
    private int count;
    private ArrayList<PostExternal> posts;
}
