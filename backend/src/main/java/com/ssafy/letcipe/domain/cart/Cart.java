package com.ssafy.letcipe.domain.cart;

import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.exception.AuthorityViolationException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "recipe_id" }) })
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    public Cart(User user, Recipe recipe, int i) {
        this.user = user;
        this.recipe = recipe;
        this.amount = i;
    }

    public void update(char operator) {
        switch (operator) {
            case '+':
                this.amount++;
                break;
            case '-':
                if (this.amount > 1) {
                    this.amount--;
                } else {
                    throw new AuthorityViolationException("더 이상 줄일 수 없습니다.");
                }
                break;
        }
    }
}

