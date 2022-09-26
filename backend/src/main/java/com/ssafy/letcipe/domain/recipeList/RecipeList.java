package com.ssafy.letcipe.domain.recipeList;

import com.ssafy.letcipe.domain.type.StatusType;
import com.ssafy.letcipe.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private StatusType statusType;

    @Column(name = "is_shared")
    private SharedType isShared;

}
