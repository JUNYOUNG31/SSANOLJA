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

import java.util.*;

@Service
@Slf4j
public class TelestationService {

    @Autowired
    private TelestationRepository telestationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;


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
            return telestationRes;
    }

    public Map<String, Object> saveData(TelestationReq telestationReq) {
        Integer dataIndex, preUserOrder;
        Telestation telestation = null;
        // ------------------------------- 저장과정이고
        // room_code >> find room_id
        Optional<Room> room = roomRepository.findByRoomCode(telestationReq.getRoomCode());

        // room_id >> game_id
        Optional<Game> game = gameRepository.findByRoom(room);  //game_id !!!!

        // user_nickname >> find user_id
        User user = userRepository.findByUserNickname(telestationReq.getUserNickname()); // user_id!!!!


        if(telestationReq.getDrawingOrder() != 1){

            telestation = saveUserAndGetUserOrder(user, game, telestationReq.getDrawingOrder());  //userOrder 찾아오고 사용자 정보 저장
            preUserOrder = (telestation.getUserOrder() - 1 == 0 ? telestationReq.getPersonnel() : telestation.getUserOrder() - 1); //userOrder 이전 값
            
            System.out.println("이전 유저오더 확인" + preUserOrder);

            dataIndex = getNotFirstRoundDataIndex(game.get().getPlayGameId(), preUserOrder, telestationReq.getDrawingOrder()); //두번째 라운드 부터 받은 데이터의 이전 data index 찾아오기
        }else{
            dataIndex = getFirstRoundDataIndex(game.get().getPlayGameId(),user.getUsersId()); //첫번째 라운드의 telestations_id 찾아오기
        }

        System.out.println("dataIndex 확인 " + dataIndex);


        //data, data_index, drawing_order(라운드) set
//       Telestation tel = telestationRepository.findByUsersIdAndGamesId(user.getUsersId(), game.get().getPlayGameId());
        telestation.setData(telestationReq.getData());
        telestation.setDataIndex(dataIndex);
        telestation.setDrawingOrder(telestationReq.getDrawingOrder());
       telestationRepository.save(telestation);
       System.out.println("telestation+"+telestation);

       //-------------------------------------뿌리는 과정---------------------------------

        Map<String, Object> sendData = new HashMap<String, Object>();

        if(telestation.getUserOrder() != telestationReq.getPersonnel()){ //처음으로
           Integer byUsersId = telestationRepository.findByUsersId(telestation.getUserOrder() + 1 , game.get().getPlayGameId());

           System.out.println("============byuserId확인 " + byUsersId);

           String byUserNicknameFromUsersId = userRepository.findByUserNicknameFromUsersId(byUsersId);

           System.out.println("================userNickname 화긴 "+byUserNicknameFromUsersId);

            sendData.put("userNickname", byUserNicknameFromUsersId);
            sendData.put("data", telestationReq.getData());
        }else{

            Integer byUsersId = telestationRepository.findByUsersId(1, game.get().getPlayGameId());

            System.out.println("============byuserId확인 " + byUsersId);

            String byUserNicknameFromUsersId = userRepository.findByUserNicknameFromUsersId(byUsersId);

            System.out.println("================userNickname 화긴 "+byUserNicknameFromUsersId);
            sendData.put("userNickname", byUserNicknameFromUsersId);
            sendData.put("data", telestationReq.getData());
            //getUserOrder()+1에게 키워드 넘김
        }
        return sendData;
    }



    private Telestation saveUserAndGetUserOrder(User user, Optional<Game> game,  Integer drawingOrder){

        //1. userid, gamesid, drawingorder로 drawing_order 1번의 user_order 찾아옴
        Telestation findUserOrder = telestationRepository.findByUsersIdGamesIdDrawingOrder(user.getUsersId(), game.get().getPlayGameId(), drawingOrder);
        Integer userOrder = findUserOrder.getUserOrder();

        System.out.println("userOrder 확인 "+ userOrder);

        //2. user_id, game_id, user_order 저장한다.
        //save(users_id games_id user_order)
        Telestation userInsert = telestationRepository.save(Telestation.builder()
                .user(user)
                //.game(game) //-> game 저장 어떻게 해야할지 타입 모르겠음.
                .userOrder(userOrder)
                .build()
        );
        System.out.println(userInsert);
        return userInsert;
    }

    private Integer getNotFirstRoundDataIndex(Integer gamesId, Integer preUserOrder, Integer drawingOrder){
        Telestation findDataIndex = telestationRepository.findByGamesIdUserOrderDrawingOrder(gamesId, preUserOrder, drawingOrder - 1);
        Integer dataIndex = findDataIndex.getDataIndex();
        return dataIndex;
    }

    private Integer getFirstRoundDataIndex(Integer gamesId, Integer usersId){
        Telestation findTelestationsId = telestationRepository.findByGamesIdUserId(gamesId, usersId);
        Integer dataIndex = findTelestationsId.getTelestationId();
        return dataIndex;
    }




}
