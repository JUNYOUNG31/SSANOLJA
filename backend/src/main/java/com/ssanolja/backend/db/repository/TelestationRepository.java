package com.ssanolja.backend.db.repository;

import com.ssanolja.backend.db.entity.Telestation;
import com.ssanolja.backend.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TelestationRepository extends JpaRepository<Telestation, Integer> {

    Telestation findByUser(User users);

    @Query(value = "SELECT * from Telestations where Telestations.users_id = ? and Telestations.games_id = ?", nativeQuery = true)
    Telestation findByUsersIdAndGamesId(Integer usersId, Integer gamesId);

    @Query(value = "SELECT users_id from telestations where telestations.user_order = ? and telestations.games_id = ?", nativeQuery = true)
    Integer findByUsersId(Integer userOrder, Integer gamesId);
}