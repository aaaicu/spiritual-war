package com.donamdong.spiritualwar.domain;

import lombok.Getter;

@Getter
public enum Role {
    CITIZEN("시민"),
    DEVIL("악마"),
    HOLY("성령 충만한 자"),
    FAN("팬심 가득 시민"),
    FRIEND("친구가 있는 시민");


    final String value;

    Role(String value) {
        this.value = value;
    }
}
