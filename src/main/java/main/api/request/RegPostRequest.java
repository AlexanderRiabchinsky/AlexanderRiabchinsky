package main.api.request;

import lombok.Getter;
import lombok.Setter;

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
