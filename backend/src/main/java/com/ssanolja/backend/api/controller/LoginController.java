package com.ssanolja.backend.api.controller;

import com.ssanolja.backend.config.auth.PrincipalDetails;
import com.ssanolja.backend.db.entity.User;
import com.ssanolja.backend.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/sendUser")
    public User sendUser(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println(principalDetails.getUser());
        return principalDetails.getUser();
    }

//    @GetMapping({"","/"})
//    public String index() {
//        return "home";
//    }
//    @GetMapping("/lobby")
//    public String lobby() {
//        System.out.println("로비 실행됨");
//        return "lobby1";
//    }

//    @GetMapping("/test")
//    public String getUser(User user) {
//        System.out.println("먹는다.");
//        System.out.println(user.toString());
//        return user.toString();
//    }

//    @GetMapping("/user")
//    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
//        System.out.println("@@@@@@@principalDetails : " + principalDetails.getAttributes());
//        System.out.println("@@@@@@@principalDetails : " + principalDetails.getUser());
//        System.out.println("@@@@@@@principalDetails : " + principalDetails.getName());
//        System.out.println("@@@@@@@principalDetails : " + principalDetails.getUsername());
//        System.out.println("@@@@@@@principalDetails : " + principalDetails.getUsername());
//        System.out.println("@@@@@@@principalDetails : " + principalDetails.getPassword());
//        return "user";
//    }

//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }
//
//    @PostMapping("/oauth2/authorization/google")
//    public String join() {
////        return "redirect://";
//        return "join";
//    }
}
