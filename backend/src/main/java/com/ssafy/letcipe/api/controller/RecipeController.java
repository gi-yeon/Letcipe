package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.recipe.ReqCreateRecipeDto;
import com.ssafy.letcipe.api.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    @PostMapping("")
    public ResponseEntity createRecipe(@ModelAttribute ReqCreateRecipeDto createDto) throws FileUploadException {
        int userId = 1; // TODO 토큰에서 유저 id 가져와야 함
        recipeService.createRecipe(userId, createDto);
        return ResponseEntity.ok("");
    }

}
