package com.example.hh99_week5.controller;

import com.example.hh99_week5.dto.PostRequestDto;
import com.example.hh99_week5.dto.PostResponseDto;
import com.example.hh99_week5.dto.PostsResponseDto;
import com.example.hh99_week5.service.PostService;
import com.example.hh99_week5.service.Scheduler;
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
    private final Scheduler scheduler;

    @GetMapping("/posts") // 전체 게시글 조회
    public List<PostsResponseDto> readPosts(){
        return postService.readPosts();
    }

    @GetMapping("/posts/{postId}") // 선택 게시글 조회
    public PostResponseDto readPost(@PathVariable Long postId){
        return postService.readPost(postId);
    }

    @PostMapping("/post") // 게시글 작성
    public ResponseEntity<String> createPost(@RequestPart PostRequestDto postRequestDto,
                                             @RequestPart MultipartFile multipartFile) throws IOException {
        return postService.createPost(postRequestDto, multipartFile);
    }

    @PutMapping("/post/{postId}") // 게시글 수정
    public ResponseEntity<String> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto){
        return postService.updatePost(postId, postRequestDto);
    }

    @DeleteMapping("/post/{postId}") // 게시글 삭제
    public ResponseEntity<String> deletePost(@PathVariable Long postId){
        return postService.deletePost(postId);
    }

    @DeleteMapping("/post/{postId}/todo/{todoId}") // 선택 게시글의 투두 삭제
    public ResponseEntity<String> deleteTodo(@PathVariable Long postId, @PathVariable Long todoId){
        return postService.deleteTodo(postId, todoId);
    }
}
