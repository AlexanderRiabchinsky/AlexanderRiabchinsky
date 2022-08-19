package main.service;

import lombok.AllArgsConstructor;
import main.api.request.LikeDislikeRequest;
import main.api.request.RegPostRequest;
import main.api.response.*;
import main.model.*;
import main.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApiPostService {
    private final MapperService mapperService;
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final PostVotesRepository postVotesRepository;
    private final TagsRepository tagsRepository;
    private final ApiGeneralService apiGeneralService;

    public static final int TITLE_LENGTH = 3;
    public static final int TEXT_LENGTH = 50;

    public PostResponse getPostSearch(int offset, int limit, String query) {
        PostResponse postSearchResponse = new PostResponse();
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Posts> page = postsRepository.findPostsByQuery(pageable, query);
        postSearchResponse.setPosts(page.getContent().stream().map(mapperService::convertPostToDto)
                .collect(Collectors.toList()));
        postSearchResponse.setCount(page.getTotalElements());

        return postSearchResponse;
    }

    public PostResponse getPostByMode(int offset, int limit, String mode) {
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
            case "recent":
                page = postsRepository.findRecentPosts(pageable);
        }
        posts.addAll(page.getContent());
        postByMode.setCount(page.getTotalElements());
        List<PostExternal> postDtoList = posts.stream().map(mapperService::convertPostToDto).collect(Collectors.toList());
        postByMode.setPosts(postDtoList);
        return postByMode;
    }

    public PostResponse getPostByDate(int offset, int limit, String date) {
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

        AuthCheckResponse authCheckResponse = getAuthCheck(principal);
        int view;
        if (authCheckResponse.isResult()) {
            UserExternal user = authCheckResponse.getUser();
            if (user.isModeration() || user.getId() == post.getUser().getId()) {
                view = post.getViewCount();
            } else {
                view = post.getViewCount() + 1;
                post.setViewCount(view);
                postsRepository.save(post);
            }
        } else {
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
                postDto.isActive(), postDto.getUser(), postDto.getTitle(), postDto.getText(),
                postDto.getLikeCount(), postDto.getDislikeCount(), view,
                comments, tags);
    }

    public PostResponse getModerationData(int offset, int limit, String status, Principal principal) {
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
            case "new":
                page = postsRepository.findNewPosts(pageable);
        }
        posts.addAll(page.getContent());
        postModeration.setCount(page.getTotalElements());
        List<PostExternal> moderatorPosts = posts.stream()
                .map(mapperService::convertPostToDto)
                .collect(Collectors.toList());

        postModeration.setPosts(page.getContent().stream().map(mapperService::convertPostToDto)
                .collect(Collectors.toList()));

        postModeration.setPosts(moderatorPosts);

        return postModeration;
    }

    public PostResponse getMyPosts(int offset, int limit, String status, Principal principal) {
        PostResponse myPosts = new PostResponse();
        int myId = getAuthorizedUser(principal).getId();
        List<Posts> posts = new ArrayList<>();
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Posts> page;
        switch (status) {
            case "inactive":
                page = postsRepository.findMyInactivePosts(pageable, myId);
                break;
            case "pending":
                page = postsRepository.findMyPendingPosts(pageable, myId);
                break;
            case "declined":
                page = postsRepository.findMyDeclinedPosts(pageable, myId);
                break;
            default:
            case "published":
                page = postsRepository.findMyPublishedPosts(pageable, myId);
        }
        posts.addAll(page.getContent());
        myPosts.setCount(page.getTotalElements());
        List<PostExternal> moderatorPosts = posts.stream()
                .map(mapperService::convertPostToDto)
                .collect(Collectors.toList());

        myPosts.setPosts(page.getContent().stream().map(mapperService::convertPostToDto)
                .collect(Collectors.toList()));

        myPosts.setPosts(moderatorPosts);

        return myPosts;
    }

    public User getAuthorizedUser(Principal principal) {
        User user = new User();
        if (principal != null) {
            user = userRepository.findByEmail(principal.getName()).get();
        }
        return user;
    }

    public AuthCheckResponse getAuthCheck(Principal principal) {
        if (principal == null) {
            AuthCheckResponse authCheck = new AuthCheckResponse();
            authCheck.setResult(false);
            return authCheck;
        }
        return getResponse(principal.getName());
    }

    private AuthCheckResponse getResponse(String email) {
        main.model.User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));
        UserExternal userDto = mapperService.convertUserToDto(currentUser);
        AuthCheckResponse authCheck = new AuthCheckResponse();
        authCheck.setResult(true);
        authCheck.setUser(userDto);
        return authCheck;
    }

    public RegResponse getRegPostResponse(RegPostRequest regRequest, Principal principal) {
        RegResponse regResponse = new RegResponse();
        Map<String, String> errors = new HashMap<>();
        String title = regRequest.getTitle();
        if (title.length() < TITLE_LENGTH) {
            errors.put("title", "Название поста короче 3 символов");
        }
        String text = regRequest.getText();
        if (text.length() < TEXT_LENGTH) {
            errors.put("text", "Текст поста короче 50 символов");
        }
        if (errors.isEmpty()) {
            regResponse.setResult(true);
            Posts post = new Posts();
            post.setIsActive(regRequest.getActive());
            ModerationStatus status =
                    ((!apiGeneralService.isPostPremoderated() || getAuthorizedUser(principal)
                            .getIsModerator() == 1) && regRequest.getActive() == 1) ?
                            ModerationStatus.ACCEPTED : ModerationStatus.NEW;
            post.setStatus(status);
            post.setModeratorId((getAuthorizedUser(principal).
                    getIsModerator() == 1) ? getAuthorizedUser(principal).getId() : null);
            post.setUser(getAuthorizedUser(principal));

            LocalDateTime localDateTime = LocalDateTime.
                    ofEpochSecond(regRequest.getTimestamp(), 0, ZoneOffset.UTC);
            int result = localDateTime.compareTo(LocalDateTime.now());
            LocalDateTime time = (result < 0) ? LocalDateTime.now() : localDateTime;
            post.setTimestamp(time);

            post.setTitle(title);
            post.setText(text);
            post.setViewCount(0);

            List<Tags> tags = new ArrayList<>();
            for (String t : regRequest.getTags()) {
                if (tagsRepository.findTagByName(t) == null) {
                    Tags newTag = new Tags();
                    newTag.setName(t);
                    tagsRepository.save(newTag);
                }
                tags.add(tagsRepository.findTagByName(t));
            }
            post.setTags(tags);
            postsRepository.save(post);
        } else {
            regResponse.setResult(false);
            regResponse.setErrors(errors);
        }
        return regResponse;
    }

    public RegResponse getUpdatePostResponse(int id, RegPostRequest regRequest, Principal principal) {
        RegResponse regResponse = new RegResponse();
        Map<String, String> errors = new HashMap<>();
        String title = regRequest.getTitle();
        if (title.length() < TITLE_LENGTH) {
            errors.put("title", "Название поста короче 3 символов");
        }
        String text = regRequest.getText();
        if (text.length() < TEXT_LENGTH) {
            errors.put("text", "Текст поста короче 50 символов");
        }
        if (errors.isEmpty()) {
            regResponse.setResult(true);
            Posts post = new Posts();
            post.setId(id);
            post.setIsActive(regRequest.getActive());
            ModerationStatus status =
                    ((!apiGeneralService.isPostPremoderated() || getAuthorizedUser(principal)
                            .getIsModerator() == 1) && regRequest.getActive() == 1) ? ModerationStatus.ACCEPTED : ModerationStatus.NEW;
            post.setStatus(status);
            post.setModeratorId((getAuthorizedUser(principal).getIsModerator() == 1) ?
                    getAuthorizedUser(principal).getId() : null);
            post.setUser(getAuthorizedUser(principal));

            LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(regRequest.getTimestamp(), 0, ZoneOffset.UTC);
            int result = localDateTime.compareTo(LocalDateTime.now());
            LocalDateTime time = (result < 0) ? LocalDateTime.now() : localDateTime;
            post.setTimestamp(time);

            post.setTitle(title);
            post.setText(text);
            post.setViewCount(0);

            List<Tags> tags = new ArrayList<>();
            for (String t : regRequest.getTags()) {
                if (tagsRepository.findTagByName(t) == null) {
                    Tags newTag = new Tags();
                    newTag.setName(t);
                    tagsRepository.save(newTag);
                }
                tags.add(tagsRepository.findTagByName(t));
            }
            post.setTags(tags);
            postsRepository.save(post);

        } else {
            regResponse.setResult(false);
            regResponse.setErrors(errors);
        }
        return regResponse;
    }

    public ResultResponse getLike(LikeDislikeRequest request, Principal principal) {
        Optional<PostVotes> like = postVotesRepository.checkVote(request.getPostId(),
                userRepository.findByEmail(principal.getName()).get().getId());
        if (like.isPresent()) {
            if (like.get().getValue() == 1) {
                return getFalse();
            } else if (like.get().getValue() == -1) {
                return getChangeTolike(like.get());
            }
        }
        PostVotes newPV = new PostVotes();
        newPV.setPost(postsRepository.getOne(request.getPostId()));
        newPV.setUser(userRepository.findByEmail(principal.getName()).get());
        newPV.setTime(LocalDateTime.now());
        newPV.setValue(1);
        postVotesRepository.save(newPV);
        ResultResponse response = new ResultResponse();
        response.setResult(true);
        return response;
    }

    public ResultResponse getDislike(LikeDislikeRequest request, Principal principal) {
        Optional<PostVotes> disLike = postVotesRepository.checkVote(request.getPostId(),
                userRepository.findByEmail(principal.getName()).get().getId());
        if (disLike.isPresent()) {
            if (disLike.get().getValue() == -1) {
                return getFalse();
            } else if (disLike.get().getValue() == 1) {
                return getChangeToDislike(disLike.get());
            }
        }
        PostVotes newPV = new PostVotes();
        newPV.setPost(postsRepository.getOne(request.getPostId()));
        newPV.setUser(userRepository.findByEmail(principal.getName()).get());
        newPV.setTime(LocalDateTime.now());
        newPV.setValue(-1);
        postVotesRepository.save(newPV);
        ResultResponse response = new ResultResponse();
        response.setResult(true);
        return response;
    }

    public ResultResponse getFalse() {
        ResultResponse response = new ResultResponse();
        response.setResult(false);
        return response;
    }

    public ResultResponse getChangeTolike(PostVotes like) {
        PostVotes newPV = new PostVotes();
        newPV.setId(like.getId());
        newPV.setPost(like.getPost());
        newPV.setUser(like.getUser());
        newPV.setTime(LocalDateTime.now());
        newPV.setValue(1);
        postVotesRepository.save(newPV);
        ResultResponse response = new ResultResponse();
        response.setResult(true);
        return response;
    }

    public ResultResponse getChangeToDislike(PostVotes disLike) {
        PostVotes newPV = new PostVotes();
        newPV.setId(disLike.getId());
        newPV.setPost(disLike.getPost());
        newPV.setUser(disLike.getUser());
        newPV.setTime(LocalDateTime.now());
        newPV.setValue(-1);
        postVotesRepository.save(newPV);
        ResultResponse response = new ResultResponse();
        response.setResult(true);
        return response;
    }

    public Posts getOptionalPostById(int id, Principal principal) {
        return postsRepository.getOptionalPostById(id);
    }
}
