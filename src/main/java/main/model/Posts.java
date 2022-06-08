package main.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@Table(name = "posts")
public class Posts{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "is_active")
    private int isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status",
            columnDefinition = "enum('NEW', 'ACCEPTED', 'DECLINED') default 'NEW'",
            nullable = false)
    private ModerationStatus status;


    @Column(name = "moderator_id")
    private int moderatorId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users user;

    private LocalDateTime timestamp;

    private String title;

    private String text;

    @Column(name = "view_count")
    private int viewCount;


    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY)
    //@JoinColumn(name = "post_id")
    private List<PostComments> postComments;

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY)
   // @JoinColumn(name = "post_id")
    private List<PostVotes> postVotes;

    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinTable(name = "tag2post", joinColumns = {@JoinColumn(name = "postId")},
            inverseJoinColumns = {@JoinColumn(name = "tagId")})
    private List<Tags> tags;
}
