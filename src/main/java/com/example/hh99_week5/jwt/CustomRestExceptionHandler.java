package com.example.hh99_week5.jwt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomRestExceptionHandler {

    @ExceptionHandler(Exception.class) // 번호로 조회했는데 해당 번호의 게시물은 존재하지 않음. 또는 로그인 실패
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("code", "400 Bad Request");
        error.put("message", "유효하지 않거나 존재하지 않는 값입니다. 다시 확인해주세요.");
        return ResponseEntity.badRequest().body(error);
    }
}