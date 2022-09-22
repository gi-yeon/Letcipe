package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.*;
import com.ssafy.letcipe.domain.board.Board;
import com.ssafy.letcipe.domain.board.BoardRepository;
import com.ssafy.letcipe.domain.boardComment.BoardComment;
import com.ssafy.letcipe.domain.boardComment.BoardCommentRepository;
import com.ssafy.letcipe.domain.type.StatusType;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.data.domain.PageRequest;
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
    private final BoardCommentRepository boardCommentRepository;
    private final UserRepository userRepository;

    //page로 조회
    @Transactional
    public List<ResGetBoardListDto> getBoardList(int page, int boardNum) throws Exception{
        if(page == 0) throw new Exception("잘못된 요청입니다.");
        PageRequest pageRequest = PageRequest.of(page-1, boardNum);

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
    public ResGetBoardDto getBoard(Long boardId, Long userId) {
        Board board = boardRepository.findByStatusTypeAndId(StatusType.N, boardId).orElseThrow(
                () -> new NullPointerException("해당 게시판이 존재하지 않습니다."));
        List<BoardComment> boardComments = board.getBoardCommentList().stream()
                .filter(c -> c.getStatusType() == StatusType.N).collect(Collectors.toList());
        List<ResGetBoardCommentDto> comments = new ArrayList<>();
        for (BoardComment comment : boardComments) {
            comments.add(ResGetBoardCommentDto.createDto(comment));
        }
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new NullPointerException("해당 유저를 찾을 수 없습니다."));
        return ResGetBoardDto.createDto(board, user, comments);
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
        int rownum = boardCommentRepository.updateStatusType(StatusType.Y, board);
        System.out.println(rownum);
        board.patchBoard();
        boardRepository.save(board);
    }

    @Transactional
    public void postBoardComment(Long boardId, ReqPostBoardCommentDto boardCommentDto, Long userId){
        System.out.println(boardCommentDto.getContent());
        Board board = boardRepository.findByStatusTypeAndId(StatusType.N,boardId).orElseThrow(
                ()-> new NullPointerException("해당 게시판이 존재하지 않습니다."));
        //TODO 현재 로그인되어있는 User의 아이디를 가져오는 것 필요
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new NullPointerException("해당 유저를 찾을 수 없습니다."));
        BoardComment comment = BoardComment.builder()
                .user(user)
                .board(board)
                .content(boardCommentDto.getContent())
                .regTime(LocalDateTime.now())
                .modTime(LocalDateTime.now())
                .statusType(StatusType.N)
                .build();

        boardCommentRepository.save(comment);
    }

    @Transactional
    public void putBoardComment(Long boardCommentId, ReqPutBoardCommentDto boardCommentDto, Long userId) throws Exception {
        BoardComment comment = boardCommentRepository.findByStatusTypeAndId(StatusType.N, boardCommentId).orElseThrow(
                ()-> new NullPointerException("해당 댓글이 존재하지 않습니다."));
        //TODO 현재 로그인되어있는 User의 아이디를 가져오는 것 필요

        User user = userRepository.findById(userId).orElseThrow(
                ()->  new NullPointerException("해당 유저를 찾을 수 없습니다."));
        if(!comment.getUser().getUserId().equals(user.getUserId()))
            throw new Exception("본인 댓글만 수정이 가능합니다.");
        comment.putBoardComment(boardCommentDto.getContent());
    }

    @Transactional
    public void patchBoardComment(Long boardCommentId, Long userId) throws Exception {
        BoardComment comment = boardCommentRepository.findByStatusTypeAndId(StatusType.N, boardCommentId).orElseThrow(
                ()-> new NullPointerException("해당 댓글이 존재하지 않습니다."));
        //TODO 현재 로그인되어있는 User의 아이디를 가져오는 것 필요
        User user = userRepository.findById(userId).orElseThrow(
                ()->  new NullPointerException("해당 유저를 찾을 수 없습니다."));
        if(!comment.getUser().getUserId().equals(user.getUserId()))
            throw new Exception("본인 댓글만 삭제 가능합니다.");
        comment.patchBoardComment();
        boardCommentRepository.save(comment);
    }

}
