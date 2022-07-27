package main.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModerationRequest {
    private int post_id;
    private String decision;
}
