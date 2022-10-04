package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.recipe.ResGetHotRecipeComponentDto;
import com.ssafy.letcipe.api.dto.recipe.ResGetHotRecipeDto;
import com.ssafy.letcipe.api.dto.report.ResGetCartReport;
import com.ssafy.letcipe.api.dto.user.*;
import com.ssafy.letcipe.api.service.JwtService;
import com.ssafy.letcipe.api.service.RecipeService;
import com.ssafy.letcipe.api.service.ReportService;
import com.ssafy.letcipe.api.service.UserService;
import com.ssafy.letcipe.domain.comment.BoardType;
import com.ssafy.letcipe.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final ReportService reportService;

    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    @PostMapping("")
    public ResponseEntity createUser(@ModelAttribute ReqPostUserDto requestDto) throws NoSuchAlgorithmException, FileUploadException {
        userService.createUser(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/id/{userId}/exists")
    public ResponseEntity<?> checkIdDuplicate(@PathVariable String userId) {
        userService.checkDuplicationId(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/nickname/{nickname}/exists")
    public ResponseEntity<?> checkNicknameDuplicate(@PathVariable String nickname) {
        userService.checkDuplicationNickname(nickname);
        return ResponseEntity.ok().build();
    }

    @PostMapping("login")
    public ResponseEntity loginUser(@RequestBody ReqLoginUserDto requestDto) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(userService.loginUser(requestDto));
    }

    @PostMapping("login/admin")
    public ResponseEntity loginAdmin(@RequestBody ReqLoginUserDto requestDto) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(userService.loginAdmin(requestDto));
    }

    @PostMapping("token")
    public ResponseEntity updateToken(HttpServletRequest request) {
        String token = request.getHeader("access-token");
        String refreshToken = request.getHeader("refresh-token");
        return ResponseEntity.ok(userService.updateToken(token, refreshToken));
    }

    @GetMapping("")
    public ResponseEntity readUser(HttpServletRequest request) {
        Long userId = jwtService.getUserId(request);
        ResGetUserDto resDto = userService.readUser(userId);
        return ResponseEntity.ok(resDto);
    }

    @PatchMapping("")
    public ResponseEntity deleteUser(HttpServletRequest request) {
        Long userId = jwtService.getUserId(request);
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("")
    public ResponseEntity updateUser(@ModelAttribute ReqPutUserDto requestDto, HttpServletRequest request) throws FileUploadException {
        Long userId = jwtService.getUserId(request);
        System.out.println(requestDto);
        userService.updateUser(userId, requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("recipe")
    public ResponseEntity readUserRecipe(HttpServletRequest request, Pageable pageable) {
        Long userId = jwtService.getUserId(request);
        return ResponseEntity.ok(userService.readUserRecipe(userId, pageable));
    }

    @GetMapping("recipelist")
    public ResponseEntity readUserRecipeList(HttpServletRequest request, Pageable pageable) {
        Long userId = jwtService.getUserId(request);
        return ResponseEntity.ok(userService.readUserRecipeList(userId, pageable));
    }

    @GetMapping("mark/recipe")
    public ResponseEntity readRecipeBookmark(HttpServletRequest request, Pageable pageable) {
        Long userId = jwtService.getUserId(request);
        return ResponseEntity.ok(userService.readRecipeBookmark(userId, pageable));
    }

    @GetMapping("like/recipe")
    public ResponseEntity readRecipeLike(HttpServletRequest request, Pageable pageable) {
        Long userId = jwtService.getUserId(request);
        return ResponseEntity.ok(userService.readRecipeLike(userId, pageable));
    }

    @GetMapping("mark/recipelist")
    public ResponseEntity readRecipeListBookmark(HttpServletRequest request, Pageable pageable) {
        Long userId = jwtService.getUserId(request);
        return ResponseEntity.ok(userService.readRecipeListBookmark(userId, pageable));
    }

    @GetMapping("comment")
    public ResponseEntity<?> getUserComment(HttpServletRequest request, Pageable pageable) {
        Long userId = jwtService.getUserId(request);
        return ResponseEntity.ok(userService.getUserComment(userId, pageable));
    }
    @GetMapping("commentNum")
    public ResponseEntity<Long> getCommentNum(HttpServletRequest request){
        Long userId = jwtService.getUserId(request);
        return ResponseEntity.ok(userService.getCommentNum(userId));
    }

    @PostMapping("id")
    public ResponseEntity readUserId(@RequestBody ReqGetUserIdDto requestDto) {
        return ResponseEntity.ok(userService.readUserId(requestDto));
    }

    @PostMapping("password")
    public ResponseEntity readPassword(@RequestBody ReqGetPasswordDto requestDto) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(userService.readPassword(requestDto));
    }

    @PatchMapping("password")
    public ResponseEntity updatePassword(@RequestBody ReqUpdatePasswordDto requestDto, HttpServletRequest request) throws NoSuchAlgorithmException {
        Long userId = jwtService.getUserId(request);
        userService.updatePassword(requestDto, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("check")
    public ResponseEntity checkUser(HttpServletRequest request) {
        return ResponseEntity.ok(jwtService.isAdmin(request));
    }

    @GetMapping("/recipe/recommend")
    @Transactional
    ResponseEntity getRecipeRecommend(Pageable pageable, HttpServletRequest request) throws SQLException {
        Long userId;
        try {
            userId = jwtService.getUserId(request);
        } catch (Exception e) {
            userId = -1L;
        }
        List<ResGetCartReport> cartReport;
        ResGetHotRecipeDto responseDto = null;
        for (int loop = 0; loop < 5; loop++) {
            ResGetHotRecipeComponentDto hot = userService.getAttribute2(userId);
            LocalDate now = LocalDate.now();
            cartReport = reportService.getCartReport(
                    hot.getAttribute(),
                    now.minusDays(30),
                    now.minusDays(1),
                    pageable);
            responseDto = ResGetHotRecipeDto.builder()
                    .title(hot.getTitle())
                    .report(cartReport)
                    .build();

            if (cartReport.size() > 0) break;
        }
        return ResponseEntity.ok(responseDto);
    }
}
