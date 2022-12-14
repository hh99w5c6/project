package com.example.hh99_week5.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyCommentsDto {
    private Long id;
    private String content;
    private String author;
    private int likesCnt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
