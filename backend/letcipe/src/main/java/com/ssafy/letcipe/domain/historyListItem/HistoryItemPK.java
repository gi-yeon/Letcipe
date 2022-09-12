package com.ssafy.letcipe.domain.historyListItem;

import com.ssafy.letcipe.domain.history.History;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryItemPK implements Serializable {
    private History history;
//    private Recipe recipe;
}
