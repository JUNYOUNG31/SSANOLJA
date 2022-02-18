package com.ssanolja.backend.api.service;

import com.ssanolja.backend.db.entity.Game;
import com.ssanolja.backend.db.repository.GameRepository;
import com.ssanolja.backend.db.entity.Room;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;



@Service
public class GameService {

    private final GameRepository gameRepository;
    private final RoomService roomService;

    public GameService(GameRepository gameRepository, RoomService roomService) {
        this.gameRepository = gameRepository;
        this.roomService = roomService;
    }

    @Transactional
    public Game makeGame(String roomCode) {
        Room room = roomService.findRoom(roomCode).get();

        return gameRepository.save(Game.builder()
                .room(room)
                .build());
    }




}