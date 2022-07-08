package main.api.response;

import lombok.Data;
import main.model.PostComments;
import main.model.Users;

import javax.persistence.Id;
import java.util.Date;

@Data
public class PostCommentsExternal extends PostComments {
    @Id
    private int id;
    private Date timestamp;
    private String text;

    private UserExternal user;
}
