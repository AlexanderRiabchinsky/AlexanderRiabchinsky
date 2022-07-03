package main.api.response;

import lombok.Data;
import java.util.ArrayList;

@Data
public class PostResponse  {
    private long count;
    private ArrayList<PostExternal> posts;
}
