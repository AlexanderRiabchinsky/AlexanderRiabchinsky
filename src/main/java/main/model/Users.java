package main.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "is_moderator")
    private int isModerator;

    @Column(name = "reg_time")
    private Date regTime;

    private String name;

    private String email;

    private String password;

    private String code;

    private String photo;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
  //  @JoinColumn(name = "user_id")
    private List<Posts> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostVotes> postVotes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostComments> postComments;
}
