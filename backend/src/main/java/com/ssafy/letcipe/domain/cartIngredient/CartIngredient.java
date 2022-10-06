package com.ssafy.letcipe.domain.cartIngredient;

import com.ssafy.letcipe.domain.ingredient.Ingredient;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.exception.AuthorityViolationException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "ingredient_id" }) })
public class CartIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = Ingredient.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    private Ingredient ingredient;


    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "amount")
    private Integer amount;

    public CartIngredient(User user, Ingredient ingredient, char operator) {
        this.user = user;
        this.ingredient = ingredient;

        switch (operator) {
            case '+':
                this.amount = 1;
                break;
            case '-':
                this.amount = -1;
                break;
        }
    }
    public CartIngredient(User user, Ingredient ingredient, Integer amount) {
        this.user = user;
        this.ingredient = ingredient;
        this.amount=amount;
    }
    public void update(char operator) {
        switch (operator) {
            case '+':
                this.amount++;
                break;
            case '-':
                if(this.amount > 1) {
                    this.amount--;
                } else {
                    throw new AuthorityViolationException("더 이상 줄일 수 없습니다.");
                }
                break;
        }
    }
}
