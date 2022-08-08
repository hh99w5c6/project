package com.example.hh99_week5.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyPostDto {
    private Long id;
    private String title;
    private String author;
    private String imgUrl;
    private int likesCnt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
