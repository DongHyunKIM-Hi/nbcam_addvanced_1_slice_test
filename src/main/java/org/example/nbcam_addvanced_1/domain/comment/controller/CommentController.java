package org.example.nbcam_addvanced_1.domain.comment.controller;

import javax.xml.stream.events.Comment;
import lombok.RequiredArgsConstructor;
import org.example.nbcam_addvanced_1.domain.comment.model.dto.CommentDto;
import org.example.nbcam_addvanced_1.domain.comment.model.request.CreateCommentRequestDto;
import org.example.nbcam_addvanced_1.domain.comment.service.CommentService;
import org.example.nbcam_addvanced_1.domain.post.model.dto.PostDto;
import org.example.nbcam_addvanced_1.domain.post.model.request.CreatePostRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId, @RequestBody CreateCommentRequestDto request){
        return ResponseEntity.ok(commentService.createComment(postId,request.getContent()));
    }

}
