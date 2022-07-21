package main.controller;

import main.api.response.*;
import main.model.Posts;
import main.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class ApiPostController {

    private final ApiPostService postService;

    public ApiPostController(ApiPostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<PostResponse> byMode(@RequestParam(defaultValue = "0") int offset,
                                               @RequestParam(defaultValue = "10") int limit,
                                               @RequestParam (required = false, defaultValue = "recent") String mode) {

        return ResponseEntity.ok(postService.getPostByMode(offset, limit, mode));
    }

    @GetMapping("/post/search")
    @PreAuthorize("hasAuthority('user:moderate')")
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

    @GetMapping("/post/{ID}")
    public ResponseEntity<PostIDResponse> postIdCheck(@PathVariable("ID") int ID,
                                                      @RequestParam (required = false) Principal principal) {return ResponseEntity.ok(postService.getPostById(ID,principal));
    }

    @GetMapping("/post/moderation")
    public ResponseEntity<PostResponse> moderationCheck(@RequestParam(defaultValue = "0") int offset,
                                                        @RequestParam(defaultValue = "10") int limit,
                                                        @RequestParam (required = false) String status,
                                                        @RequestParam (required = false) Principal principal){return ResponseEntity.ok(postService.getModerationData(offset, limit, status, principal));
    }

}
