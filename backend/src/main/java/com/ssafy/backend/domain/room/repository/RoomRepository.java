package com.ssafy.backend.domain.room.repository;


import com.ssafy.backend.domain.room.entity.Room;
import lombok.Builder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findByRoomCode(String roomCode);

}
