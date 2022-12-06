package com.ssafy.letcipe.domain.user;

import com.ssafy.letcipe.domain.type.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserIdAndPassword(String userId, String password);

    Optional<User> findByNameAndPhone(String name, String phone);

    Optional<User> findByUserIdAndPhone(String userId, String phone);

    boolean existsByUserId(String userId);

    boolean existsByNickname(String nickname);

    Optional<User> findByUserIdAndPasswordAndUserType(String userId, String password, UserType admin);

    Optional<User> findByUserIdAndPasswordAndStatusType(String userId, String password, StatusType n);

    Optional<User> findByIdAndStatusType(Long userId, StatusType n);
}
