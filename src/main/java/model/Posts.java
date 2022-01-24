package model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "posts")
public class Posts {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int is_active;

    private enum moderation_status {NEW,ACCEPTED,DECLINED};

    private int moderator_id;

    private int user_id;

    private Date time;

    private String title;

    private String text;

    private int view_count;
}
