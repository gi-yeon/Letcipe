package com.ssafy.letcipe.domain.comment;

import lombok.Getter;
@Getter
public enum BoardType {

    RECIPE("recipe", 0),
    BOARD("board", 1);

    private final String desc;
    private final Integer stateCode;

    BoardType(String desc, Integer stateCode) {
        this.desc = desc;
        this.stateCode = stateCode;
    }
}
