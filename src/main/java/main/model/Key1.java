package main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Key1 implements Serializable {
    @Column(name = "post_id", insertable = false, updatable = false)
    private int postId;
    @Column(name = "tag_id", insertable = false, updatable = false)
    private int tagId;
}
