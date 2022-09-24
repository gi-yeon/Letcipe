package com.ssafy.letcipe.domain.board;

import com.ssafy.letcipe.domain.type.StatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // data jpa에서 제공하는 pagenation
    // status type에 맞는 애들만 추려서, 알아서 pagenation을 해서 보내줌.
    Page<Board> findByStatusType(StatusType statusType, Pageable pageable);

    Optional<Board> findByStatusTypeAndId(StatusType statusType, Long boardId);

}
