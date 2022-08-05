package com.example.hh99_week5.dto;

import com.example.hh99_week5.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {

    private final Long id;
    private final String title;
    private final String author;
    private final List<TodoResponseDto> todoList;
    private final List<CommentResponseDto> commentList;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getAuthor();
        this.todoList = post.getTodoList().stream().map(TodoResponseDto::new).collect(Collectors.toList());
        this.commentList = post.getCommentList().stream().map(CommentResponseDto::new).collect(Collectors.toList());
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getCreatedAt();
    }
}
