package com.ssanolja.backend.api.controller;

import com.ssanolja.backend.api.request.GetRuleReq;
import com.ssanolja.backend.api.request.StartGameReq;
import com.ssanolja.backend.api.response.GameRes;
import com.ssanolja.backend.api.response.RuleRes;
import com.ssanolja.backend.api.service.GameService;
import com.ssanolja.backend.api.service.SpyfallService;
import com.ssanolja.backend.api.service.UserService;
import com.ssanolja.backend.db.entity.Game;
import com.ssanolja.backend.db.entity.User;
import com.ssanolja.backend.util.RuleUtil;
import org.springframework.http.HttpStatus;
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
    private final SpyfallService spyfallService;
    private final UserService userService;


    public GameController(GameService gameService, SpyfallService spyfallService, UserService userService) {
        this.gameService = gameService;
        this.spyfallService = spyfallService;
        this.userService = userService;
    }

    @PostMapping("/start")
    public ResponseEntity<GameRes> startGame(@RequestBody StartGameReq startGameReq) {
        String roomCode = startGameReq.getRoomCode();
        Game game = gameService.makeGame(roomCode);
        List<String> userNicknames = startGameReq.getUserNicknames();
        List<User> users = userService.getUserList(userNicknames);
        String selectedGame = startGameReq.getSelectedGame();
        if (selectedGame.equals("Spyfall")) {
//            spyfallService
            return new ResponseEntity<>(spyfallService.makeSpyfall(users, game), HttpStatus.CREATED);
//        }
//        else if (selectedGame.equals("telestation")) {
////            telestationService
//            return "telestation";
//        }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/rules")
    public ResponseEntity getRule(@RequestBody GetRuleReq getRuleReq) {
        Integer personnel = getRuleReq.getPersonnel();
        String selectedGame = getRuleReq.getSelectedGame();

        RuleUtil rule = RuleUtil.valueOf(selectedGame.toUpperCase());
        if (personnel > rule.getMaxPeople() || personnel < rule.getMinPeople()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            int gameType = rule.getGameType();
            RuleRes res = RuleRes.builder()
                    .playTime(rule.getPlayTime())
                    .drawingTime(rule.getDrawingTime())
                    .meetingTime(rule.getMeetingTime())
                    .textingTime(rule.getTextingTime())
                    .voteTime(rule.getVoteTime())
                    .build();

            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }
    }
}
