package main.api.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostByTagResponse {
    private int count;
    private ArrayList<PostExternal> posts;
}
