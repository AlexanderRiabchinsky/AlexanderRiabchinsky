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

    private int is_active;

    private enum status {NEW, ACCEPTED, DECLINED}


    private int moderator_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Users user_id;

    private Date time;

    private String title;

    private String text;

    private int view_count;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "post_id")
//    private List<Tag2Post> tag2post;

    @OneToMany(mappedBy = "post_comment",fetch = FetchType.LAZY)
    //@JoinColumn(name = "post_id")
    private List<PostComments> postComment;

    @OneToMany(mappedBy = "post_vote",fetch = FetchType.LAZY)
   // @JoinColumn(name = "post_id")
    private List<PostVotes> postVote;

    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.ALL})
    @JoinTable(name = "tag2post", joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tags> tags;
}
