package com.ssanolja.backend.db.repository;

import com.ssanolja.backend.db.entity.Game;
import com.ssanolja.backend.db.entity.Telestation;
import com.ssanolja.backend.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface TelestationRepository extends JpaRepository<Telestation, Integer> {

    Telestation findByUser(User users);

    @Query(value = "SELECT * from telestations where telestations.users_id = ? and telestations.games_id = ?", nativeQuery = true)
    Telestation findByUsersIdGamesId(Integer usersId, Integer gamesId);

    @Query(value = "select telestations_id from telestations where games_id = ? and users_id = ?", nativeQuery = true)
    Integer findTeleIdByGamesIdUsersId(Integer gamesId, Integer usersId);

    @Query(value = "SELECT * from telestations where telestations.users_id = ? and telestations.games_id = ? and telestations.drawing_order = ?", nativeQuery = true)
    Telestation FindByUsersIdGamesIdDrawingOrder(Integer usersId, Integer gamesId, Integer drawingOrder);


    @Query(value = "SELECT DISTINCT users_id from telestations where telestations.user_order = ? and telestations.games_id = ?", nativeQuery = true)
    Integer findUsersIdByUserOrderGamesId(Integer userOrder, Integer gamesId);






    @Query(value = "SELECT user_order from telestations where telestations.users_id = ? and telestations.games_id = ? and telestations.drawing_order = ? ", nativeQuery = true)
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
    @Query(value = "select data from telestations where games_id = ? and data_index = ? and drawing_order = ?", nativeQuery = true)
    String findDataByGamesIdUserOrder(Integer gamesId, Integer dataIndex, Integer drawingOrder);


    //----------VOTE--------------------
    @Modifying(clearAutomatically = true)
    @Query(value = "update telestations set best_vote = 1+ best_vote where data_index = ? and drawing_order = ?", nativeQuery = true)
    Integer findBestByDataIndexandDrawingOrder(Integer dataIndex, Integer bestVote);

    @Modifying(clearAutomatically = true)
    @Query(value = "update telestations set worst_vote = worst_vote + 1 where data_index = ? and drawing_order = ? ", nativeQuery = true)
    Integer findWorstByDataIndexandDrawingOrder(Integer dataIndex, Integer worstVote);

    @Query(value = "select *  from telestations where data_index = ? and drawing_order = ? ", nativeQuery = true)
    Telestation findByIndexAndDrawingOrder(Integer dataIndex, Integer bestVote);

    //--------------showAlbum find usersId
    @Query(value = "select users_id from telestations where data_index = ? and drawing_order= ?", nativeQuery = true)
    Integer findUsersIdByDataIndexDrawingOrder(Integer dataIndex, Integer drawingOrder);

    //--------DataGet-------------------
    @Query(value = "select data_index, data from telestations where games_id = ? and data_index = ( select data_index from telestations where games_id = ? and user_order = ? and drawing_order = 1)", nativeQuery = true)
    List<Telestation> findDataIndexDataByGamesIdUserOrder(Integer gamesId, Integer dataIndex, Integer gamesId2, Integer round);


    //----------VOTE RESULT--------------------
    // 베스트, 워스트 유저 id 찾기 ( user_nickname 찾기 ) 
    @Query(value = "select users_id from telestations where games_id = ? group by users_id order by SUM(best_vote) desc limit 1" , nativeQuery = true)
    Integer findSumBestVoteUsersIdByGamesId(Integer gamesId);

    @Query(value = "select users_id from telestations where games_id = ? group by users_id order by SUM(worst_vote) desc limit 1" , nativeQuery = true)
    Integer findSumWorstVoteUsersIdByGamesId(Integer gamesId);

    // 베스트, 워스트 유저 최다 득표 컬럼 찾기 ( data, drawing_order 찾기 위해 )
    @Query(value = "select * from telestations where games_id = ? and users_id = ? order by best_vote desc limit 1", nativeQuery = true)
    Telestation findBestUserByGamesIdUsersId(Integer gamesId, Integer usersId);

    @Query(value = "select * from telestations where games_id = ? and users_id = ? order by worst_vote desc limit 1", nativeQuery = true)
    Telestation findWorstUserByGamesIdUsersId(Integer gamesId, Integer usersId);

    // 이전 유저 컬럼 찾기 (  data, drawing_order 찾기 위해 )
    @Query(value ="select * from telestations where games_id = ? and data_index = ? and drawing_order = ?", nativeQuery = true)
    Telestation findPreUserByGamesIdDataIndexDrawingOrder(Integer gamesId, Integer dataIndex, Integer drawingOrder);

    // 게임 방의 인원수 찾기
    @Query(value = "select count(*) from telestations where games_id = ? and drawing_order = 1", nativeQuery = true)
    Integer findCountByGamesIdDrawing_order(Integer gamesId);

    //최다 베스트 투표 데이터의 데이터 인덱스 찾기
    @Query(value = "select data_index from telestations where games_id = ? and users_Id = ? order by best_vote desc limit 1", nativeQuery = true)
    Integer findBestDataIndexByGamesIdUsersId(Integer gamesId, Integer usersId);

    //최다 워스트 투표 데이터의 데이터 인덱스 찾기
    @Query(value = "select data_index from telestations where games_id = ? and users_Id = ? order by worst_vote desc limit 1", nativeQuery = true)
    Integer findWorstDataIndexByGamesIdUsersId(Integer gamesId, Integer usersId);

    // 최다 베스트 투표 데이터의 preUsersId 찾기
    @Query(value = "select users_id from telestations where data_index = ? and drawing_order = ?", nativeQuery = true)
    Integer findPreBestUsersIdByDataIndexDrawingOrder(Integer dataIndex, Integer drawingOrder);

    // 최다 워스트 투표 데이터의 preUsersId 찾기
    @Query(value = "select users_id from telestations where data_index = ? and drawing_order = ?", nativeQuery = true)
    Integer findPreWorstUsersIdByDataIndexDrawingOrder(Integer dataIndex, Integer drawingOrder);




}