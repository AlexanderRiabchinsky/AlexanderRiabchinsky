package main.api.response;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
public class UserAuthCheck {
    @Id
    private int id;
    private String name;
    private String photo;
    private String email;
    private boolean moderation;
    private int moderationCount;
    private boolean settings;
}