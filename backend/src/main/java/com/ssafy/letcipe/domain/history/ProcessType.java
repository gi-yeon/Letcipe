package com.ssafy.letcipe.domain.history;

import lombok.Getter;

@Getter
public enum ProcessType {
    READY("ready", 0),
    EATING("eating", 1),
    END("end", 2);

    private final String desc;
    private final Integer stateCode;

    ProcessType(String desc, Integer stateCode) {
        this.desc = desc;
        this.stateCode = stateCode;
    }
}
