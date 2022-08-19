package main.api.response;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class PostModeration {
    private int count;
    private ArrayList<PostExternal> posts;
}
