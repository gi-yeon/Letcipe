package com.ssafy.letcipe.domain.historyIngredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryIngredientRepository extends JpaRepository<HistoryIngredient, Long> {
}
