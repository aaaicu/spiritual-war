package com.donamdong.spiritualwar.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    GAME_PARTICIPATION_ERROR(HttpStatus.BAD_REQUEST, "게임에 참가할 수 없습니다."),
    GAME_ALREADY_STARTED(HttpStatus.BAD_REQUEST, "이미 게임을 시작하여 나갈 수 없습니다."),
    MEMBER_COUNT_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "인원수가 충분하지 않습니다."),
    NOT_FOUNT_MEMBER(HttpStatus.BAD_REQUEST, "사용자 없음");

    private final HttpStatus httpStatus;
    private final String detail;
}
