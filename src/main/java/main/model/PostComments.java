package main.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "post_comments")
public class PostComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "parent_id")
    private Integer parentId;


    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", nullable = false)
    private Posts post;

    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    private LocalDateTime time;

    private String text;
}
