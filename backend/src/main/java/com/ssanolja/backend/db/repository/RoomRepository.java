package com.ssanolja.backend.db.repository;


import com.ssanolja.backend.db.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findByRoomCode(String roomCode);
//    Room findByRoomCode(String roomCode);

    @Query(value = "select rooms_id from rooms where room_code = ?", nativeQuery = true)
    Integer findRoomIdByRoomCode(String roomCode);

}
