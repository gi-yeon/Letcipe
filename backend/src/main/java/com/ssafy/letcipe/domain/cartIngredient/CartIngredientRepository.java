package com.ssafy.letcipe.domain.cartIngredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartIngredientRepository extends JpaRepository<CartIngredient, Long> {
}
