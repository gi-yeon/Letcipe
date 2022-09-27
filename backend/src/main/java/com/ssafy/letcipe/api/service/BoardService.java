package com.ssafy.letcipe.api.service;


import com.ssafy.letcipe.api.dto.board.ResGetBoardDto;
import com.ssafy.letcipe.api.dto.board.ResGetBoardListDto;
import com.ssafy.letcipe.domain.board.Board;
import com.ssafy.letcipe.domain.board.BoardRepository;
import com.ssafy.letcipe.domain.comment.BoardType;
import com.ssafy.letcipe.domain.comment.CommentRepository;
import com.ssafy.letcipe.domain.type.StatusType;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    //page로 조회
    @Transactional
    public List<ResGetBoardListDto> getBoardList(Pageable pageable) throws Exception{
        if(pageable.getPageNumber() == 0) throw new Exception("잘못된 요청입니다.");
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize());

//      int firstP = pageRequest.getPageNumber()-1;
//      PageRequest P = PageRequest.of(firstP, pageRequest.getPageSize());
        List<Board> boardList = boardRepository.findByStatusType(StatusType.N, pageRequest)
                .stream()
                .collect(Collectors.toList());
        List<ResGetBoardListDto> boardListRes = new ArrayList<>();
        for(Board board : boardList) {
            boardListRes.add(
                    ResGetBoardListDto.builder()
                            .id(board.getId())
                            .likes(board.getLikes())
                            .nickname(board.getUser().getNickname())
                            .title(board.getTitle())
                            .regTime(board.getRegTime())
                            .build()
            );

        }
        return boardListRes;
    }



    @Transactional(readOnly = true)
    public ResGetBoardDto getBoard(Long boardId) {
        Board board = boardRepository.findByStatusTypeAndId(StatusType.N, boardId).orElseThrow(
                () -> new NullPointerException("해당 게시판이 존재하지 않습니다."));

        return ResGetBoardDto.createDto(board);
    }




    @Transactional
    public void postBoard(String title, String content, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 유저를 찾을 수 없습니다.")
        );
        Board board = Board.builder()
                .user(user)
                .title(title)
                .content(content)
                .statusType(StatusType.N)
                .regTime(LocalDateTime.now())
                .build();
        boardRepository.save(board);
    }

    @Transactional
    public void putBoard(Long boardId, String title, String content, Long userId) throws Exception{
        Board board = boardRepository.findByStatusTypeAndId(StatusType.N,boardId).orElseThrow(
                () -> new NullPointerException("해당 게시글을 찾을 수 없습니다.")
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 유저를 찾을 수 없습니다.")
        );
        if(!board.getUser().getUserId().equals(user.getUserId()))
            throw new Exception("본인 게시글만 수정이 가능합니다.");
        board.putBoard(title, content);
        boardRepository.save(board);
    }
    @Transactional
    public void patchBoard(Long boardId, Long userId) throws Exception {
        Board board = boardRepository.findByStatusTypeAndId(StatusType.N, boardId).orElseThrow(
                () -> new NullPointerException("해당 게시글을 찾을 수 없습니다.")
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 유저를 찾을 수 없습니다.")
        );

        if(!board.getUser().getUserId().equals(user.getUserId()))
            throw new Exception("본인 게시글만 삭제가 가능합니다.");
        int rownum = commentRepository.updateStatusType(StatusType.Y, board.getId(), BoardType.BOARD);
        System.out.println(rownum);
        board.patchBoard();
        boardRepository.save(board);
    }

}
