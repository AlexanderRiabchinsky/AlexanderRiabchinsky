package main.controller;

import main.api.response.*;
import main.repositories.PostsRepository;
import main.repositories.UsersRepository;
import main.service.ApiPostSearchService;
import main.service.ApiPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiPostController {

    private final ApiPostService postResponse;
    private ApiPostSearchService postSearchResponse;

    public ApiPostController(PostsRepository postsRepository, UsersRepository usersRepository, ApiPostService postResponse,ApiPostSearchService postSearchResponse) {
        this.postResponse = postResponse;
        this.postSearchResponse = postSearchResponse;
    }

    @GetMapping("/api/post")
    private PostResponse apiPost() {
        return postResponse.getPostResponse();
    }

    @GetMapping("/api/post/search")
   private PostSearchResponse apiPostSearch() { return postSearchResponse.getPostSearchResponse();
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
