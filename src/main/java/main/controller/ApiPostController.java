package main.controller;

import main.api.response.*;
import main.model.PostsRepository;
import main.model.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ApiPostController {

    @Autowired
    private PostsRepository postsRepository;
    private UsersRepository usersRepository;

    @GetMapping("/api/post")
    public PostResponse postCheck() {
        PostResponse postResponse = new PostResponse();
        postResponse.setCount((int) postsRepository.count());
        PostResponse.Posts post = new PostResponse.Posts();
        post.setId(post.getId());
        post.setTimestamp("2022-04-08");
        PostResponse.Posts.Users user = new PostResponse.Posts.Users();
        user.setId(1);
        user.setName("Petya");
        ArrayList<PostResponse.Posts.Users> users = new ArrayList<>();
        post.setUser(users);
        post.setTitle("Проект в разработке - апрель");
        post.setAnnounce("Анонс поста");
        post.setLikeCount(2);
        post.setDislikeCount(1);
        post.setCommentCount(3);
        post.setViewCount(5);
        ArrayList<PostResponse.Posts> posts = new ArrayList<>();
        posts.add(post);
        postResponse.setPostsList(posts);
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
