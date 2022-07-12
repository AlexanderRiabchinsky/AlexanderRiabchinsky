package main.api.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostResponse  {
    private long count;
    private List<PostExternal> posts;
}
