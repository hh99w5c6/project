package com.example.hh99_week5.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //400 BAD_REQUEST 잘못된 요청
    INVALID_PARAMETER(400, "파라미터 값을 확인해주세요."),

    //404 NOT_FOUND 잘못된 리소스 접근
    POST_NOT_FOUND(404, "존재하지 않는 게시글 ID 입니다."),

    //500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "로그인 입력값을 다시 확인해주세요!");

    private final int status;
    private final String message;
}