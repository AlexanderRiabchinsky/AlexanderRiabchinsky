package main.controller;

import main.api.request.RegPostRequest;
import main.api.response.*;
import main.model.Posts;
import main.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiPostController {

    private final ApiPostService postService;

    public ApiPostController(ApiPostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post")
    public ResponseEntity<PostResponse> byMode(@RequestParam(defaultValue = "0") int offset,
                                               @RequestParam(defaultValue = "10") int limit,
                                               @RequestParam (required = false, defaultValue = "recent") String mode) {
        return ResponseEntity.ok(postService.getPostByMode(offset, limit, mode));
    }

    @GetMapping("/post/search")
    public ResponseEntity <PostResponse> apiPostSearch(@RequestParam(defaultValue = "0") int offset,
                                                      @RequestParam(defaultValue = "10") int limit,
                                                      @RequestParam (required = false) String query) { return ResponseEntity.ok(postService.getPostSearch(offset,limit,query));
    }

    @GetMapping("/post/byDate")
    public ResponseEntity<PostResponse> byDate(@RequestParam(defaultValue = "0") int offset,
                                               @RequestParam(defaultValue = "10") int limit,
                                               @RequestParam (required = false) String date) {return ResponseEntity.ok(postService.getPostByDate(offset, limit, date));
    }

    @GetMapping("/post/byTag")
    public ResponseEntity<PostResponse> byTag(@RequestParam(defaultValue = "0") int offset,
                                              @RequestParam(defaultValue = "10") int limit,
                                              @RequestParam (required = false) String tag) {return ResponseEntity.ok(postService.getPostByTag(offset, limit, tag));
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostIDResponse> postIdCheck(@PathVariable int id, Principal principal) {return ResponseEntity.ok(postService.getPostById(id,principal));
    }

    @PreAuthorize("hasAuthority('user:moderate')")
    @GetMapping("/post/moderation")
    public ResponseEntity<PostResponse> moderationCheck(@RequestParam(defaultValue = "0") int offset,
                                                        @RequestParam(defaultValue = "10") int limit,
                                                        @RequestParam (defaultValue = "new") String status,
                                                        Principal principal){return ResponseEntity.ok(postService.getModerationData(offset, limit, status, principal));
    }

    @PreAuthorize("hasAuthority('user:write')")
    @GetMapping("/post/my")
    public ResponseEntity<PostResponse> postMy(@RequestParam(defaultValue = "0") int offset,
                                                        @RequestParam(defaultValue = "10") int limit,
                                                        @RequestParam (required = false) String status,
                                                        Principal principal){return ResponseEntity.ok(postService.getMyPosts(offset, limit, status, principal));
    }

  @PreAuthorize("hasAuthority('user:write')")
    @PostMapping("/post")
    public ResponseEntity<RegResponse> newPost(@RequestBody RegPostRequest regPostRequest,
                                               Principal principal) {
        return ResponseEntity.ok(postService.getRegPostResponse(regPostRequest,principal));
    }

  @PreAuthorize("hasAuthority('user:write')")
  @PutMapping("/post/{id}")
  public ResponseEntity<RegResponse> updatePost(@PathVariable int id,
                                                @RequestBody RegPostRequest regPostRequest,
                                                Principal principal) {
    Optional<Posts> optionalPost = Optional.ofNullable(postService.getOptionalPostById(id, principal));
    if (optionalPost.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return ResponseEntity.ok(postService.getUpdatePostResponse(optionalPost.get().getId(), regPostRequest,principal));
  }

}
