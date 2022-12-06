package com.ssafy.letcipe.domain.recipe;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.letcipe.domain.ingredient.Ingredient;
import com.ssafy.letcipe.domain.ingredient.QIngredient;
import com.ssafy.letcipe.domain.recipeIngredient.QRecipeIngredient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecipeRepositoryCustomImpl implements RecipeRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    QRecipeIngredient recipeIngredient = QRecipeIngredient.recipeIngredient;


    @Override
    public List<RecipeIngredientCountDto> findRecipeContains(String[] ingredientIds) throws SQLException {
        BooleanBuilder builder = new BooleanBuilder();
        for (String ingredientId : ingredientIds) {
            builder.or(recipeIngredient.ingredient.id.eq(Long.parseLong(ingredientId)));
        }

        return queryFactory
                .select(Projections.constructor(RecipeIngredientCountDto.class,recipeIngredient.recipe, recipeIngredient.count()))
                .from(recipeIngredient)
                .where(builder)
                .groupBy(recipeIngredient.recipe)
                .fetch();
    }

    public List<RecipeIngredientCountDto>  totalNumRecipeContains(String[] ingredientIds) throws SQLException {
        BooleanBuilder builder = new BooleanBuilder();
        for (String ingredientId : ingredientIds) {
            builder.or(recipeIngredient.ingredient.id.eq(Long.parseLong(ingredientId)));
        }

        return queryFactory
                .select(Projections.constructor(RecipeIngredientCountDto.class,recipeIngredient.recipe, recipeIngredient.count()))
                .from(recipeIngredient)
                .where(builder)
                .groupBy(recipeIngredient.recipe)
                .fetch();
    }


}
