package com.ssafy.letcipe.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

=======
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserIdAndPassword(String userId, String password);

    Optional<User> findByNameAndPhone(String name, String phone);

    Optional<User> findByUserIdAndPhone(String userId, String phone);
}
>>>>>>> 7faa75b44292a9845976a52efe9fa4da63bbd770
