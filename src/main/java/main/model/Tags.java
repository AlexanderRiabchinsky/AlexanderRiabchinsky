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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_id")
    private List<Tag2Post> tag2post;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.ALL})
    @JoinTable(name = "tag2post", joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "post_id")})
    private List<Posts> post;
}
