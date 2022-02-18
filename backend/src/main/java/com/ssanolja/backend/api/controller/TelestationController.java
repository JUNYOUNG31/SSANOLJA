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
        Map<String, Object> res = telestationService.saveData(telestationReq);

        return new ResponseEntity<>(res,  HttpStatus.ACCEPTED);
    }


    @PostMapping("/showAlbum")//라운드마다 앨범 보내주는거
    public ResponseEntity<Map<String,Object>> showAlbum(@RequestBody TelestationReq telestationReq) throws  Exception {

        Map<String,Object> res = telestationService.showAlbum(telestationReq);

        if(res == null){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(res,  HttpStatus.ACCEPTED);
    }

    @GetMapping("/getData")
    public ResponseEntity<Map<String, Object>> getData(@RequestParam String userNickname, int gameId, int drawingOrder) throws Exception {

        Map<String, Object> res = telestationService.getData(userNickname,gameId, drawingOrder);

        return  new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }







}






