package com.donamdong.spiritualwar.repository.user;

import com.donamdong.spiritualwar.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByUserId(String id);
    Optional<User> findFirstByUserIdAndUserPassword(String userId, String userPassword);


}
