package main.controller;

import main.api.response.*;
import main.model.Posts;
import main.model.PostsRepository;
import main.model.Users;
import main.model.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiPostController {

    @Autowired
    private PostsRepository postsRepository;
    private UsersRepository usersRepository;

    @GetMapping("/api/post")
    public PostResponse postCheck() {
        PostResponse postResponse = new PostResponse();
        postResponse.setCount((int) postsRepository.count());
        List<Posts> posts = postsRepository.findAll();
        ArrayList<PostResponse.Posts> postss = new ArrayList<>();
        for (Posts post:posts) {
        PostResponse.Posts postt = new PostResponse.Posts();
        postt.setId(post.getId());
        postt.setTimestamp("2022-04-08");
 //           Optional<Users> user = usersRepository.findById(postsRepository.getUser(post.getId()));
        PostResponse.Posts.Users userr = new PostResponse.Posts.Users();
       // userr.setId(postsRepository.getUser(post.getId()));
        userr.setId(postsRepository.getUser(post.getId()));
      //  userr.setId(postsRepository.findById(post.getUser_id()));
        userr.setName(usersRepository.getUserNameById(postsRepository.getUser(post.getId())));
    //    userr.setName(usersRepository.getUserNameById(post.getUser_id()));
 //       ArrayList<PostResponse.Posts.Users> users = new ArrayList<>();
        postt.setUser((List<PostResponse.Posts.Users>) userr);
        postt.setTitle(post.getTitle());
        postt.setAnnounce("Анонс поста");
        postt.setLikeCount(2);
        postt.setDislikeCount(1);
        postt.setCommentCount(3);
        postt.setViewCount(5);

        postss.add(postt);}
        postResponse.setPostsList(postss);
        return postResponse;
    }

    @GetMapping("/api/post/search")
    public PostSearchResponse postSearchCheck() {
        PostSearchResponse postSearchResponse = new PostSearchResponse();
        return postSearchResponse;
    }

    @GetMapping("/api/post/byDate")
    public PostByDateResponse postByDateCheck() {
        PostByDateResponse postByDateResponse = new PostByDateResponse();
        return postByDateResponse;
    }

    @GetMapping("/api/post/byTag")
    public PostByTagResponse postByTagCheck() {
        PostByTagResponse postByTagResponse = new PostByTagResponse();
        return postByTagResponse;
    }

    @GetMapping("/api/post/{ID}")
    public PostIDResponse postIDCheck() {
        PostIDResponse postIDResponse = new PostIDResponse();
        return postIDResponse;
    }

    @GetMapping("/api/post/moderation")
    public PostModeration moderationCheck() {
        PostModeration postModeration = new PostModeration();
        return postModeration;
    }

}
