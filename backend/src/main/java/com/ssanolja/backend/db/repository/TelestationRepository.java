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

    @Query(value = "SELECT distineusers_id from telestations where telestations.user_order = ? and telestations.games_id = ?", nativeQuery = true)
    Integer findByUsersId(Integer userOrder, Integer gamesId);

//    //drawing_order와 users_id와 games_id로 user_order 찾음
    @Query(value = "SELECT user_order from Telestations where Telestations.users_id = ? and Telestations.games_id = ? and Telestations.drawing_order = ? ", nativeQuery = true)
    Telestation findByUsersIdGamesIdDrawingOrder(Integer userId, Integer gamesId, Integer drawingOrder);


    //내 전의 data_index 찾기
  @Query(value= "select data_index from telestations where data_index = ( select data_index from telestations where user_order = ? and games_id = ? and drawing_order = ? )", nativeQuery = true)
    Telestation findByGamesIdUserOrderDrawingOrder(Integer gamesId, Integer userOrder, Integer DrawingOrder);



    //나의 telestations_id 찾기
    @Query(value = "select telestations_id from telestations where telestations_id = (select a.telestations_id from ( select telestations_id from telestations where games_id = ? and users_id = ? ) a ) ", nativeQuery = true)
        Telestation findByGamesIdUserId(Integer gamesId, Integer usersId);
        
}