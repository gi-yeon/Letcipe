package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.cart.ResGetCartIngredientListDto;
import com.ssafy.letcipe.api.dto.cart.*;
import com.ssafy.letcipe.api.dto.ingredient.ResGetIngredientDto;
import com.ssafy.letcipe.api.dto.recipe.ResGetRecipeDto;
import com.ssafy.letcipe.api.dto.user.LogUserDto;
import com.ssafy.letcipe.domain.cart.Cart;
import com.ssafy.letcipe.domain.cart.CartRepository;
import com.ssafy.letcipe.domain.cartIngredient.CartIngredient;
import com.ssafy.letcipe.domain.cartIngredient.CartIngredientRepository;
import com.ssafy.letcipe.domain.history.History;
import com.ssafy.letcipe.domain.history.HistoryRepository;
import com.ssafy.letcipe.domain.history.ProcessType;
import com.ssafy.letcipe.domain.historyIngredient.HistoryIngredient;
import com.ssafy.letcipe.domain.historyIngredient.HistoryIngredientRepository;
import com.ssafy.letcipe.domain.historyIngredient.PurchaseType;
import com.ssafy.letcipe.domain.historyItem.HistoryItem;
import com.ssafy.letcipe.domain.historyItem.HistoryItemRepository;
import com.ssafy.letcipe.domain.ingredient.Ingredient;
import com.ssafy.letcipe.domain.ingredient.IngredientRepository;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.recipe.RecipeRepository;
import com.ssafy.letcipe.domain.recipeIngredient.RecipeIngredient;
import com.ssafy.letcipe.domain.type.StatusType;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.domain.user.UserRepository;
import com.ssafy.letcipe.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class CartService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;
    private final CartRepository cartRepository;
    private final IngredientRepository ingredientRepository;
    private final HistoryRepository historyRepository;
    private final CartIngredientRepository cartIngredientRepository;
    private final HistoryIngredientRepository historyIngredientRepository;
    private final HistoryItemRepository historyItemRepository;

    @Transactional
    public void createCart(Long recipe_id, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("유저를 찾을 수 없습니다."));
        Recipe recipe = recipeRepository.findById(recipe_id)
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

    @Transactional
    public void createCarts(ReqPostCartDto requestDto, Long userId) {
        for(Long recipeId: requestDto.getList()){
            createCart(recipeId, userId);
        }
    }

    @Transactional
    public void createCartHistory(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("유저를 찾을 수 없습니다."));
        History alreadyHistory = historyRepository.findByUserAndProcessAndIsDeleted(user, ProcessType.READY, StatusType.N).orElse(null);

        if(alreadyHistory != null) {
            throw new BadRequestException("이미 실행중인 이력이 있습니다.");
        }

        // History 생성 및 저장
        History history = History.builder()
                .user(user)
                .regTime(LocalDateTime.now())
                .process(ProcessType.READY)
                .isDeleted(StatusType.N)
                .build();
        History readyHistory = historyRepository.save(history);

        // History와 Recipe Mapping
        // 진행하면서 Ingredient도 계산
        Map<Long, Double> ingredientMap = new HashMap<>();
        Map<Long, Ingredient> ingredientObjectMap = new HashMap<>();

        for(Cart cart:user.getCarts()) {
          Recipe recipe = cart.getRecipe();

          HistoryItem historyItem = HistoryItem.builder()
                  .history(readyHistory)
                  .recipe(recipe)
                  .amount(cart.getAmount())
                  .build();

          historyItemRepository.save(historyItem);

          List<RecipeIngredient> recipeIngredients = recipe.getIngredients();

          for(RecipeIngredient recipeIngredient:recipeIngredients) {
              Ingredient ingredient = recipeIngredient.getIngredient();
              Long ingredientId = ingredient.getId();
              if(!ingredientMap.containsKey(ingredientId)) {
                  ingredientMap.put(ingredientId, 0.);
                  ingredientObjectMap.put(ingredientId, ingredient);
              }
              ingredientMap.put(ingredientId, ingredientMap.get(ingredientId) + recipeIngredient.getAmount()* cart.getAmount());
          }
        }

        for(CartIngredient cartIngredient: user.getCartIngredients()){
            Ingredient ingredient = cartIngredient.getIngredient();
            Long ingredientId = ingredient.getId();
            if(!ingredientMap.containsKey(ingredientId)) {
                ingredientMap.put(ingredientId, 0.);
                ingredientObjectMap.put(ingredientId, ingredient);
            }
            ingredientMap.put(ingredientId, ingredientMap.get(ingredientId) + cartIngredient.getAmount());
        }

        Iterator<Long> keys = ingredientMap.keySet().iterator();
        while(keys.hasNext()) {
            Long id = keys.next();
            HistoryIngredient historyIngredient = HistoryIngredient.builder()
                    .history(readyHistory)
                    .ingredient(ingredientObjectMap.get(id))
                    .amount(ingredientMap.get(id))
                    .purchaseType(PurchaseType.N)
                    .build();
            historyIngredientRepository.save(historyIngredient);
        }

        for(Cart cart: user.getCarts()){
            cartRepository.delete(cart);
        }

        for(CartIngredient cartIngredient: user.getCartIngredients()) {
            cartIngredientRepository.delete(cartIngredient);
        }
    }

    @Transactional
    public ResGetCartIngredientListDto getCartIngredient(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NullPointerException("유저를 찾을 수 없습니다."));
        Map<Long, Double> ingredientMap = new HashMap<>();
        Map<Long, Ingredient> ingredientObjectMap = new HashMap<>();

        for(Cart cart:user.getCarts()) {
            Recipe recipe = cart.getRecipe();
            List<RecipeIngredient> recipeIngredients = recipe.getIngredients();

            for(RecipeIngredient recipeIngredient:recipeIngredients) {
                Ingredient ingredient = recipeIngredient.getIngredient();
                Long ingredientId = ingredient.getId();
                if(!ingredientMap.containsKey(ingredientId)) {
                    ingredientMap.put(ingredientId, 0.);
                    ingredientObjectMap.put(ingredientId, ingredient);
                }
                ingredientMap.put(ingredientId, ingredientMap.get(ingredientId) + recipeIngredient.getAmount()* cart.getAmount());
            }
        }

        for(CartIngredient cartIngredient: user.getCartIngredients()){
            Ingredient ingredient = cartIngredient.getIngredient();
            Long ingredientId = ingredient.getId();
            if(!ingredientMap.containsKey(ingredientId)) {
                ingredientMap.put(ingredientId, 0.);
                ingredientObjectMap.put(ingredientId, ingredient);
            }
            ingredientMap.put(ingredientId, ingredientMap.get(ingredientId) + cartIngredient.getAmount());
        }

        List<ResGetCartIngredientDto> list = new ArrayList<>();
        Iterator<Long> keys = ingredientMap.keySet().iterator();
        while(keys.hasNext()) {
            Long id = keys.next();
            Ingredient ingredient = ingredientObjectMap.get(id);
            ResGetCartIngredientDto dto = ResGetCartIngredientDto.builder()
                    .ingredient(ResGetIngredientDto.builder()
                            .id(ingredient.getId())
                            .category(ingredient.getCategory())
                            .name(ingredient.getName())
                            .measure(ingredient.getMeasure())
                            .gml(ingredient.getGml())
                            .build())
                    .amount(ingredientMap.get(id))
                    .build();
            list.add(dto);
        }
        System.out.println(list);
        return new ResGetCartIngredientListDto(list);
    }
}
