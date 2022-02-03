package com.ssanolja.backend.api.service;

import com.ssanolja.backend.db.entity.Game;
import com.ssanolja.backend.db.entity.User;
import com.ssanolja.backend.db.repository.GameRepository;
import com.ssanolja.backend.db.entity.Room;
import com.ssanolja.backend.db.repository.UserRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final RoomService roomService;

    public GameService(GameRepository gameRepository, UserRepository userRepository, RoomService roomService) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.roomService = roomService;
    }

    @Transactional
    public Game makeGame(String roomCode) {
        Room room = roomService.findRoom(roomCode).get();

        return gameRepository.save(Game.builder()
                .room(room)
                .build());
    }

    @Transactional
    public List<User> getUserList(List<String> userNicknames) {
        List<User> userList = new ArrayList<User>();
        for (String userNickname :
                userNicknames) {
            userList.add(userRepository.findByUserNickname(userNickname));
        }

        return userList;
    }

}