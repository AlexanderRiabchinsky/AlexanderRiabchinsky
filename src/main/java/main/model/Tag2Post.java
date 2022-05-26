package main.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "tag2post")
public class Tag2Post implements Serializable {
    @EmbeddedId
    private Key1 id;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "post_id",insertable = false,updatable = false)
//    private Posts post;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "tag_id",insertable = false,updatable = false)
//    private Tags tag;
}
