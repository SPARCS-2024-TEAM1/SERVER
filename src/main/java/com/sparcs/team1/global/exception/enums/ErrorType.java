package com.sparcs.team1.global.exception.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorType {

    /**
     * 400 BAD REQUEST
     */
    REQUEST_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "40001", "유효하지 않은 요청입니다.(Valid 실패)"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
