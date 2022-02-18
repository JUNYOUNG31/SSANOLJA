package com.ssanolja.backend.db.repository;

import com.ssanolja.backend.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserNickname(String userNickname);
    User findByUserEmail(String userEmail);

    @Query(value = "select user_nickname from users where users_id = ?", nativeQuery = true)
    String findByUserNicknameFromUsersId(Integer usersId);

    @Query(value = "select users_id from users where user_nickname = ?", nativeQuery = true)
    Integer findUsersIdByUserNickname(String userNickname);

}
