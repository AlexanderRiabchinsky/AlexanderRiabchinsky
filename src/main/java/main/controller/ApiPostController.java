package main.controller;

import main.api.response.*;
import main.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiPostController {

    private final ApiPostService postResponse;

    public ApiPostController(ApiPostService postResponse) {
        this.postResponse = postResponse;
    }

    @GetMapping("/api/post")
    public ResponseEntity<PostResponse> byMode(@RequestParam(defaultValue = "0") int offset,
                                               @RequestParam(defaultValue = "10") int limit,
                                               @RequestParam String mode) {return ResponseEntity.ok(postResponse.getPostByMode(offset, limit, mode));
    }
//    private PostResponse apiPost() {
//        return postResponse.getPostResponse();
//    }

    @GetMapping("/api/post/search")
   public ResponseEntity <PostResponse> apiPostSearch(@RequestParam(defaultValue = "0") int offset,
                                                      @RequestParam(defaultValue = "10") int limit,
                                                      @RequestParam String query) { return ResponseEntity.ok(postResponse.getPostSearch(offset,limit,query));
    }

    @GetMapping("/api/post/byDate")
    public ResponseEntity<PostResponse> byDate(@RequestParam(defaultValue = "0") int offset,
                                               @RequestParam(defaultValue = "10") int limit,
                                               @RequestParam String date) {return ResponseEntity.ok(postResponse.getPostByDate(offset, limit, date));
    }

    @GetMapping("/api/post/byTag")
    public ResponseEntity<PostResponse> byTag(@RequestParam(defaultValue = "0") int offset,
                                              @RequestParam(defaultValue = "10") int limit,
                                              @RequestParam String tag) {return ResponseEntity.ok(postResponse.getPostByTag(offset, limit, tag));
    }

    @GetMapping("/api/post/{ID}")
    public ResponseEntity<PostIDResponse> postIdCheck(@RequestParam int id) {return ResponseEntity.ok(postResponse.getPostById(id));
    }

    @GetMapping("/api/post/moderation")
    public PostResponse moderationCheck() {return postResponse.getModerationData();
    }

}
