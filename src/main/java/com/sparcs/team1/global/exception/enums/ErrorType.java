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
    INVALID_PHONE_NUMBER_ERROR(HttpStatus.BAD_REQUEST, "40002", "잘못된 휴대전화 번호 양식입니다."),
    INVALID_VERIFICATION_CODE_ERROR(HttpStatus.BAD_REQUEST, "40003", "인증번호가 올바르지 않습니다."),
    INVALID_NICKNAME_ERROR(HttpStatus.BAD_REQUEST, "40004", "닉네임이 조건을 만족하지 않습니다."),

    /**
     * 404 NOT FOUND
     */
    NOT_FOUND_VERIFICATION_REQUEST_HISTORY(HttpStatus.NOT_FOUND, "40401", "인증 요청 이력이 존재하지 않습니다."),
    NOT_FOUND_MEMBER_ERROR(HttpStatus.NOT_FOUND, "40402", "존재하지 않는 회원입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
