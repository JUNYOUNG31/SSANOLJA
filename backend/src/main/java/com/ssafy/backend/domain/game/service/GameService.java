package com.ssafy.backend.domain.game.service;

import com.ssafy.backend.domain.game.entity.Game;
import com.ssafy.backend.domain.game.repository.GameRepository;
import com.ssafy.backend.domain.room.entity.Room;
import com.ssafy.backend.domain.room.service.RoomService;
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