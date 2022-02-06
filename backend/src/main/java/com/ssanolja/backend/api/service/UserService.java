package com.ssanolja.backend.api.service;


import com.ssanolja.backend.db.entity.User;
import com.ssanolja.backend.db.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Boolean updateNickname(String updatedNickname, String userEmail){
        User user = userRepository.findByUserEmail(userEmail);
        User existingUser = userRepository.findByUserNickname(updatedNickname);

        if (existingUser == null) {
            user.setUserNickname(updatedNickname);
            userRepository.save(user);
            return true;
        } else {
            return user.getUsersId().equals(existingUser.getUsersId());
        }

    }
}
