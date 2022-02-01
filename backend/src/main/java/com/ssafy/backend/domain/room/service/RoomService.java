package com.ssafy.backend.domain.room.service;

import com.ssafy.backend.domain.room.entity.Room;
import com.ssafy.backend.domain.room.repository.RoomRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    @Transactional
    public String makeRoomCode() {
        String roomCode = RandomStringUtils.randomAlphanumeric(7);
        roomRepository.save(Room.builder()
                .roomCode(roomCode)
                .build());
        return roomCode;
    }


    @Transactional
    public Optional<Room> findRoom(String roomCode) {
        return roomRepository.findByRoomCode(roomCode);

    }
}
