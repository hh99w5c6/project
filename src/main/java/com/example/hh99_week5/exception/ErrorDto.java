package com.example.hh99_week5.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorDto {

    private final int status;
    private final String message;
}
