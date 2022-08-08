package com.example.hh99_week5.controller;

import com.example.hh99_week5.dto.CommentRequestDto;
import com.example.hh99_week5.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<String> createComment(@PathVariable Long postId,
                                                @RequestBody CommentRequestDto commentRequestDto){
        return commentService.createComment(postId, commentRequestDto);
    }

    @PostMapping("/comment/{commentId}/comment")
    public ResponseEntity<String> createSubComment(@PathVariable Long commentId,
                                                   @RequestBody CommentRequestDto commentRequestDto){
        return commentService.createSubComment(commentId, commentRequestDto);
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long commentId,
                                                @RequestBody CommentRequestDto commentRequestDto){
        return commentService.updateComment(commentId, commentRequestDto);
    }

    @PutMapping("/subComment/{subCommentId}")
    public ResponseEntity<String> updateSubComment(@PathVariable Long subCommentId,
                                                   @RequestBody CommentRequestDto commentRequestDto){
        return commentService.updateSubComment(subCommentId, commentRequestDto);
    }


    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId){
        return commentService.deleteComment(commentId);
    }

    @DeleteMapping("/subComment/{subCommentId}")
    public ResponseEntity<String> deleteSubComment(@PathVariable Long subCommentId){
        return commentService.deleteSubComment(subCommentId);
    }
}
