package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.history.ResGetHistoryDto;
import com.ssafy.letcipe.domain.history.History;
import com.ssafy.letcipe.domain.history.HistoryRepository;
import com.ssafy.letcipe.domain.type.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;

    @Transactional
    public List<ResGetHistoryDto> getHistoryList(Long userId) {
        List<History> historyList = historyRepository.findByUserIdAndIsDeleted(userId, StatusType.N);
        List<ResGetHistoryDto> result = new ArrayList<>();
        for (History history : historyList) {
            result.add(new ResGetHistoryDto(history));
        }
        return result;
    }
}
