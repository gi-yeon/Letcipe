package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.ReqPostBoardCommentDto;
import com.ssafy.letcipe.api.dto.ReqPutBoardCommentDto;
import com.ssafy.letcipe.api.dto.ResGetBoardCommentDto;
import com.ssafy.letcipe.api.dto.ResGetBoardDto;
import com.ssafy.letcipe.domain.board.Board;
import com.ssafy.letcipe.domain.board.BoardRepository;
import com.ssafy.letcipe.domain.boardComment.BoardComment;
import com.ssafy.letcipe.domain.boardComment.BoardCommentRepository;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ResGetBoardDto getBoard(Long board_id, Long user_id) {
        Board board = boardRepository.findById(board_id).orElseThrow(
                () -> new NullPointerException("해당 게시판이 존재하지 않습니다."));
        List<BoardComment> boardComments = boardCommentRepository.findByBoard(board);
        List<ResGetBoardCommentDto> comments = new ArrayList<>();
        for (BoardComment comment : boardComments) {
            comments.add(ResGetBoardCommentDto.createDto(comment));
        }
        User user = userRepository.findById(user_id).orElseThrow(
                ()-> new NullPointerException("해당 유저를 찾을 수 없습니다."));
        return ResGetBoardDto.createDto(board, user, comments);
    }

    @Transactional
    public void postBoardComment(Long board_id, ReqPostBoardCommentDto boardCommentDto, Long user_id){
        System.out.println(boardCommentDto.getContent());
        Board board = boardRepository.findById(board_id).orElseThrow(
                ()-> new NullPointerException("해당 게시판이 존재하지 않습니다."));
        //TODO 현재 로그인되어있는 User의 아이디를 가져오는 것 필요
        User user = userRepository.findById(user_id).orElseThrow(
                ()-> new NullPointerException("해당 유저를 찾을 수 없습니다."));
        BoardComment comment = BoardComment.builder()
                .user(user)
                .board(board)
                .content(boardCommentDto.getContent())
                .regTime(LocalDateTime.now())
                .modTime(LocalDateTime.now()).build();

        boardCommentRepository.save(comment);
    }

    @Transactional
    public void putBoardComment(Long board_comment_id, ReqPutBoardCommentDto boardCommentDto, Long user_id) throws Exception {
        BoardComment comment = boardCommentRepository.findById(board_comment_id).orElseThrow(
                ()-> new NullPointerException("해당 댓글이 존재하지 않습니다."));
        //TODO 현재 로그인되어있는 User의 아이디를 가져오는 것 필요
        User user = userRepository.findById(user_id).orElseThrow(
                ()->  new NullPointerException("해당 유저를 찾을 수 없습니다."));
        if(!comment.getUser().getUserId().equals(user.getUserId()))
            throw new Exception("본인 댓글만 수정이 가능합니다.");
        comment.putBoardComment(boardCommentDto.getContent());
    }

    @Transactional
    public void deleteBoardComment(Long board_comment_id, Long user_id) throws Exception {
        BoardComment comment = boardCommentRepository.findById(board_comment_id).orElseThrow(
                ()-> new NullPointerException("해당 댓글이 존재하지 않습니다."));
        //TODO 현재 로그인되어있는 User의 아이디를 가져오는 것 필요
        User user = userRepository.findById(user_id).orElseThrow(
                ()->  new NullPointerException("해당 유저를 찾을 수 없습니다."));
        if(!comment.getUser().getUserId().equals(user.getUserId()))
            throw new Exception("본인 댓글만 삭제 가능합니다.");
        boardCommentRepository.delete(comment);
    }

}
