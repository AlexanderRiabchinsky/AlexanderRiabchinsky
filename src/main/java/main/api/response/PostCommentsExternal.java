package main.api.response;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class PostCommentsExternal {
    @Id
    private int id;
    private long timestamp;
    private String text;

    private UserExternal user;
}
