package com.ssafy.letcipe.domain.cartIngredient;

import com.ssafy.letcipe.domain.cart.Cart;
import com.ssafy.letcipe.domain.ingredient.Ingredient;
import com.ssafy.letcipe.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartIngredientRepository extends JpaRepository<CartIngredient, Long> {
    Optional<CartIngredient> findByUserAndIngredient(User user, Ingredient ingredient);
}
