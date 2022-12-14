package com.example.hh99_week5.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String content;
    private String author;
    private int likesCnt;
    private List<SubCommentResponseDto> subCommentList;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
