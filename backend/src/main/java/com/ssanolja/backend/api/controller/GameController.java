package com.ssanolja.backend.api.controller;

import com.ssanolja.backend.api.request.StartGameReq;
import com.ssanolja.backend.api.response.GameRes;
import com.ssanolja.backend.api.service.GameService;
import com.ssanolja.backend.db.entity.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;


    public GameController(GameService gameService) {
        this.gameService = gameService;

    }


//    @PostMapping("/start")
//    public ResponseEntity<GameRes> startGame(@RequestBody StartGameReq startGameReq) {
//        String roomCode = startGameReq.getRoomCode();
//        Game game = gameService.makeGame(roomCode);
//        List<String> users = startGameReq.getUsers();
//        // game 이용하여 방 안의 사용자마다 게임 객체 만들기
//        String selectedGame = startGameReq.getSelectedGame();
//        if (selectedGame.equals("spyfall")) {
////            spyfallService
//
//            return "spyfall";
//        } else if (selectedGame.equals("telestation")) {
////            telestationService
//            return "telestation";
//        }
//
//        return "ok";
//    }
}
