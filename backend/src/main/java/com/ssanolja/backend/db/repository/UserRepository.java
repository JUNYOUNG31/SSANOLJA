package com.ssanolja.backend.db.repository;

import com.ssanolja.backend.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserNickname(String userNickname);
    public User findByUserEmail(String userEmail);

}
