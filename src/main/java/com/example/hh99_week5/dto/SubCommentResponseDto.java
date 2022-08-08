package com.example.hh99_week5.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class SubCommentResponseDto {
    private Long id;
    private String content;
    private String author;
    private int likesCnt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
