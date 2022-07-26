package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import main.model.Tags;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RegPostRequest {

    @JsonProperty("time")
    private long timestamp;
    @JsonProperty("is_active")
    private int active;
    private String title;
    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinTable(name = "tag2post", joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private ArrayList<Tags> tags;
    private String text;
}
