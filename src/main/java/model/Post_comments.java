package model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "post_comments")
public class Post_comments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    private int parent_id;

    private int post_id;

    private int user_id;

    private Date time;

    private String text;
}
