package com.ssafy.letcipe.domain.history;

import com.ssafy.letcipe.api.dto.history.ResGetHistoryDto;
import com.ssafy.letcipe.domain.type.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUserIdAndIsDeleted(Long userId, StatusType isDeleted);
}
