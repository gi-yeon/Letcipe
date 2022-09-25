package com.ssafy.letcipe.domain.recipeList;

import com.ssafy.letcipe.domain.recipeListItem.RecipeListItem;
import com.ssafy.letcipe.domain.type.StatusType;
import com.ssafy.letcipe.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class RecipeList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "reg_time")
    private LocalDateTime regTime;

    @Column(name = "mod_time")
    private LocalDateTime modTime;

    @Column(name = "is_deleted", nullable = false)
    private StatusType isDeleted;

    @Column(name = "is_shared")
    private SharedType isShared;

    @OneToMany(targetEntity = RecipeListItem.class, mappedBy = "recipeList", fetch = FetchType.LAZY)
    private List<RecipeListItem> recipeListItems = new ArrayList<>();

    @Builder
    public RecipeList(User user, String name, String description, SharedType isShared) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.regTime = LocalDateTime.now();
        this.isDeleted = StatusType.N;
        this.isShared = isShared;
    }

    public void updateRecipeList(String name, String description, SharedType isShared) {
        this.name = name;
        this.description = description;
        this.isShared = isShared;
    }

    public void deleteRecipeList() {
        this.isDeleted = StatusType.Y;
    }

}
