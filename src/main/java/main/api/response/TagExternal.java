package main.api.response;

import lombok.Data;
import main.model.Tags;

import javax.persistence.Id;

@Data
public class TagExternal extends Tags {
    @Id
    private String name;
    private double weight;
}
