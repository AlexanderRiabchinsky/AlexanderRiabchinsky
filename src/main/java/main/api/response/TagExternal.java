package main.api.response;

import lombok.Data;
import main.model.Tags;

import javax.persistence.Id;

@Data
public class TagExternal {
    @Id
    private String name;
    private double weight;
}
