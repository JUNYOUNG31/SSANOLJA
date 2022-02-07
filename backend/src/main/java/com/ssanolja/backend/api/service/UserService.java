package com.ssanolja.backend.api.service;


import com.ssanolja.backend.db.entity.User;
import com.ssanolja.backend.db.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

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

    @Transactional
    public List<User> getUserList(List<String> userNicknames) {
        List<User> userList = new ArrayList<User>();
        for (String userNickname :
                userNicknames) {
            userList.add(userRepository.findByUserNickname(userNickname));
        }

        return userList;
    }
}
