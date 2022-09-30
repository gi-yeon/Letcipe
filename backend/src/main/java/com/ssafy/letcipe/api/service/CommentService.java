package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.comment.ReqPatchCommentDto;
import com.ssafy.letcipe.api.dto.comment.ReqPostCommentDto;
import com.ssafy.letcipe.api.dto.comment.ReqPutCommentDto;
import com.ssafy.letcipe.api.dto.comment.ResGetCommentDto;
import com.ssafy.letcipe.domain.board.Board;
import com.ssafy.letcipe.domain.board.BoardRepository;
import com.ssafy.letcipe.domain.comment.BoardType;
import com.ssafy.letcipe.domain.comment.Comment;
import com.ssafy.letcipe.domain.comment.CommentRepository;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipe.RecipeRepository;
import com.ssafy.letcipe.domain.type.StatusType;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.domain.user.UserRepository;
import com.ssafy.letcipe.exception.AuthorityViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public List<ResGetCommentDto> getComment(BoardType boardType, Long boardId) {
        List<ResGetCommentDto> comments = commentRepository.findByStatusTypeAndBoardTypeAndBoardId(StatusType.N, boardType, boardId)
                .stream().map(comment ->
                        new ResGetCommentDto(comment.getId(), comment.getUser().getNickname(), comment.getContent(), comment.getRegTime()))
                .collect(Collectors.toList());
        return comments;
    }

    @Transactional
    public void putComment(ReqPutCommentDto commentDto, Long userId) {
        Comment comment = commentRepository.findByStatusTypeAndId(StatusType.N, commentDto.getId()).orElseThrow(
                ()->new NullPointerException("해당 댓글을 찾을 수 없습니다."));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 유저를 찾을 수 없습니다.")
        );
        if(!comment.getUser().getUserId().equals(user.getUserId()))
            throw new AuthorityViolationException("본인 댓글만 수정이 가능합니다.");
        comment.putComment(commentDto.getContent());
    }
    @Transactional
    public void patchComment(ReqPatchCommentDto commentDto, Long userId) {
        Comment comment = commentRepository.findByStatusTypeAndId(StatusType.N, commentDto.getId()).orElseThrow(
                ()->new NullPointerException("해당 댓글을 찾을 수 없습니다."));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 유저를 찾을 수 없습니다.")
        );
        if(!comment.getUser().getUserId().equals(user.getUserId()))
            throw new AuthorityViolationException("본인 댓글만 삭제가 가능합니다.");
        comment.patchComment();
    }
    @Transactional
    public void postComment(ReqPostCommentDto commentDto, Long userId) {

        if(commentDto.getBoardType() == BoardType.BOARD){
            Board board = boardRepository.findByStatusTypeAndId(StatusType.N, commentDto.getBoardId()).orElseThrow(
                    ()-> new NullPointerException("해당 게시판을 찾을 수 없습니다."));
        }else if(commentDto.getBoardType() == BoardType.RECIPE){
            Recipe recipe = recipeRepository.findByStatusTypeAndId(StatusType.N, commentDto.getBoardId()).orElseThrow(
                    ()-> new NullPointerException("해당 레시피를 찾을 수 없습니다."));
        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 유저를 찾을 수 없습니다.")
        );
        Comment comment = Comment.builder()
                .user(user)
                .boardId(commentDto.getBoardId())
                .boardType(commentDto.getBoardType())
                .content(commentDto.getContent())
                .statusType(StatusType.N)
                .build();

        commentRepository.save(comment);

    }
}
