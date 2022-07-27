package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetCommentRequest {
    private Integer parent_id;
    private int post_id;
    private String text;
}
