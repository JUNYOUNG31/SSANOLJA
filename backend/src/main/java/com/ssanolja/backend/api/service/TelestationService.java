package com.ssanolja.backend.api.service;

import com.ssanolja.backend.api.request.TelestationReq;
import com.ssanolja.backend.api.response.TelestationRes;
import com.ssanolja.backend.db.entity.Game;
import com.ssanolja.backend.db.entity.Room;
import com.ssanolja.backend.db.entity.Telestation;
import com.ssanolja.backend.db.entity.User;
import com.ssanolja.backend.db.repository.GameRepository;
import com.ssanolja.backend.db.repository.RoomRepository;
import com.ssanolja.backend.db.repository.TelestationRepository;
import com.ssanolja.backend.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
@Transactional
public class TelestationService {

    @Autowired
    private TelestationRepository telestationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomService roomService;


    public TelestationRes usersInsert(List<User> users, Game game){
            TelestationRes telestationRes = new TelestationRes();

            System.out.println(users);
            for(int i = 0; i < users.size(); i++) {
                Telestation player = telestationRepository.save(Telestation.builder()
                        .user(users.get(i))
                        .game(game)
                        .userOrder(i+1)
                        .build());
            }
            System.out.println("game_id 보내는거 확인"+ game);
            telestationRes.setPlayGameId(game.getPlayGameId());

            return telestationRes;
    }

    // 받는 데이터 : data, userNickname, roomCode, drawingOrder, personnel
    // 주는 데이터 : 다음 사람 userNickname
    public Map<String, Object> saveData(TelestationReq telestationReq) throws Exception {

        Integer dataIndex;
        int preUserOrder;
        Telestation telestation = null;
        // room_code >> find room_id
        Optional<Room> room = roomRepository.findByRoomCode(telestationReq.getRoomCode());

        // room_id >> game_id
        Optional<Game> game = gameRepository.findByRoomId(room);

//         user_nickname >> find user_id
        User user = userRepository.findByUserNickname(telestationReq.getUserNickname());

        if(telestationReq.getDrawingOrder() != 1){

            telestation = saveUserAndGetUserOrder(user, game, telestationReq.getDrawingOrder());  //userOrder 찾아오고 사용자 정보 저장

            preUserOrder = (telestation.getUserOrder() - 1 == 0 ? telestationReq.getPersonnel() : telestation.getUserOrder() - 1); //userOrder 이전 값

         dataIndex = getNotFirstRoundDataIndex(game.get().getPlayGameId(), preUserOrder, telestationReq.getDrawingOrder()); //두번째 라운드 부터 받은 데이터의 이전 data index 찾아오기

        }else{

            SetFirstDrawingData(user.getUsersId(), game.get().getPlayGameId());
            dataIndex = telestationRepository.findTeleIdByGamesIdUsersId(game.get().getPlayGameId(), user.getUsersId());

        }

        Telestation tel = SetDataAndDataIndex(user.getUsersId(), game.get().getPlayGameId(), telestationReq.getDrawingOrder(), telestationReq.getData(), dataIndex);
//
//       //-------------------------------------뿌리는 과정---------------------------------
        Map<String, Object> sendData = new HashMap<String, Object>();
//
        if(tel.getUserOrder() != telestationReq.getPersonnel()){
           Integer byUsersId = telestationRepository.findUsersIdByUserOrderGamesId(tel.getUserOrder() + 1 , game.get().getPlayGameId());
           String byUserNicknameFromUsersId = userRepository.findByUserNicknameFromUsersId(byUsersId);

            sendData.put("userNickname", byUserNicknameFromUsersId);
//            sendData.put("data", telestationReq.getData());

        }else{

            Integer byUsersId = telestationRepository.findUsersIdByUserOrderGamesId(1, game.get().getPlayGameId());

            String byUserNicknameFromUsersId = userRepository.findByUserNicknameFromUsersId(byUsersId);

            sendData.put("userNickname", byUserNicknameFromUsersId);
//            sendData.put("data", telestationReq.getData());

        }
        return sendData;



//        String roomCode = telestationReq.getRoomCode();
//        Integer roomId = roomRepository.findRoomIdByRoomCode(roomCode);
//        Integer gamesId = gameRepository.findGamesIdByRoomsId(roomId);
//        String userNickname = telestationReq.getUserNickname();
//        Integer usersId = userRepository.findUsersIdByUserNickname(userNickname);
//        Integer drawingOrder = telestationReq.getDrawingOrder();
//        Integer userOrder = telestationRepository.findUserOrderByGamesIdUsersIdDrawingOrder(gamesId, usersId, drawingOrder);
//        Integer personnel = telestationReq.getPersonnel();
//        String data = telestationReq.getData();
//        Integer nextUserId;
//
//        if((userOrder + 1) > personnel ) {
//            nextUserId = telestationRepository.findUsersIdByGamesIdUserOrderDrawingOrder(gamesId, drawingOrder, 1);
//        }else {
//            nextUserId = telestationRepository.findUsersIdByGamesIdUserOrderDrawingOrder(gamesId, drawingOrder, userOrder + 1);
//        }
//        String nextUserNickname = userRepository.findByUserNicknameFromUsersId(nextUserId);
//
//        Telestation setData = telestationRepository.findByUsersIdGamesIdDrawingOrder(usersId, gamesId, drawingOrder);
//        setData.setData(data);
//
//        Map<String, Object> sendData = new HashMap<String, Object>();
//        sendData.put("userNickname", nextUserNickname);
//
//        return sendData;

    }

    public Map<String, Object> getData(TelestationReq telestationReq) throws Exception {

        String roomCode = telestationReq.getRoomCode();
        Integer roomId = roomRepository.findRoomIdByRoomCode(roomCode);
        Integer gamesId = gameRepository.findGamesIdByRoomsId(roomId);
        String userNickname = telestationReq.getUserNickname();
        Integer usersId = userRepository.findUsersIdByUserNickname(userNickname);
        Integer drawingOrder = telestationReq.getDrawingOrder();
        Integer userOrder = telestationRepository.findUserOrderByGamesIdUsersIdDrawingOrder(gamesId, usersId, drawingOrder);
        Integer personnel = telestationReq.getPersonnel();
        String  data;

        if((userOrder - 1) == 0){
            data = telestationRepository.findDataByGamesIdDrawingOrderUserOrder(gamesId, drawingOrder, personnel);
        }else {
            data = telestationRepository.findDataByGamesIdDrawingOrderUserOrder(gamesId, drawingOrder, userOrder - 1);
        }

        Map<String, Object> sendData = new HashMap<String, Object>();
        sendData.put("data", data);

        return  sendData;
    }


    private Telestation saveUserAndGetUserOrder(User user, Optional<Game> game,  Integer drawingOrder) throws Exception {

        Integer findUserOrder = telestationRepository.findUserOrderByUsersIdGamesIdDrawingOrder(user.getUsersId(), game.get().getPlayGameId(), 1);

        Integer userOrder = findUserOrder;

        Telestation userInsert = telestationRepository.save(Telestation.builder()
                .user(user)
                .game(game.orElseThrow(() -> new Exception("ohlcv result set null")))
                .userOrder(userOrder)
                .drawingOrder(drawingOrder)
                .build()
        );

        return userInsert;
    }

    private Integer getNotFirstRoundDataIndex(Integer gamesId, Integer preUserOrder, Integer drawingOrder){
        Integer findDataIndex = telestationRepository.findDataIndexByGamesIdUserOrderDrawingOrder(gamesId, preUserOrder, drawingOrder - 1);
        return findDataIndex;
    }


    private void SetFirstDrawingData(int userId, int gamesId) {
        Telestation tel = telestationRepository.findByUsersIdGamesId(userId, gamesId);
        tel.setDrawingOrder(1);
        telestationRepository.save(tel);
    }



   private Telestation SetDataAndDataIndex(Integer usersId, Integer gamesId, Integer drawingOrder, String data, Integer dataIndex){
       Telestation tel = telestationRepository.FindByUsersIdGamesIdDrawingOrder(usersId, gamesId, drawingOrder);
       tel.setData(data);
       tel.setDataIndex(dataIndex);
       telestationRepository.save(tel);
       return tel;
   }

    public   Map<String,Object> showAlbum(TelestationReq telestationReq){

        Integer personnel = telestationRepository.findCountByGamesIdDrawing_order(telestationReq.getGameId());

        Map<String, Object> returnData = new HashMap<String, Object>();
        if(telestationReq.getRound() != 1){

            System.out.println("=================  " + telestationReq.getRound() + "  =================");

            if(telestationReq.getBestVote() != 0){
                Integer bestVote = telestationRepository.findBestByDataIndexandDrawingOrder(telestationReq.getDataIndex(),  telestationReq.getBestVote());
                System.out.println("    userNickname" + telestationReq.getUserNickname() + " / bestVote = " + bestVote);

            }
            if(telestationReq.getWorstVote() != 0){
                Integer worstVote = telestationRepository.findWorstByDataIndexandDrawingOrder(telestationReq.getDataIndex(), telestationReq.getWorstVote());
                System.out.println("    userNickname" + telestationReq.getUserNickname() + " / worstVote = " + worstVote);
            }


        }
        if(telestationReq.getRound() > personnel) {
            return returnData = voteResult(telestationReq);
        }

            Integer dataIndex = telestationRepository.findDataIndexByGamesIdUserOrder(telestationReq.getGameId(), telestationReq.getRound());


//        List<Telestation> getAlbumList = getAlbumList(telestationReq, dataIndex);
            List<String> getAlbumList = new ArrayList<>();
            for (int i = 1; i <= personnel; i++) {
                getAlbumList.add(getAlbumList(telestationReq, dataIndex, i));
            }
            List<String> getUserNicknameList = new ArrayList<>();
            for (int i = 1; i<= personnel; i++) {
                Integer getUsersId = telestationRepository.findUsersIdByDataIndexDrawingOrder(dataIndex, i);
                String getUserNickname =  userRepository.findByUserNicknameFromUsersId(getUsersId);
                getUserNicknameList.add(getUserNickname);
            }

            returnData.put("dataIndex", dataIndex);
            returnData.put("dataList", getAlbumList);
            returnData.put("userNicknameList", getUserNicknameList);

        return returnData;
    }



    private String getAlbumList(TelestationReq telestationReq, Integer dataIndex, Integer drawingOrder){

        return telestationRepository.findDataByGamesIdUserOrder(telestationReq.getGameId(), dataIndex, drawingOrder);
    }


    public Map<String, Object> voteResult(TelestationReq telestationReq){

        Integer gameId = telestationReq.getGameId();
        Integer bestUsersId = telestationRepository.findSumBestVoteUsersIdByGamesId(gameId);
        Integer worstUserId = telestationRepository.findSumWorstVoteUsersIdByGamesId(gameId);
        // best => 유저 닉네임
        String bestUserNickname = userRepository.findByUserNicknameFromUsersId(bestUsersId);

        // best => data, drawingOrder
        Telestation bestUser = telestationRepository.findBestUserByGamesIdUsersId(gameId, bestUsersId);
        Integer bestUserDrawingOrder = bestUser.getDrawingOrder();
        String  bestUserData = bestUser.getData();

        // pre best => data, drawingOrder
        Integer preBestUserDrawingOrder = 0;
        String preBestUserData = null;

        // pre nickname
        Integer bestUsersId2 = telestationRepository.findUsersIdByGamesIdGroupByUsersIdOrderBySumBestVoteDescLimit(telestationReq.getGameId());
        Telestation bestUserColumn = telestationRepository.findAllByGamesIdUsersIdOrderByBestVoteDescLimit(telestationReq.getGameId(), bestUsersId2);
        Integer preBestUserId = telestationRepository.findUsersIdByGamesIdDataIndexDrawingOrder(telestationReq.getGameId(), bestUserColumn.getDataIndex(), bestUserColumn.getDrawingOrder() - 1);
        String PreBestUserNickname = userRepository.findByUserNicknameFromUsersId(preBestUserId);
        preBestUserDrawingOrder = bestUserColumn.getDrawingOrder() - 1;
        bestUserDrawingOrder = bestUserColumn.getDrawingOrder();
        preBestUserData = telestationRepository.findDataByGamesIdDataIndexDrawingOrder(telestationReq.getGameId(), bestUserColumn.getDataIndex(), bestUserColumn.getDrawingOrder() - 1);

        // worst => 유저 닉네임
        String worstUserNickname = userRepository.findByUserNicknameFromUsersId(worstUserId);
        
        // worst => data, drawingOrder
        Telestation worstUser = telestationRepository.findWorstUserByGamesIdUsersId(gameId, worstUserId);
        Integer worstUserDrawingOrder = worstUser.getDrawingOrder();
        String worstUserData = worstUser.getData();

        // pre best => data, drawingOrder
        Integer preWorstUserDrawingOrder = 0;
        String preWorstUserData = null;

        // pre nickname
        Integer worstUsersId2 = telestationRepository.findUsersIdByGamesIdGroupByUsersIdOrderBySumWorstVoteDescLimit(telestationReq.getGameId());
        Telestation worstUserColumn = telestationRepository.findAllByGamesIdUsersIdOrderByWorstVoteDescLimit(telestationReq.getGameId(), worstUsersId2);
        Integer preWorstUserId = telestationRepository.findUsersIdByGamesIdDataIndexDrawingOrder(telestationReq.getGameId(), worstUserColumn.getDataIndex(), worstUserColumn.getDrawingOrder() - 1);
        String PreWorstUserNickname = userRepository.findByUserNicknameFromUsersId(preWorstUserId);
        preWorstUserDrawingOrder = worstUserColumn.getDrawingOrder() - 1;
        worstUserDrawingOrder = worstUserColumn.getDrawingOrder();
        preWorstUserData = telestationRepository.findDataByGamesIdDataIndexDrawingOrder(telestationReq.getGameId(), worstUserColumn.getDataIndex(), worstUserColumn.getDrawingOrder() - 1);

        Map<String, Object> res = new HashMap<>();
        Map<String, Object> worst = new HashMap<>();
        Map<String, Object> best = new HashMap<>();

        res.put("worst", worst);
        res.put("best", best);

        worst.put("nickname", worstUserNickname);
        worst.put("preNickname", PreWorstUserNickname);
        worst.put("preDrawingOrder", preWorstUserDrawingOrder);
        worst.put("preData", preWorstUserData);
        worst.put("drawingOrder", worstUserDrawingOrder);
        worst.put("data", worstUserData);

        best.put("nickname", bestUserNickname);
        best.put("preNickname", PreBestUserNickname);
        best.put("preDrawingOrder", preBestUserDrawingOrder);
        best.put("preData", preBestUserData);
        best.put("drawingOrder", bestUserDrawingOrder);
        best.put("data", bestUserData);

        return res;
    }



}
