package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetCommentRequest {
    @JsonProperty("parent_id")
    private int parentId;
    @JsonProperty("post_id")
    private int postId;
    private String text;
}
