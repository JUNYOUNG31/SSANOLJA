package com.ssanolja.backend.api.controller;

import com.ssanolja.backend.api.request.TelestationReq;
import com.ssanolja.backend.api.service.TelestationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/telestations")
public class TelestationController {
    private final TelestationService telestationService;

    public TelestationController(TelestationService telestationService){
        this.telestationService = telestationService;
    }


    @PostMapping("/InputKeyword")
    public ResponseEntity<Map<String , Object>> InputKeyword(@RequestBody TelestationReq telestationReq) {
        System.out.println("emfhdlddhej"+telestationReq.getDrawingOrder());
        Map<String, Object> res = telestationService.keywordInsert(telestationReq);

        System.out.println("res 체크 "+ res);
        return new ResponseEntity<>(res,  HttpStatus.ACCEPTED);

//        return new ResponseEntity<>(, HttpStatus.CREATED);
    }


    @GetMapping("/getData")
    public ResponseEntity<String> getData() {
//
        String keyword = "리스폰스 엔티티 테스트";
        System.out.println("리스폰스 엔티티 테스트");
//
        return ResponseEntity.ok(keyword);
    }

}






