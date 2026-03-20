package org.example.nbcam_addvanced_1.domain.post.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.nbcam_addvanced_1.domain.post.model.dto.PostDto;
import org.example.nbcam_addvanced_1.domain.post.model.dto.PostSummaryDto;
import org.example.nbcam_addvanced_1.domain.post.model.request.CreatePostRequestDto;
import org.example.nbcam_addvanced_1.domain.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@AuthenticationPrincipal User user, @RequestBody CreatePostRequestDto request) {
        return ResponseEntity.ok(postService.createPost(user.getUsername(), request.getContent()));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostDto>> getPostListByUsername(@PathVariable String username) {
        return ResponseEntity.ok(postService.getPostListByUsername(username));
    }

    @GetMapping("/user/{username}/detail")
    public ResponseEntity<List<PostSummaryDto>> getPostSummaryListByUsername(@PathVariable String username) {
        return ResponseEntity.ok(postService.getPostSummaryListByUsername(username));
    }

}
