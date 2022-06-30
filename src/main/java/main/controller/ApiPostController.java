package main.controller;

import main.api.response.*;
import main.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiPostController {

    private final ApiPostService postResponse;
    private final ApiPostSearchService postSearchResponse;
    private final ApiPostByDateService postByDateResponse;
    private final ApiPostByTagService postByTagResponse;
    private final ApiPostByIdService postIDResponse;
    private final PostModerationService postModeration;

    public ApiPostController(ApiPostService postResponse, ApiPostSearchService postSearchResponse, ApiPostByDateService postByDateResponse, ApiPostByTagService postByTagResponse, ApiPostByIdService postIDResponse, PostModerationService postModeration) {
        this.postResponse = postResponse;
        this.postSearchResponse = postSearchResponse;
        this.postByDateResponse = postByDateResponse;
        this.postByTagResponse = postByTagResponse;
        this.postIDResponse = postIDResponse;
        this.postModeration = postModeration;
    }

    @GetMapping("/api/post")
    private PostResponse apiPost() {
        return postResponse.getPostResponse();
    }

    @GetMapping("/api/post/search")
   private PostSearchResponse apiPostSearch() { return postSearchResponse.getPostSearchResponse();
    }

    @GetMapping("/api/post/byDate")
    private PostByDateResponse apiPostByDateSearch() {return postByDateResponse.getPostByDateResponse();
    }

    @GetMapping("/api/post/byTag")
    public PostByTagResponse apiPostByTagResponse() {return postByTagResponse.getPostByTagResponse();
    }

    @GetMapping("/api/post/{ID}")
    public PostIDResponse postIdCheck(@PathVariable int id) {return postIDResponse.getPostByIdResponse(id);
    }

    @GetMapping("/api/post/moderation")
    public PostModeration moderationCheck() {return postModeration.getModerationData();
    }

}
