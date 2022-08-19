package main.api.response;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class TagExternal {
    @Id
    private String name;
    private double weight;
}
