package com.example.hh99_week5.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class PostResponseDto {

    private final Long id;
    private final String title;
    private final String author;
    private final String imgUrl;
    private final int likesCnt;
    private final List<TodoResponseDto> todoList;
    private final List<CommentResponseDto> commentList;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}
