package main.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;


    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Posts> posts;
}
