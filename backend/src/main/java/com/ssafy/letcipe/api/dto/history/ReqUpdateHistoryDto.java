package com.ssafy.letcipe.api.dto.history;

import com.ssafy.letcipe.domain.history.ProcessType;
import lombok.Getter;

@Getter
public class ReqUpdateHistoryDto {
    private Long id;
    private ProcessType process;
}
