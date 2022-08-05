package com.example.hh99_week5.dto;

import com.example.hh99_week5.entity.Todo;
import lombok.Getter;

import java.util.List;

@Getter
public class PostRequestDto {
    private String title;
    private List<Todo> todoList;
    private String author;
}
