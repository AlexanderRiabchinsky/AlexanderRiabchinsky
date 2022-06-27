package main.service;

import main.api.response.PostExternal;
import main.api.response.PostResponse;
import main.api.response.PostSearchResponse;
import main.model.Posts;
import main.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiPostSearchService {
    @Autowired
    private PostsRepository postsRepository;
    public PostSearchResponse getPostSearchResponse() {
        PostSearchResponse postSearchResponse = new PostSearchResponse();
        postSearchResponse.setCount((int) postsRepository.count());
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
        postSearchResponse.setPosts(postss);
        return postSearchResponse;
    }
}
