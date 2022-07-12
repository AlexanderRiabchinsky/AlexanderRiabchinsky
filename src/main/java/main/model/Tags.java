package main.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Accessors(chain = true)
@Entity
@Getter
@Setter
@Table(name = "tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;


    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Posts> posts;
}
