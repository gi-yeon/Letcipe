package com.ssafy.letcipe.domain.history;

import com.ssafy.letcipe.api.dto.history.ResGetHistoryDto;
import com.ssafy.letcipe.domain.type.StatusType;
import com.ssafy.letcipe.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUserIdAndIsDeleted(Long userId, StatusType isDeleted);

    //Optional<History> findByUserIdAndProcessAndIsDeleted(User user, ProcessType ready, StatusType n);

    Optional<History> findByUserAndProcessAndIsDeleted(User user, ProcessType ready, StatusType n);
}
