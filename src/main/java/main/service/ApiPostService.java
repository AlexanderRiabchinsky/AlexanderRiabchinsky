package main.service;

import lombok.AllArgsConstructor;
import main.api.response.*;
import main.model.PostComments;
import main.model.Posts;
import main.model.Tags;
import main.model.User;
import main.repositories.PostCommentsRepository;
import main.repositories.PostsRepository;
import main.repositories.TagsRepository;
import main.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApiPostService {
    private MapperService mapperService;
    private ApiAuthService apiAuthService;
  //  @Autowired
    private PostsRepository postsRepository;
    private final UserRepository userRepository;
    private PostCommentsRepository postCommentsRepository;
    private TagsRepository tagsRepository;

    public PostResponse getPostSearch(int offset,int limit,String query) {
        PostResponse postSearchResponse = new PostResponse();
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Posts> page = postsRepository.findPostsByQuery(pageable, query);
        postSearchResponse.setPosts(page.getContent().stream().map(mapperService::convertPostToDto)
                .collect(Collectors.toList()));
        postSearchResponse.setCount(page.getTotalElements());

        return postSearchResponse;
    }

    public PostResponse getPostByMode   (int offset, int limit, String mode,Principal principal) {
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
        }
        posts.addAll(page.getContent());
        postByMode.setCount(page.getTotalElements());
        List<PostExternal> postDtoList = posts.stream().map(mapperService::convertPostToDto).collect(Collectors.toList());
        postByMode.setPosts(postDtoList);
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
    public PostIDResponse getPostById(int id, Principal principal) {
        Posts post = postsRepository.findById(id).get();

   //     AuthCheckResponse authCheckResponse = apiAuthService.getAuthCheck(principal);
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
        int moderatorId = getAuthorizedUser(principal).getId();
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
            case "new":    page = postsRepository.findNewPosts(pageable);
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

    public PostResponse getMyPosts(int offset, int limit, String status, Principal principal){
        PostResponse myPosts = new PostResponse();
//        int moderatorId = 6;//getAuthorizedUser(principal).getId();
//        List<Posts> posts = new ArrayList<>();
//        Pageable pageable = PageRequest.of(offset / limit, limit);
//        Page<Posts> page;
//        switch (status) {
//            case "accepted":
//                page = postsRepository.findAcceptedPostsByModerator(pageable, moderatorId);
//                break;
//            case "declined":
//                page = postsRepository.findDeclinedPostsByModerator(pageable, moderatorId);
//                break;
//            default:
//            case "new":    page = postsRepository.findNewPosts(pageable);
//        }
//        posts.addAll(page.getContent());
//        postModeration.setCount(page.getTotalElements());
//        List<PostExternal> moderatorPosts = posts.stream()
//                .map(mapperService::convertPostToDto)
//                /*         .map(p -> new PostExternal(p.getId(), p.getTimestamp(),
//                                 p.getTitle(), p.getAnnounce(), p.getLikeCount(),
//                                 p.getDislikeCount(), p.getCommentCount(), p.getViewCount(), p.getUser()))*/
//                .collect(Collectors.toList());
//
//        postModeration.setPosts(page.getContent().stream().map(mapperService::convertPostToDto)
//                .collect(Collectors.toList()));
//
//        postModeration.setPosts(moderatorPosts);

        return myPosts;
    }

    private User getAuthorizedUser(Principal principal){
        User user = userRepository.findByEmail(principal.getName()).get();
        return user;
    }
}
