package com.ssanolja.backend.db.repository;

import com.ssanolja.backend.db.entity.Game;
import com.ssanolja.backend.db.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findByRoom(Optional<Room> room);
}
