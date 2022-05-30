package main.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@Table(name = "posts")
public class Posts {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "is_active")
    private int isActive;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_ID", nullable = false, columnDefinition = "enum('NEW', 'ACCEPTED', 'DECLINED') default 'NEW'")
    private ModerationStatus status;


    @Column(name = "moderator_id")
    private int moderatorId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Users user;

    private Date time;

    private String title;

    private String text;

    @Column(name = "view_count")
    private int viewCount;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "post_id")
//    private List<Tag2Post> tag2post;

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY)
    //@JoinColumn(name = "post_id")
    private List<PostComments> postComments;

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY)
   // @JoinColumn(name = "post_id")
    private List<PostVotes> postVotes;

    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.ALL},fetch = FetchType.LAZY)
//    @JoinTable(name = "tag2post", joinColumns = {@JoinColumn(name = "postId")},
//            inverseJoinColumns = {@JoinColumn(name = "tagId")})
    private List<Tags> tags;
}
