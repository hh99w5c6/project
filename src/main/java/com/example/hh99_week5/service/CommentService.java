package com.example.hh99_week5.service;

import com.example.hh99_week5.dto.CommentRequestDto;
import com.example.hh99_week5.entity.Comment;
import com.example.hh99_week5.entity.Post;
import com.example.hh99_week5.repository.CommentRepository;
import com.example.hh99_week5.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public void createComment(Long postId, CommentRequestDto commentRequestDto) {
        Post post = postRepository.findById(postId).
                orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));
        commentRepository.save(Comment.builder()
                .author(commentRequestDto.getAuthor())
                .content(commentRequestDto.getContent())
                .post(post)
                .build());
    }
}
