package main.model;

import lombok.Data;
import main.api.response.ModerationStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "posts")
public class Posts{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Users user;

    @Column(name = "time")
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
    @JoinTable(name = "tag2post", joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tags> tags;
}
