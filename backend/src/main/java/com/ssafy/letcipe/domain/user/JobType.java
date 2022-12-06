package com.ssafy.letcipe.domain.user;

import lombok.Getter;

@Getter
public enum JobType {
    STUDENT("student", 0),
    JUBU("jubu", 1),
    WORKER("worker", 2),
    COOK("cook", 3);

    private final String desc;
    private final Integer stateCode;

    JobType(String desc, Integer stateCode) {
        this.desc = desc;
        this.stateCode = stateCode;
    }
}
