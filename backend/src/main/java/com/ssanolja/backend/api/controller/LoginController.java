package com.ssanolja.backend.api.controller;

import com.ssanolja.backend.api.service.LoginService;
import com.ssanolja.backend.api.service.UserService;
import com.ssanolja.backend.db.entity.User;
import com.ssanolja.backend.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginService loginService;

    @PostMapping("/insertUser")
    public ResponseEntity insertUser(@RequestBody User user) {
        Map res = loginService.insertUser(user);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PostMapping("/sendUser")
    public ResponseEntity senduser(@RequestBody String userEmail){
        User res = loginService.senduser(userEmail);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
}
