package com.sparcs.team1.global.exception;

import com.sparcs.team1.global.exception.enums.ErrorType;
import com.sparcs.team1.global.exception.model.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 비즈니스 로직에서 발생한 예외 (언체크)
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorType> handleBusinessException(CustomException e) {
        log.error("GlobalExceptionHandler catch CustomException: {}", e.getErrorType().getMessage());
        return ResponseEntity
                .status(e.getErrorType().getHttpStatus())
                .body(e.getErrorType());
    }

    // @Valid에서 발생한 예외
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorType> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("GlobalExceptionHandler catch MethodArgumentNotValidException: {}", e.getMessage());
        return ResponseEntity
                .status(e.getStatusCode())
                .body(ErrorType.REQUEST_VALIDATION_ERROR);
    }
}
