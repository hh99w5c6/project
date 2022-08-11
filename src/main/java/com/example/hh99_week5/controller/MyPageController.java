package com.example.hh99_week5.controller;

import com.example.hh99_week5.dto.MyCommentsDto;
import com.example.hh99_week5.dto.MyPostDto;
import com.example.hh99_week5.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/myPage")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping("/posts") // 내가쓴글 조회하기
    public List<MyPostDto> readMyPosts(){
        return myPageService.readMyPosts();
    }

    @GetMapping("/comments") //내가쓴 댓글 조회하기
    public List<MyCommentsDto> readMyComments(){
        return myPageService.readMyComments();
    }

    @GetMapping("/likes/posts") //내가 좋아요한 게시글 조회하기
    public List<MyPostDto> readMyPostsLikes(){
        return myPageService.readMyPostsLikes();
    }

    @GetMapping("/likes/comments") //내가 좋아요한 댓글 조회하기
    public List<MyCommentsDto> readMyCommentsLikes(){
        return myPageService.readMyCommentsLikes();
    }
}
