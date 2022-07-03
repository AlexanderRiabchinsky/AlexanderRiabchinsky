package main.service;

import main.api.response.*;
import main.model.Posts;
import main.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiPostService {
    @Autowired
    private PostsRepository postsRepository;
    public PostResponse getPostResponse() {
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
    public PostResponse getPostSearchResponse() {
        PostResponse postSearchResponse = new PostResponse();
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
    public PostResponse getPostByDate   (int offset, int limit, String date) {
        PostResponse postByDateResponse = new PostResponse();
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Posts> page = postsRepository.findPostsByDate(pageable, date);
        postByDateResponse.setPosts(page.getContent().stream().map(mapperService::convertPostToDto)
                .collect(Collectors.toList()));
        postByDateResponse.setCount(page.getTotalElements());

        return postByDateResponse;
    }
    public PostResponse getPostByTagResponse() {
        PostResponse postByTagResponse = new PostResponse();
        postByTagResponse.setCount((int) postsRepository.count());
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
        postByTagResponse.setPosts(postss);
        return postByTagResponse;
    }
    public PostIDResponse getPostByIdResponse(int id) {
        PostIDResponse postIdResponse = new PostIDResponse();
        postIdResponse.setId(id);
        postIdResponse.setTimestamp(postIdResponse.getTimestamp());
        postIdResponse.setActive(postIdResponse.isActive());
        postIdResponse.setUser(postIdResponse.getUser());
        postIdResponse.setTitle(postIdResponse.getTitle());
        postIdResponse.setText(postIdResponse.getText());
        postIdResponse.setLikeCount(postIdResponse.getLikeCount());
        postIdResponse.setDislikeCount(postIdResponse.getDislikeCount());
        postIdResponse.setViewCount(postIdResponse.getViewCount());
        postIdResponse.setComments(postIdResponse.getComments());
        postIdResponse.setTags(postIdResponse.getTags());

        return postIdResponse;
    }
    public PostResponse getModerationData(){
        PostResponse postModeration = new PostResponse();
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
