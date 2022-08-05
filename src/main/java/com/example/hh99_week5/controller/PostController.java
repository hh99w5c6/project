package com.example.hh99_week5.controller;

import com.example.hh99_week5.dto.PostRequestDto;
import com.example.hh99_week5.dto.PostResponseDto;
import com.example.hh99_week5.dto.PostsResponseDto;
import com.example.hh99_week5.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public List<PostsResponseDto> readPosts(){
        return postService.readPosts();
    }

    @GetMapping("/post/{postId}")
    public PostResponseDto readPost(@PathVariable Long postId){
        return postService.readPost(postId);
    }

    @PostMapping("/post")
    public void createPost(@RequestBody PostRequestDto postRequestDto){
        postService.createPost(postRequestDto);
    }
}
