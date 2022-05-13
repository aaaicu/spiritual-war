package com.donamdong.spiritualwar.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    MEMBER_COUNT_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "인원수가 충분하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
