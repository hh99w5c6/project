package com.example.hh99_week5.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class PostsResponseDto {
    private final Long id;
    private final String title;
    private final String author;
    private final int commentsCnt;
    private final int likesCnt;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}
