package main.api.request;

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

    private long timestamp;
    private byte active;
    private String title;
    private List<String> tags;
    private String text;
}
