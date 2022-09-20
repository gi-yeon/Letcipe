package com.ssafy.letcipe.domain.user;

import com.ssafy.letcipe.domain.type.StatusType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "user_id", nullable = false, length = 30)
    private String userId;

    @Column(name = "email", nullable = false, length = 40)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Column(name = "phone", length = 100)
    private String phone;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "is_deleted", nullable = false)
    private StatusType statusType;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "gender", nullable = false)
    private GenderType gender;

    @Column(name = "job", nullable = false)
    private JobType job;

    @Column(name = "family")
    private Integer family;

    @Column(name = "user_type", nullable = false)
    private UserType userType;

}
