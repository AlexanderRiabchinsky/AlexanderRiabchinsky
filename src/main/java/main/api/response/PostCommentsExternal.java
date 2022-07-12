package main.api.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import main.model.PostComments;
import main.model.Users;

import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
public class PostCommentsExternal {
    @Id
    private int id;
    private Date timestamp;
    private String text;

    private UserExternal user;
}
