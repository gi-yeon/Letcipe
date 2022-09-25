package com.ssafy.letcipe.domain.user;

import lombok.Getter;

@Getter
public enum GenderType {
    M("male", 0),
    F("female", 1);

    private final String desc;
    private final Integer stateCode;

    GenderType(String desc, Integer stateCode) {
        this.desc = desc;
        this.stateCode = stateCode;
    }
}
