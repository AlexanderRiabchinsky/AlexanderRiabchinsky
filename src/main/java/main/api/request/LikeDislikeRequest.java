package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDislikeRequest {
    @JsonProperty("post_id")
    private int postId;
}
