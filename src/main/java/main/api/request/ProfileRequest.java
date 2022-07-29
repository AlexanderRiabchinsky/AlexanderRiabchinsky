package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequest {
    private String name;
    @JsonProperty("e_mail")
    private String email;
    private String password;
    private int removePhoto;
}
