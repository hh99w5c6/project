package com.example.hh99_week5.controller;

import com.example.hh99_week5.dto.PostRequestDto;
import com.example.hh99_week5.dto.PostResponseDto;
import com.example.hh99_week5.dto.PostsResponseDto;
import com.example.hh99_week5.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<String> createPost(@RequestPart PostRequestDto postRequestDto,
                                             @RequestPart MultipartFile multipartFile) throws IOException {
        return postService.createPost(postRequestDto, multipartFile);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto){
        return postService.updatePost(postId, postRequestDto);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId){
        return postService.deletePost(postId);
    }

    @DeleteMapping("/post/{postId}/todo/{todoId}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long postId, @PathVariable Long todoId){
        return postService.deleteTodo(postId, todoId);
    }
}
