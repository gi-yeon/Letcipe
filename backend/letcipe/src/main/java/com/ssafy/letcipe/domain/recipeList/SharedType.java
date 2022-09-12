package com.ssafy.letcipe.domain.recipeList;

import lombok.Getter;

@Getter
public enum SharedType {
    Y("public", 0),
    N("private", 1);

    private final String desc;
    private final Integer stateCode;

    SharedType(String desc, Integer stateCode) {
        this.desc = desc;
        this.stateCode = stateCode;
    }
}
