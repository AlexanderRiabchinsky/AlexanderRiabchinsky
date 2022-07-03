package main.api.response;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostResponse  {
    private long count;
    private List<PostExternal> posts;
}
