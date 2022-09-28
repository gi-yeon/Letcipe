package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.cart.*;
import com.ssafy.letcipe.api.dto.ingredient.ResGetIngredientDto;
import com.ssafy.letcipe.api.dto.recipe.ResGetRecipeDto;
import com.ssafy.letcipe.api.dto.recipeIngredient.ResGetRecipeIngredientDto;
import com.ssafy.letcipe.api.dto.user.LogUserDto;
import com.ssafy.letcipe.api.dto.user.ResGetUserDto;
import com.ssafy.letcipe.domain.cart.Cart;
import com.ssafy.letcipe.domain.cart.CartRepository;
import com.ssafy.letcipe.domain.cartIngredient.CartIngredient;
import com.ssafy.letcipe.domain.cartIngredient.CartIngredientRepository;
import com.ssafy.letcipe.domain.ingredient.Ingredient;
import com.ssafy.letcipe.domain.ingredient.IngredientRepository;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipe.RecipeRepository;
import com.ssafy.letcipe.domain.recipeIngredient.RecipeIngredient;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class CartService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;
    private final CartRepository cartRepository;
    private final IngredientRepository ingredientRepository;
    private final CartIngredientRepository cartIngredientRepository;
    private final IngredientService ingredientService;

    @Transactional
    public void createCart(ReqPostCartDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("유저를 찾을 수 없습니다."));
        Recipe recipe = recipeRepository.findById(requestDto.getRecipeId())
                .orElseThrow(() -> new NullPointerException("레시피를 찾을 수 없습니다."));;

        // 유저 로그 찍기
        JSONObject json = new JSONObject();
        json.put("USER",new LogUserDto(user).toJsonMap());
        // 레시피 로그 찍기
        ResGetRecipeDto recipeDto = recipeService.getRecipeDto(recipe);
        json.put("RECIPE",recipeDto.toJsonMap());
        log.info("{}", json.toString());

        cartRepository.save(new Cart(user, recipe, 1));
    }

    @Transactional
    public void deleteCart(ReqDeleteCartDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("유저를 찾을 수 없습니다."));
        Recipe recipe = recipeRepository.findById(requestDto.getRecipeId())
                .orElseThrow(() -> new NullPointerException("레시피를 찾을 수 없습니다."));
        Cart cart = cartRepository.findByUserAndRecipe(user, recipe)
                .orElseThrow(() -> new NullPointerException("레시피를 등록한 적 없습니다."));
        cartRepository.delete(cart);
    }

    @Transactional
    public ResGetCartDto getCart(Long userId) throws SQLException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("유저를 찾을 수 없습니다."));
        List<ResGetCartItemDto> cartDtos = new ArrayList<>();
        for (Cart cart : user.getCarts()) {
            ResGetCartItemDto cartItemDto = new ResGetCartItemDto(recipeService.getRecipeDto(cart.getRecipe()),cart.getAmount());
            cartDtos.add(cartItemDto);
        }

        return new ResGetCartDto(cartDtos);
    }

    @Transactional
    public void updateCartRecipe(ReqPatchCartDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("유저를 찾을 수 없습니다."));
        Recipe recipe = recipeRepository.findById(requestDto.getRecipeId())
                .orElseThrow(() -> new NullPointerException("레시피를 찾을 수 없습니다."));
        Cart cart = cartRepository.findByUserAndRecipe(user, recipe)
                .orElseThrow(() -> new NullPointerException("레시피를 등록한 적 없습니다."));
        cart.update(requestDto.getOperator());
    }

    @Transactional
    public void createCartIngredient(ReqPostCartIngredientDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("유저를 찾을 수 없습니다."));
        Ingredient ingredient = ingredientRepository.findById(requestDto.getIngredientId())
                .orElseThrow(() -> new NullPointerException("재료를 찾을 수 없습니다."));

        cartIngredientRepository.save(new CartIngredient(user, ingredient, requestDto.getOperator()));
    }

    @Transactional
    public void patchCartIngredient(ReqPatchCartIngredientDto requestDto, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("유저를 찾을 수 없습니다."));
        Ingredient ingredient = ingredientRepository.findById(requestDto.getIngredientId())
                .orElseThrow(() -> new NullPointerException("재료를 찾을 수 없습니다."));
        CartIngredient cartIngredient = cartIngredientRepository.findByUserAndIngredient(user, ingredient)
                .orElseThrow(() -> new NullPointerException("수정한 적 없습니다."));
        cartIngredient.update(requestDto.getOperator());
    }

    @Transactional
    public void deleteCartIngredient(ReqDeleteCartIngredientDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("유저를 찾을 수 없습니다."));
        Ingredient ingredient = ingredientRepository.findById(requestDto.getIngredientId())
                .orElseThrow(() -> new NullPointerException("재료를 찾을 수 없습니다."));
        CartIngredient cartIngredient = cartIngredientRepository.findByUserAndIngredient(user, ingredient)
                .orElseThrow(() -> new NullPointerException("수정한 적 없습니다."));
        cartIngredientRepository.delete(cartIngredient);
    }
}
