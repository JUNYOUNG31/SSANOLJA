package com.ssanolja.backend.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/telestations")
public class TelestationController {

//    @GetMapping("/inputKeyword")
//    public String inputKey() {
//        System.out.println("input KeyWord 실행 확인");
//        return "이거 가나?";
//    }

    @GetMapping("/inputKeyword")
    public ResponseEntity<String> inputKey1() {
        String keyword = "리스폰스 엔티티 테스트";
        System.out.println("리스폰스 엔티티 테스트");
        return ResponseEntity.ok(keyword);
    }

    //    @PostMapping("/postTest")
//    public String inputKey2(@RequestBody String keyword) {
//        System.out.println("포스트 테스트 : " + keyword);
//        return null;
//    }
    @PostMapping("/postTest")
    public ResponseEntity<String> inputKey3(@RequestBody String keyword) {
        System.out.println("keyWordTest1 : " + keyword);
        System.out.println("keyWordTest2 : " + keyword.toString());
        return ResponseEntity.ok(keyword);
    }
}
