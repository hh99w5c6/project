package com.example.hh99_week5.controller;

import com.example.hh99_week5.dto.CommentRequestDto;
import com.example.hh99_week5.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post/{postId}")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    public void createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto){
        commentService.createComment(postId, commentRequestDto);
    }
}
