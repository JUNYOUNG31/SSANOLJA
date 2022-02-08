package com.ssanolja.backend.api.service;

import com.ssanolja.backend.db.entity.User;
import com.ssanolja.backend.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    public Map insertUser(User user){
        Map result = new HashMap<String, Object>();
        User selectUser = userRepository.findByUserEmail(user.getUserEmail());
        if(selectUser == null){
            User inputUser = User.builder()
                    .userEmail(user.getUserEmail())
                    .build();
            userRepository.save(inputUser);
            System.out.println("유저 확인 (첫 회원가입)." + inputUser);

            result.put("userEmail", user.getUserEmail());
            result.put("userNickname", user.getUserNickname());
            result.put("status", "ok");
        }else {
            System.out.println("유저 확인 (이미 회원가입)." + selectUser);

            result.put("userEmail", selectUser.getUserEmail());
            result.put("userNickname", selectUser.getUserNickname());
            result.put("status", "ok");
        }
        return result;
    }

    public User senduser(String userEmail){
        User user = userRepository.findByUserEmail(userEmail);
        return user;
    }
}
