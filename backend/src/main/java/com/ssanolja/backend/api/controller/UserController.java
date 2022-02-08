package com.ssanolja.backend.api.controller;


import com.ssanolja.backend.api.request.UpdateUserNicknameReq;
import com.ssanolja.backend.api.service.UserService;
import com.ssanolja.backend.db.entity.User;
import com.ssanolja.backend.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/update")
    public ResponseEntity updateUserNickname(@RequestBody UpdateUserNicknameReq updateUserNicknameReq) {
        if(updateUserNicknameReq.getUpdatedUserNickname() == ""){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        String userEmail = updateUserNicknameReq.getUserEmail();
        String updatedUserNickname = updateUserNicknameReq.getUpdatedUserNickname();
        Boolean isUpdated = userService.updateNickname(updatedUserNickname, userEmail);
        if (isUpdated) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
