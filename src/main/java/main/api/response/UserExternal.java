package main.api.response;

import lombok.Data;
import main.model.Users;

import javax.persistence.Id;

@Data
public class UserExternal extends Users {
    @Id
    private int id;
    private String name;
}
