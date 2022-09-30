package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.cart.*;
import com.ssafy.letcipe.api.service.CartService;
import com.ssafy.letcipe.api.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final JwtService jwtService;

    private final Logger logger = LoggerFactory.getLogger(CartController.class);

    @PostMapping("cart")
    public ResponseEntity createCart(@RequestBody ReqPostCartDto requestDto, HttpServletRequest request) {
        Long userId = 1L;
        System.out.println("=================================================================" + requestDto);
        cartService.createCarts(requestDto, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("cart")
    //TODO: 추가재료도 삭제해주기
    public ResponseEntity deleteCart(@RequestBody ReqDeleteCartDto requestDto, HttpServletRequest request) {
        Long userId = 1L;
        cartService.deleteCart(requestDto, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("cart")
    //TODO: 추가재료 수량까지 계산해서 재료도 보내주기
    public ResponseEntity readCart(HttpServletRequest request) throws SQLException {
        Long userId = 1L;
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PatchMapping("cart-recipe")
    public ResponseEntity updateCartRecipe(@RequestBody ReqPatchCartDto requestDto, HttpServletRequest request){
        Long userId = 1L;
        cartService.updateCartRecipe(requestDto, userId);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("cart-ingredient")
    public ResponseEntity createCartIngredient(@RequestBody ReqPostCartIngredientDto requestDto, HttpServletRequest request) {
        Long userId = 1L;
        cartService.createCartIngredient(requestDto, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("cart-ingredient")
    public ResponseEntity getCartIngredient(HttpServletRequest request){
        Long userId = 1L;
        userId = 1L;

        return ResponseEntity.ok(cartService.getCartIngredient(userId));
    }

    @PatchMapping("cart-ingredient")
    public ResponseEntity patchCartIngredient(@RequestBody ReqPatchCartIngredientDto requestDto, HttpServletRequest request) {
        Long userId = 1L;
        cartService.patchCartIngredient(requestDto, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("cart-ingredient")
    public ResponseEntity deleteCartIngredient(@RequestBody ReqDeleteCartIngredientDto requestDto, HttpServletRequest request) {
        Long userId = 1L;
        cartService.deleteCartIngredient(requestDto, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("cart-history")
    public ResponseEntity createCartHistory(HttpServletRequest request){
        Long userId = 1L;
        cartService.createCartHistory(userId);
        return ResponseEntity.ok().build();
    }
}

