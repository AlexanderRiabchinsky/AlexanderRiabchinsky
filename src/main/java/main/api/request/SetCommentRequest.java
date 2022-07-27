package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetCommentRequest {
    private int parent_id=1;
    private int post_id;
    private String text;
}
