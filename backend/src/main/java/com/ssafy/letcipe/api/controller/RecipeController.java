package com.ssafy.letcipe.api.controller;


import com.ssafy.letcipe.api.dto.recipe.*;
import com.ssafy.letcipe.api.dto.recipeBookmark.ReqPostRecipeBookmarkDto;
import com.ssafy.letcipe.api.dto.recipeBookmark.ReqDeleteRecipeBookmarkDto;
import com.ssafy.letcipe.api.dto.recipeLike.ReqPostRecipeLikeDto;
import com.ssafy.letcipe.api.dto.recipeLike.ReqDeleteRecipeLikeDto;
import com.ssafy.letcipe.api.dto.report.ReqGetCartReport;
import com.ssafy.letcipe.api.dto.report.ResGetCartReport;
import com.ssafy.letcipe.api.service.JwtService;
import com.ssafy.letcipe.api.service.RecipeService;
import com.ssafy.letcipe.api.service.ReportService;
import com.ssafy.letcipe.api.service.UserService;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
@Log4j2
public class RecipeController {
    private final RecipeService recipeService;
    private final ReportService reportService;
    private final JwtService jwtService;
    private final UserService userService;
    @GetMapping("/{recipe_id}")
    @Transactional
    public ResponseEntity readRecipe(@PathVariable long recipe_id, HttpServletRequest request) {
        // TODO 토큰에서 유저 id 가져와야 함, 없다면 -1 등으로 표기
        Long userId;
        try {
            userId = jwtService.getUserId(request);
        } catch (Exception e) {
            userId = -1L;
        }
        ResGetDetailRecipeDto recipe = recipeService.readRecipe(recipe_id, userId);
        return ResponseEntity.ok(recipe);
    }

    @PostMapping("")
    public ResponseEntity createRecipe(@ModelAttribute ReqPostRecipeDto dto, HttpServletRequest request) throws FileUploadException {
        Long userId = jwtService.getUserId(request); // TODO 토큰에서 유저 id 가져와야 함
        recipeService.createRecipe(dto, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{recipe_id}")
    @Transactional
    public ResponseEntity updateRecipe(@PathVariable long recipe_id, @ModelAttribute @Validated ReqPutRecipeDto dto, HttpServletRequest request) throws FileNotFoundException, FileUploadException {
        Long userId = jwtService.getUserId(request); // TODO 토큰에서 유저 id 가져와야 함
        recipeService.updateRecipe(dto, recipe_id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{recipe_id}")
    public ResponseEntity deleteRecipe(@PathVariable long recipe_id, HttpServletRequest request) {
        long userId = jwtService.getUserId(request); // TODO 토큰에서 유저 id 가져와야 함
        recipeService.deleteRecipe(recipe_id, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/like")
    ResponseEntity createRecipeLike(@RequestBody ReqPostRecipeLikeDto requestDto, HttpServletRequest request) throws SQLException {
        try {
            recipeService.createLike(requestDto, jwtService.getUserId(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/like")
    ResponseEntity deleteRecipeLike(@RequestBody ReqDeleteRecipeLikeDto requestDto, HttpServletRequest request) throws SQLException {
        try {
            recipeService.deleteLike(requestDto, jwtService.getUserId(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/mark")
    ResponseEntity createRecipeBookmark(@RequestBody ReqPostRecipeBookmarkDto requestDto, HttpServletRequest request) throws SQLException {
        recipeService.createBookmark(requestDto, jwtService.getUserId(request));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/mark")
    ResponseEntity deleteRecipeBookmark(@RequestBody ReqDeleteRecipeBookmarkDto requestDto, HttpServletRequest request) throws SQLException {
        recipeService.deleteBookmark(requestDto, jwtService.getUserId(request));
        return ResponseEntity.ok().build();
    }


    @GetMapping("")
    ResponseEntity searchRecipe(@RequestParam(required = false) String keyword, @RequestParam(required = false) String ingredients, Pageable pageable) throws SQLException {
        log.info(keyword);
        log.info(ingredients);
        if (!StringUtil.isNullOrEmpty(keyword))
            return ResponseEntity.ok(recipeService.searchRecipeByKeyword(pageable, keyword));
        else
            return ResponseEntity.ok(recipeService.searchRecipeByIngredients(pageable, ingredients));
    }

    @GetMapping("/totalNum")
    ResponseEntity<Integer> totalNumRecipe(@RequestParam(required = false) String keyword,@RequestParam(required = false) String ingredients) throws SQLException {
        if (!StringUtil.isNullOrEmpty(keyword))
            return ResponseEntity.ok(recipeService.totalNumByKeyword(keyword));
        else
            return ResponseEntity.ok(recipeService.totalNumByIngredients(ingredients));
    }

    @GetMapping("/hot")
    @Transactional
    ResponseEntity searchHot(Pageable pageable) throws SQLException {
        LocalDate now=LocalDate.now();
        List<ResGetCartReport> cartReport;
        ResGetHotRecipeDto responseDto = null;
        for (int loop = 0; loop < 5; loop++) {
        ResGetHotRecipeComponentDto hot= userService.getAttribute2(-1L);
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

    @GetMapping("/best")
    ResponseEntity getBestRecipes(Pageable pageable) throws SQLException {
        return ResponseEntity.ok(recipeService.getBestRecipes(pageable));
    }

    @GetMapping("/recommend")
    public ResponseEntity getCartReport(String attributes, Pageable pageable) {
//        List<ResGetCartReport> cartReport = reportService.getCartReport(reqDto.getAttributes(), LocalDate.parse(reqDto.getBeginDate()), LocalDate.parse(reqDto.getEndDate()), pageable);
        LocalDate now=LocalDate.now();
        ResGetHotRecipeComponentDto hot = new ResGetHotRecipeComponentDto(attributes);
        List<ResGetCartReport> cartReport = reportService.getCartReport(
                attributes,
                now.minusDays(30),
                now.minusDays(1),
                pageable);

        ResGetHotRecipeDto responseDto = ResGetHotRecipeDto.builder()
                .title(hot.getTitle())
                .report(cartReport)
                .build();

        return ResponseEntity.ok(responseDto);
    }

}
