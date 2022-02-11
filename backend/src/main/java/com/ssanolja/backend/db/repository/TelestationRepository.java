package com.ssanolja.backend.db.repository;

import com.ssanolja.backend.db.entity.Game;
import com.ssanolja.backend.db.entity.Telestation;
import com.ssanolja.backend.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TelestationRepository extends JpaRepository<Telestation, Integer> {

    Telestation findByUser(User users);

    @Query(value = "SELECT * from Telestations where Telestations.users_id = ? and Telestations.games_id = ?", nativeQuery = true)
    Telestation findByUsersIdGamesId(Integer usersId, Integer gamesId);

    @Query(value = "select telestations_id from telestations where games_id = ? and users_id = ?", nativeQuery = true)
    Integer findTeleIdByGamesIdUsersId(Integer gamesId, Integer usersId);

    @Query(value = "SELECT * from Telestations where Telestations.users_id = ? and Telestations.games_id = ? and Telestations.drawing_order = ?", nativeQuery = true)
    Telestation FindByUsersIdGamesIdDrawingOrder(Integer usersId, Integer gamesId, Integer drawingOrder);


    @Query(value = "SELECT DISTINCT users_id from telestations where telestations.user_order = ? and telestations.games_id = ?", nativeQuery = true)
    Integer findUsersIdByUserOrderGamesId(Integer userOrder, Integer gamesId);






    @Query(value = "SELECT user_order from Telestations where Telestations.users_id = ? and Telestations.games_id = ? and Telestations.drawing_order = ? ", nativeQuery = true)
    Integer findUserOrderByUsersIdGamesIdDrawingOrder(Integer userId, Integer gamesId, Integer drawingOrder);


  @Query(value= "select data_index from telestations where games_id = ? and user_order = ? and drawing_order= ? ", nativeQuery = true)
    Integer findDataIndexByGamesIdUserOrderDrawingOrder(Integer gamesId, Integer userOrder, Integer DrawingOrder);

//    //나의 telestations_id 찾기
//    @Query(value = "select telestations_id from telestations where telestations_id = 187", nativeQuery = true)
////    @Query(value = "select telestations_id from telestations where telestations_id = (select a.telestations_id from ( select telestations_id from telestations where games_id = ? and users_id = ? ) a ) ", nativeQuery = true)
//        Telestation findByGamesIdUserId(Integer gamesId, Integer usersId);

    Game findByGame(Integer telestations_id);


    //--------findDataIndex
    @Query(value = " select data_index from telestations where games_id = ? and user_order = ? and drawing_order = 1", nativeQuery = true)
    Integer findDataIndexByGamesIdUserOrder(Integer gamesId, Integer round);

    //--------DataGet-------------------
    @Query(value = "select data from telestations where games_id = ? and data_index = ?", nativeQuery = true)
    List<Telestation> findDataByGamesIdUserOrder(Integer gamesId, Integer dataIndex);


    //----------VOTE--------------------
    @Query(value = "select best_vote, worst_vote  from telestations where data_index = ? and drawing_order = ? ", nativeQuery = true)
    Telestation findVoteByDataIndexandDrawingOrder(Integer dataIndex, Integer drawinOrder);


}