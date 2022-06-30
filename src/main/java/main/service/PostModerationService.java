package main.service;

import main.api.response.PostExternal;
import main.api.response.PostModeration;
import main.model.Posts;
import main.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostModerationService {
    @Autowired
    private PostsRepository postsRepository;
    public PostModeration getModerationData(){
        PostModeration postModeration = new PostModeration();
        postModeration.setCount(postModeration.getCount());
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
        postModeration.setPosts(postss);
        return postModeration;
    }
}
