package main.api.response;

import lombok.Data;
import main.model.Users;

import javax.persistence.Id;

@Data
public class UserExternal2 extends Users {
    @Id
    private int id;
    private String name;
    private String photo;
}
