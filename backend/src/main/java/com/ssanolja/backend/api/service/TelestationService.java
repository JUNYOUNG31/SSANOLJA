package com.ssanolja.backend.api.service;

import ch.qos.logback.core.net.SyslogOutputStream;
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
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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


    public TelestationRes userInsert(List<User> users, Game game){
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

    public Map<String, Object> keywordInsert(TelestationReq telestationReq) {

        // ------------------------------- inserti
        Optional<Room> room = roomRepository.findByRoomCode(telestationReq.getRoomCode());
        Optional<Game> game = gameRepository.findByRoom(room);
        User user = userRepository.findByUserNickname(telestationReq.getUserNickname());

       Telestation tel = telestationRepository.findByUsersIdAndGamesId(user.getUsersId(), game.get().getPlayGameId());
       tel.setTextData(telestationReq.getKeyword());
       tel.setDrawingOrder(telestationReq.getDrawingOrder());//data_index추가
       telestationRepository.save(tel);
       System.out.println("tel+"+tel);

       //-------------------------------------changeInsert---------------------------------
        //user의 해당 userOrder따와서 +1
        //userOrder+1한 값의 애한테 UserOrder의 키워드
        //인원수넘으면 첫번째로 ㄱㄱ
        // userOrder +1 (다음사람) 의 userNickname과 내 데이터를 프론트로 보낸다.  >> 프론트에서 소켓메시지로 userNickname에게 내가 받은 키워드를 보낸다?
        Map<String, Object> sendKeyword = new HashMap<String, Object>();

        if(tel.getUserOrder() != telestationReq.getPersonnel()){ //처음으로
           Integer byUsersId = telestationRepository.findByUsersId(tel.getUserOrder() + 1 , game.get().getPlayGameId());

           System.out.println("============byuserId확인 " + byUsersId);

           String byUserNicknameFromUsersId = userRepository.findByUserNicknameFromUsersId(byUsersId);

           System.out.println("================userNickname 화긴 "+byUserNicknameFromUsersId);

            sendKeyword.put("userNickname", byUserNicknameFromUsersId);
            sendKeyword.put("keyword", telestationReq.getKeyword());
        }else{

            Integer byUsersId = telestationRepository.findByUsersId(1, game.get().getPlayGameId());

            System.out.println("============byuserId확인 " + byUsersId);

            String byUserNicknameFromUsersId = userRepository.findByUserNicknameFromUsersId(byUsersId);

            System.out.println("================userNickname 화긴 "+byUserNicknameFromUsersId);
            sendKeyword.put("userNickname", byUserNicknameFromUsersId);
            sendKeyword.put("keyword", telestationReq.getKeyword());
            //getUserOrder()+1에게 키워드 넘김
        }
        return sendKeyword;
    }
}
