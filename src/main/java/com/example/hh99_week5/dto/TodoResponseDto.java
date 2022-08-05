package com.example.hh99_week5.dto;

import com.example.hh99_week5.entity.Todo;
import lombok.Getter;

@Getter
public class TodoResponseDto {
    private final Long id;
    private final String content;
    private final boolean done;

    public TodoResponseDto(Todo todo){
        this.id = todo.getId();
        this.content = todo.getContent();
        this.done = todo.isDone();
    }
}
