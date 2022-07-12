package main.api.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import main.model.Tags;

import javax.persistence.Id;

@Getter
@Setter
public class TagExternal {
    @Id
    private String name;
    private double weight;
}
