package com.ssafy.letcipe.domain.type;

public enum StatusType {
    N("activated",0),
    Y("deleted",1);
    private final String desc;
    private final Integer stateCode;
    StatusType(String desc, Integer stateCode) {
        this.desc = desc;
        this.stateCode = stateCode;
    }
}
