package main.controller;

import main.api.response.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ApiPostController {
//    private final PostResponse postResponse;
//    private final PostSearchResponse postSearchResponse;
//    private final PostByDateResponse postByDateResponse;
//    private final PostByTagResponse postByTagResponse;
//    private final PostIDResponse postIDResponse;
//    private final PostModeration postModeration;
//
//    public ApiPostController(PostResponse postResponse, PostSearchResponse postSearchResponse, PostByDateResponse postByDateResponse, PostByTagResponse postByTagResponse, PostIDResponse postIDResponse, PostModeration postModeration) {
//        this.postResponse = postResponse;
//        this.postSearchResponse = postSearchResponse;
//        this.postByDateResponse = postByDateResponse;
//        this.postByTagResponse = postByTagResponse;
//        this.postIDResponse = postIDResponse;
//        this.postModeration = postModeration;
//    }

    @GetMapping("/api/post")
    public PostResponse postCheck() {
        PostResponse postResponse = new PostResponse();
        postResponse.setCount(2);
        PostResponse.Posts post = new PostResponse.Posts();
        post.setId(1);
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
