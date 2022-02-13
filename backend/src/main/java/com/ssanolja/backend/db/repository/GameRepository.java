package com.ssanolja.backend.db.repository;

import com.ssanolja.backend.db.entity.Game;
import com.ssanolja.backend.db.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findByRoom(Optional<Room> room);

    @Query(value = "select * from games where rooms_id = ? order by games_id desc limit 1", nativeQuery = true)
    Optional<Game> findByRoomId(Optional<Room> room);
}
