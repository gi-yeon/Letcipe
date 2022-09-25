package com.ssafy.letcipe.domain.ingredient;

import com.ssafy.letcipe.domain.headerCode.HeaderCode;
import com.ssafy.letcipe.domain.recipe.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "measure")
    private String measure;

    @Column(name = "gml")
    private Double gml;
}
