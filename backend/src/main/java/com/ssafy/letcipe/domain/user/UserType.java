package com.ssafy.letcipe.domain.user;

import lombok.Getter;

@Getter
public enum UserType {
    USER("user", 0),
    ADMIN("admin", 1);

    private final String desc;
    private final Integer stateCode;

    UserType(String desc, Integer stateCode) {
        this.desc = desc;
        this.stateCode = stateCode;
    }
}
