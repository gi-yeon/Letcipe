package com.ssafy.letcipe.domain.ingredient;

import com.ssafy.letcipe.domain.headerCode.HeaderCode;
import com.ssafy.letcipe.domain.measure.Measure;
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

    @ManyToOne(targetEntity = Measure.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "measure_id", referencedColumnName = "id")
    private Measure measure;

    @Column(name = "category")
    private String category;
}
