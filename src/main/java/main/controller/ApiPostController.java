package main.controller;

import main.api.response.*;
import main.model.Posts;
import main.api.response.UserExternal;
import main.repositories.PostsRepository;
import main.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiPostController {

    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/api/post")
    public PostResponse postCheck() {
        PostResponse postResponse = new PostResponse();
        postResponse.setCount((int) postsRepository.count());
        List<Posts> posts = postsRepository.findAll();
        ArrayList<PostExternal> postss = new ArrayList<>();
        for (Posts post:posts) {
        PostExternal postExt = new PostExternal();
        postExt.setId(post.getId());
        postExt.setTimestamp(post.getTimestamp());
        postExt.setUser(post.getUser());

        postExt.setTitle(post.getTitle());
        postExt.setAnnounce("Анонс поста");
        postExt.setLikeCount(5);
        postExt.setDislikeCount(5);
        postExt.setCommentCount(5);
        postExt.setViewCount(post.getViewCount());

        postss.add(postExt);}
        postResponse.setPosts(postss);
        return postResponse;
    }

    @GetMapping("/api/post/search")
    public PostSearchResponse postSearchCheck() {
        PostSearchResponse postSearchResponse = new PostSearchResponse();
        postSearchResponse.setCount((int) postsRepository.count());
        List<Posts> posts = postsRepository.findAll();
        ArrayList<PostSearchResponse.Posts> postss = new ArrayList<>();
        for (Posts post:posts) {
            PostSearchResponse.Posts postt = new PostSearchResponse.Posts();
            postt.setId(post.getId());
            postt.setTimestamp("2022-05-30");

            UserExternal userr = new UserExternal();

            userr.setId(postsRepository.getUserIdByPostId(post.getId()));

            userr.setName(usersRepository.getUserNameById(postsRepository.getUserIdByPostId(post.getId())));

       //        postt.setUser((List<PostSearchResponse.Posts.Users>) userr);
            postt.setTitle(post.getTitle());
            postt.setAnnounce("Анонс поста в отборе");
            postt.setLikeCount(20);
            postt.setDislikeCount(10);
            postt.setCommentCount(30);
            postt.setViewCount(50);

            postss.add(postt);}
        postSearchResponse.setPostsList(postss);
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
