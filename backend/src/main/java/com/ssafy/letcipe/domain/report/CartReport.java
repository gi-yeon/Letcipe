package com.ssafy.letcipe.domain.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssafy.letcipe.api.dto.report.ResGetCartReport;
import com.ssafy.letcipe.domain.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "cart_report_constraint",
                columnNames = {"attributes", "date","recipe_id"}
        )
})
public class CartReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;
    private String attributes;
    private LocalDate date;
    private Integer count;
    
}
