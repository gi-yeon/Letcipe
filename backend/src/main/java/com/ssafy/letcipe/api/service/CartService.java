package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.cart.*;
import com.ssafy.letcipe.domain.cart.Cart;
import com.ssafy.letcipe.domain.cart.CartRepository;
import com.ssafy.letcipe.domain.cartIngredient.CartIngredient;
import com.ssafy.letcipe.domain.cartIngredient.CartIngredientRepository;
import com.ssafy.letcipe.domain.ingredient.Ingredient;
import com.ssafy.letcipe.domain.ingredient.IngredientRepository;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipe.RecipeRepository;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final CartRepository cartRepository;
    private final IngredientRepository ingredientRepository;
    private final CartIngredientRepository cartIngredientRepository;

    @Transactional
    public void createCart(ReqPostCartDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("유저를 찾을 수 없습니다."));
        Recipe recipe = recipeRepository.findById(requestDto.getRecipeId())
                .orElseThrow(() -> new NullPointerException("레시피를 찾을 수 없습니다."));;

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
    public ResGetCartDto getCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("유저를 찾을 수 없습니다."));
        return new ResGetCartDto(user.getCarts());
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
