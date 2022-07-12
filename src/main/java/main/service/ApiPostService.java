package main.service;

import main.api.response.*;
import main.model.PostComments;
import main.model.Posts;
import main.repositories.PostCommentsRepository;
import main.repositories.PostsRepository;
import main.repositories.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApiPostService {
    public MapperService mapperService;
    @Autowired
    private PostsRepository postsRepository;
    private PostCommentsRepository postCommentsRepository;
    private TagsRepository tagsRepository;

    public PostResponse getPostSearch(int offset,int limit,String query) {
        PostResponse postSearchResponse = new PostResponse();
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Posts> page = postsRepository.findPostsByQuery(pageable, query);
        postSearchResponse.setPosts(page.getContent().stream().map(mapperService::convertPostToDto)
                .collect(Collectors.toList()));
        postSearchResponse.setCount(page.getTotalElements());
//        postSearchResponse.setCount((int) postsRepository.count());
//        List<Posts> posts = postsRepository.findAll();
//        ArrayList<PostExternal> postss = new ArrayList<>();
//        for (Posts post:posts) {
//            PostExternal postExt = new PostExternal();
//            postExt.setId(post.getId());
//            postExt.setTimestamp(post.getTimestamp());
//            postExt.setUser(post.getUser());
//
//            postExt.setTitle(post.getTitle());
//            postExt.setAnnounce("Анонс поста");
//            postExt.setLikeCount(5);
//            postExt.setDislikeCount(5);
//            postExt.setCommentCount(5);
//            postExt.setViewCount(post.getViewCount());
//
//            postss.add(postExt);}
//        postSearchResponse.setPosts(postss);
        return postSearchResponse;
    }

    public PostResponse getPostByMode   (int offset, int limit, String mode) {
        Sort sort;

        switch (mode){

            case "popular": sort = new Sort(Sort.Direction.DESC,"comments_count");
            break;
            case "best": sort = new Sort(Sort.Direction.DESC,"likes_count");
            break;
            case "early": sort = new Sort(Sort.Direction.ASC,"time");
            break;
            default: case "recent":sort = new Sort(Sort.Direction.DESC,"time");
        }
        PostResponse postByMode = new PostResponse();        System.out.println("mode third = "+mode);
        Pageable pageable = PageRequest.of(offset / limit, limit, sort);        System.out.println("pageable = "+pageable.toString());
        Page<Posts> page = postsRepository.findPostsByMode(pageable,mode);System.out.println(sort.toString());System.out.println("tot elements = "+page.getTotalElements());
        postByMode.setPosts(page.getContent().stream().map(mapperService::convertPostToDto)
                .collect(Collectors.toList()));
        postByMode.setCount(page.getTotalElements());

        return postByMode;
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
    public PostResponse getPostByTag(int offset, int limit, String tag) {
        PostResponse postByTagResponse = new PostResponse();
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Posts> page = postsRepository.findPostsByTag(pageable, tag);
        postByTagResponse.setPosts(page.getContent().stream().map(mapperService::convertPostToDto)
                .collect(Collectors.toList()));
        postByTagResponse.setCount(page.getTotalElements());
        return postByTagResponse;
    }
    public PostIDResponse getPostById(int id) {
        PostIDResponse postIdResponse = new PostIDResponse();
        Optional<Posts> post = postsRepository.findById(id);
        postIdResponse.setId(post.get().getId());
        postIdResponse.setTimestamp(post.get().getTimestamp());
        postIdResponse.setActive(post.get().getIsActive()==1);
        postIdResponse.setUser(mapperService.convertUserToDto(post.get().getUser()));
        postIdResponse.setTitle(post.get().getTitle());
        postIdResponse.setText(post.get().getText());
        postIdResponse.setLikeCount(postsRepository.findPostLikesCount(post.get().getId()));
        postIdResponse.setDislikeCount(postsRepository.findPostDislikesCount(post.get().getId()));
        postIdResponse.setViewCount(post.get().getViewCount());
        Page<PostComments> pageComm = postCommentsRepository.findCommentsByPostId(Pageable.unpaged(),post.get().getId());
        postIdResponse.setComments(pageComm.getContent().stream().map(mapperService::convertPostToComment) .collect(Collectors.toList()));
        postIdResponse.setTags(tagsRepository.findTagsByPost(post.get().getId()));//

        return postIdResponse;
    }
    public PostResponse getModerationData(int offset, int limit){
        PostResponse postModeration = new PostResponse();
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Posts> page = postsRepository.findPostsModeration(pageable);
        postModeration.setPosts(page.getContent().stream().map(mapperService::convertPostToDto)
                .collect(Collectors.toList()));
        postModeration.setCount(page.getTotalElements());

        return postModeration;
    }
}
