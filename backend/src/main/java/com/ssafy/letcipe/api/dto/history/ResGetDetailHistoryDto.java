package com.ssafy.letcipe.api.dto.history;

import com.ssafy.letcipe.domain.history.History;
import com.ssafy.letcipe.domain.history.ProcessType;
import com.ssafy.letcipe.domain.historyIngredient.HistoryIngredient;
import com.ssafy.letcipe.domain.historyItem.HistoryItem;
import lombok.Getter;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ResGetDetailHistoryDto {
    private Long id;
    private LocalDateTime regTime;
    private ProcessType process;
    private String review;
    private List<ResGetHistoryItemDto> historyItems;
    private List<ResGetHistoryIngredientDto> historyIngredients;

    public ResGetDetailHistoryDto(History history, List<ResGetHistoryItemDto> historyItems, List<ResGetHistoryIngredientDto> resGetHistoryIngredientDtoList) {
        this.id = history.getId();
        this.regTime = history.getRegTime();
        this.process = history.getProcess();
        this.review = history.getReview();
        this.historyItems = historyItems;
        this.historyIngredients = resGetHistoryIngredientDtoList;
    }
}
