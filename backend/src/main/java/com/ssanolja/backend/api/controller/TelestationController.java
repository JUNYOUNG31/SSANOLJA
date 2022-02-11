package com.ssanolja.backend.api.controller;

import com.ssanolja.backend.api.request.TelestationReq;
import com.ssanolja.backend.api.service.TelestationService;
import com.ssanolja.backend.db.entity.Telestation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/telestations")
public class TelestationController {
    private final TelestationService telestationService;

    public TelestationController(TelestationService telestationService){
        this.telestationService = telestationService;
    }


    @PostMapping("/saveData")
    public ResponseEntity<Map<String , Object>> saveData(@RequestBody TelestationReq telestationReq) throws Exception {
        System.out.println("1. saveData 들어옴.");
        Map<String, Object> res = telestationService.saveData(telestationReq);
        System.out.println("res : "+ res);

        return new ResponseEntity<>(res,  HttpStatus.ACCEPTED);
    }


    @GetMapping("/showAlbum")//라운드마다 앨범 보내주는거
    public ResponseEntity<List<Telestation>> showAlbum(@RequestBody TelestationReq telestationReq) throws  Exception {


        List<Telestation> res = telestationService.showAlbum(telestationReq);

        System.out.println("res : " + res);

        return new ResponseEntity<>(res,  HttpStatus.ACCEPTED);
    }

    @GetMapping("/voteResult") // 투표 최종 결과 보내주는거
    public ResponseEntity<Map<String, Object>> voteResult(@RequestBody TelestationReq telestationReq) throws Exception{

        Map<String, Object> res = telestationService.voteResult(telestationReq);
        System.out.println("res : " + res);

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }






}






