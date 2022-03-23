package main.controller;

import main.api.response.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiPostController {
    private final PostResponse postResponse;
    private final PostSearchResponse postSearchResponse;
    private final PostByDateResponse postByDateResponse;
    private final PostByTagResponse postByTagResponse;
    private final PostIDResponse postIDResponse;
    private final PostModeration postModeration;

    public ApiPostController(PostResponse postResponse, PostSearchResponse postSearchResponse, PostByDateResponse postByDateResponse, PostByTagResponse postByTagResponse, PostIDResponse postIDResponse, PostModeration postModeration){this.postResponse=postResponse;
        this.postSearchResponse = postSearchResponse;
        this.postByDateResponse = postByDateResponse;
        this.postByTagResponse = postByTagResponse;
        this.postIDResponse = postIDResponse;
        this.postModeration = postModeration;
    }
    @GetMapping("/api/post")
    public PostResponse postCheck(){return postResponse;}
    @GetMapping("/api/post/search")
    public PostSearchResponse postSearchCheck(){return postSearchResponse;}
    @GetMapping("/api/post/byDate")
    public PostByDateResponse postByDateCheck(){return postByDateResponse;}
    @GetMapping("/api/post/byTag")
    public PostByTagResponse postByTagCheck(){return postByTagResponse;}
    @GetMapping("/api/post/{ID}")
    public PostIDResponse postIDCheck(){return postIDResponse;}
    @GetMapping("/api/post/moderation")
    public PostModeration moderationCheck(){return postModeration;}

}
