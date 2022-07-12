package main.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Date;
import java.time.LocalDateTime;

@Accessors(chain = true)
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

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "id", insertable = false, updatable = false)
//    private Posts postComment;

    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", nullable = false)
    private Posts post;

    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    //private int userId;

    private Date time;

    private String text;
}
