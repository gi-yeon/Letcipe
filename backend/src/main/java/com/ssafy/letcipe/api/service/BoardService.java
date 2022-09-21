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


    @Transactional
    public List<ResGetBoardListDto> getBoardList(int page, int board_num) throws Exception{
        if(page == 0) throw new Exception("잘못된 요청입니다.");
        PageRequest pageRequest = PageRequest.of(page-1, board_num);

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

    //그냥 보드 리스트 조회
//        @Transactional
//    public List<ResGetBoardListDto> getBoardList() {
//        List<Board> boardList = boardRepository.findAllByStatusType(StatusType.N);
//            List<ResGetBoardListDto> boardListRes = new ArrayList<>();
//        for(Board board : boardList) {
//            boardListRes.add(
//                    ResGetBoardListDto.builder()
//                            .id(board.getId())
//                            .likes(board.getLikes())
//                            .nickname(board.getUser().getNickname())
//                            .title(board.getTitle())
//                            .regTime(board.getRegTime())
//                            .build()
//            );
//
//        }
//        return boardListRes;
//    }


//    @Transactional(readOnly = true)
//    public ResGetBoardDto getBoard(Long board_id, Long user_id) {
//        Board board = boardRepository.findById(board_id).orElseThrow(
//                () -> new NullPointerException("해당 게시판이 존재하지 않습니다."));
//        List<BoardComment> boardComments = boardCommentRepository.findByBoard(board);
//        List<ResGetBoardCommentDto> comments = new ArrayList<>();
//        for (BoardComment comment : boardComments) {
//            comments.add(ResGetBoardCommentDto.createDto(comment));
//        }
//        User user = userRepository.findById(user_id).orElseThrow(
//                ()-> new NullPointerException("해당 유저를 찾을 수 없습니다."));
//        return ResGetBoardDto.createDto(board, user, comments);
//    }

    @Transactional(readOnly = true)
    public ResGetBoardDto getBoard(Long board_id, Long user_id) {
        Board board = boardRepository.findById(board_id).orElseThrow(
                () -> new NullPointerException("해당 게시판이 존재하지 않습니다."));
        List<BoardComment> boardComments = board.getBoardCommentList();
        List<ResGetBoardCommentDto> comments = new ArrayList<>();
        for (BoardComment comment : boardComments) {
            comments.add(ResGetBoardCommentDto.createDto(comment));
        }
        User user = userRepository.findById(user_id).orElseThrow(
                ()-> new NullPointerException("해당 유저를 찾을 수 없습니다."));
        return ResGetBoardDto.createDto(board, user, comments);
    }



    @Transactional
    public void postBoard(String title, String content, Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(
                () -> new NullPointerException("해당 유저를 찾을 수 없습니다.")
        );
        Board board = Board.builder()
                .user(user)
                .title(title)
                .content(content)
                .statusType(StatusType.N)
                .build();
        boardRepository.save(board);
    }

    @Transactional
    public void putBoard(Long board_id, String title, String content, Long user_id) throws Exception{
        Board board = boardRepository.findById(board_id).orElseThrow(
                () -> new NullPointerException("해당 게시글을 찾을 수 없습니다.")
        );
        User user = userRepository.findById(user_id).orElseThrow(
                () -> new NullPointerException("해당 유저를 찾을 수 없습니다.")
        );
        if(!board.getUser().getUserId().equals(user.getUserId()))
            throw new Exception("본인 게시글만 수정이 가능합니다.");
        board.putBoard(title, content);
        boardRepository.save(board);
    }
    @Transactional
    public void patchBoard(Long board_id, Long user_id) throws Exception {
        Board board = boardRepository.findById(board_id).orElseThrow(
                () -> new NullPointerException("해당 게시글을 찾을 수 없습니다.")
        );
        User user = userRepository.findById(user_id).orElseThrow(
                () -> new NullPointerException("해당 유저를 찾을 수 없습니다.")
        );

        if(!board.getUser().getUserId().equals(user.getUserId()))
            throw new Exception("본인 게시글만 삭제가 가능합니다.");
        board.patchBoard();
        boardRepository.save(board);
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
