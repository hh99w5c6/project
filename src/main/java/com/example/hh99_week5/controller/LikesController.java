package com.example.hh99_week5.controller;

import com.example.hh99_week5.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/likes/")
public class LikesController {
    private final LikesService likesService;

    @PostMapping("/post/{postId}") // 게시글 좋아요
    public void likesToPost(@PathVariable Long postId){
        likesService.likesToPost(postId);
    }

    @PostMapping("/comment/{commentId}") // 댓글 좋아요
    public void likesToComment(@PathVariable Long commentId){
        likesService.likesToComment(commentId);
    }

    @PostMapping("/subComment/{subCommentId}") //대댓글 좋아요
    public void likesToSubComment(@PathVariable Long subCommentId){
        likesService.likesToSubComment(subCommentId);
    }
}
