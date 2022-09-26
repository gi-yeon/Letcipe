package com.ssafy.letcipe.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserIdAndPassword(String userId, String password);

    Optional<User> findByNameAndPhone(String name, String phone);

    Optional<User> findByUserIdAndPhone(String userId, String phone);
}

