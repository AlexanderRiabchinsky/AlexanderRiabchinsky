package main.model;

import lombok.Data;

import javax.persistence.Id;

@Data
public class UserExternal extends Users{
    @Id
    private int id;
    private String name;
}
