package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestoreRequest {
//    @JsonProperty("e_mail")
    private String email;
}
