package com.ssafy.letcipe.domain.boardComment;

import com.ssafy.letcipe.domain.board.Board;
import com.ssafy.letcipe.domain.type.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {
    List<BoardComment> findByBoard(Board board);
    Optional<BoardComment> findByStatusTypeAndId(StatusType statusType, Long boardCommentId);

    @Modifying
    @Query("UPDATE BoardComment bc SET bc.statusType = :statusType WHERE bc.board = :board")
   int updateStatusType(@Param("statusType") StatusType statusType, @Param("board") Board board);
}
