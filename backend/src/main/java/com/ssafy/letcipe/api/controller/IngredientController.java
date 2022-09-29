package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.ingredient.ResGetIngredientDto;
import com.ssafy.letcipe.api.service.IngredientService;
import com.ssafy.letcipe.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
@Log4j2
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("")
    public ResponseEntity searchIngredient(@RequestParam String keyword) {
        if (keyword.length() >= 1)
            return ResponseEntity.ok(ingredientService.searchIngredient(keyword));
        else
            return ResponseEntity.badRequest().build();
    }
}
