package main.service;

import main.api.response.*;
import main.model.PostComments;
import main.model.Posts;
import main.model.Tags;
import main.repositories.PostCommentsRepository;
import main.repositories.PostsRepository;
import main.repositories.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApiPostService {
    private MapperService mapperService;
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
        PostResponse postByMode = new PostResponse();
        List<Posts> posts = new ArrayList<>();
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Posts> page;
        switch (mode) {
            case "popular":
                page = postsRepository.findPopularPosts(pageable);
                break;
            case "early":
                page = postsRepository.findEarlyPosts(pageable);
                break;
            case "best":
                page = postsRepository.findBestPosts(pageable);
                break;
            default:
                page = postsRepository.findRecentPosts(pageable);
        }System.out.println("offset = "+offset+"; limit = "+limit+"; mode = "+mode);
        posts.addAll(page.getContent());System.out.println("posts = "+posts);
        postByMode.setCount(page.getTotalElements());System.out.println("tot elements = "+page.getTotalElements());
        System.out.println("CHECK = "+posts.stream().map(mapperService::convertPostToDto));
        List<PostExternal> postDtoList = posts.stream().map(mapperService::convertPostToDto)
                .collect(Collectors.toList());
        postByMode.setPosts(postDtoList);
        return postByMode;
    }
    public PostResponse getPostByDate   (int offset, int limit, String date) {
        PostResponse postByDateResponse = new PostResponse();
        Pageable pageable = PageRequest.of(offset / limit, limit);System.out.println("date = "+date);
        Page<Posts> page = postsRepository.findPostsByDate(pageable, date);
        System.out.println("stream = "+page.getContent().stream());
        postByDateResponse.setPosts(page.getContent().stream().map(mapperService::convertPostToDto)
                .collect(Collectors.toList()));
        postByDateResponse.setCount(page.getTotalElements());

        return postByDateResponse;
    }
    public PostResponse getPostByTag(int offset, int limit, String tag) {
        PostResponse postByTagResponse = new PostResponse();
        Pageable pageable = PageRequest.of(offset / limit, limit);System.out.println("tag = "+tag);
        Page<Posts> page = postsRepository.findPostsByTag(pageable, tag);
        postByTagResponse.setPosts(page.getContent().stream().map(mapperService::convertPostToDto)
                .collect(Collectors.toList()));
        postByTagResponse.setCount(page.getTotalElements());
        return postByTagResponse;
    }
    public PostIDResponse getPostById(Posts post, Principal principal) {
       // AuthCheckResponse authCheckResponse = authCheckService.getAuthCheck(principal);
        int view;
      /*  if (authCheckResponse.isResult()) {
            UserExternal user = authCheckResponse.getUser();
            if (user.isModeration() || user.getId() == post.getUser().getId()) {
                view = post.getViewCount();
            } else {
                view = post.getViewCount() + 1;
                post.setViewCount(view);
                postsRepository.save(post);
            }
        } else */{
            view = post.getViewCount() + 1;
            post.setViewCount(view);
            postsRepository.save(post);
        }

        List<PostCommentsExternal> comments = post.getPostComments().stream()
                .map(mapperService::convertCommentToDto)
                .collect(Collectors.toList());
        List<String> tags = post.getTags().stream().map(Tags::getName)
                .collect(Collectors.toList());
        PostExternal postDto = mapperService.convertPostToDto(post);

        return new PostIDResponse(postDto.getId(), postDto.getTimestamp(),
                postDto.isActive(), postDto.getUser(), postDto.getTitle(), postDto.getAnnounce(),
                postDto.getLikeCount(), postDto.getDislikeCount(), view,
                comments, tags);
    }

    public PostResponse getModerationData(int offset, int limit, String status, Principal principal){
        PostResponse postModeration = new PostResponse();
        int moderatorId = 6;//getAuthorizedUser(principal).getId();
        List<Posts> posts = new ArrayList<>();
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Posts> page;
        switch (status) {
            case "accepted":
                page = postsRepository.findAcceptedPostsByModerator(pageable, moderatorId);
                break;
            case "declined":
                page = postsRepository.findDeclinedPostsByModerator(pageable, moderatorId);
                break;
            default:
                page = postsRepository.findNewPosts(pageable);
        }
        posts.addAll(page.getContent());
        postModeration.setCount(page.getTotalElements());
        List<PostExternal> moderatorPosts = posts.stream()
                .map(mapperService::convertPostToDto)
       /*         .map(p -> new PostExternal(p.getId(), p.getTimestamp(),
                        p.getTitle(), p.getAnnounce(), p.getLikeCount(),
                        p.getDislikeCount(), p.getCommentCount(), p.getViewCount(), p.getUser()))*/
                .collect(Collectors.toList());

        postModeration.setPosts(page.getContent().stream().map(mapperService::convertPostToDto)
                .collect(Collectors.toList()));

        postModeration.setPosts(moderatorPosts);

        return postModeration;
    }
}
