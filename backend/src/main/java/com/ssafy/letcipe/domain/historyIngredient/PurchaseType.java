package com.ssafy.letcipe.domain.historyIngredient;

import lombok.Getter;

@Getter
public enum PurchaseType {
    N("activated", 0),
    Y("purchased", 1);

    private final String desc;
    private final Integer stateCode;

    PurchaseType(String desc, Integer stateCode) {
        this.desc = desc;
        this.stateCode = stateCode;
    }
}
