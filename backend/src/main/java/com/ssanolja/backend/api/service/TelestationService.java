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

       //-------------------------------------뿌리는 과정---------------------------------
        Map<String, Object> sendData = new HashMap<String, Object>();

        if(tel.getUserOrder() != telestationReq.getPersonnel()){
           Integer byUsersId = telestationRepository.findUsersIdByUserOrderGamesId(tel.getUserOrder() + 1 , game.get().getPlayGameId());
           String byUserNicknameFromUsersId = userRepository.findByUserNicknameFromUsersId(byUsersId);

            sendData.put("userNickname", byUserNicknameFromUsersId);
            sendData.put("data", telestationReq.getData());

        }else{

            Integer byUsersId = telestationRepository.findUsersIdByUserOrderGamesId(1, game.get().getPlayGameId());

            String byUserNicknameFromUsersId = userRepository.findByUserNicknameFromUsersId(byUsersId);

            sendData.put("userNickname", byUserNicknameFromUsersId);
            sendData.put("data", telestationReq.getData());

        }
        return sendData;
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
                Telestation bestTele = telestationRepository.findByIndexAndDrawingOrder(telestationReq.getDataIndex(), telestationReq.getBestVote());
                System.out.println("    userNickname" + telestationReq.getUserNickname() + " / bestVote = " + bestVote);
                bestTele.setBestVote(bestVote + 1);
                telestationRepository.save(bestTele);

            }
            if(telestationReq.getWorstVote() != 0){
                Integer worstVote = telestationRepository.findWorstByDataIndexandDrawingOrder(telestationReq.getDataIndex(), telestationReq.getWorstVote());
                Telestation worstTele = telestationRepository.findByIndexAndDrawingOrder(telestationReq.getDataIndex(), telestationReq.getWorstVote());
                System.out.println("    userNickname" + telestationReq.getUserNickname() + " / worstVote = " + worstVote);
                worstTele.setWorstVote(worstVote + 1);
                telestationRepository.save(worstTele);
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

            returnData.put("dataIndex", dataIndex);
            returnData.put("dataList", getAlbumList);


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

        if(bestUser.getDrawingOrder() - 1 == 0){
            Integer personnel = telestationRepository.findCountByGamesIdDrawing_order(telestationReq.getGameId());
            Telestation preBestUser = telestationRepository.findPreUserByGamesIdDataIndexDrawingOrder(telestationReq.getGameId(), bestUser.getDataIndex(), personnel);
            preBestUserDrawingOrder = preBestUser.getDrawingOrder();
            preBestUserData = preBestUser.getData();
        }else{
            Telestation preBestUser = telestationRepository.findPreUserByGamesIdDataIndexDrawingOrder(telestationReq.getGameId(), bestUser.getDataIndex(), bestUser.getDrawingOrder() - 1);
            preBestUserDrawingOrder = preBestUser.getDrawingOrder();
            preBestUserData = preBestUser.getData();
        }

        // worst => 유저 닉네임
        String worstUserNickname = userRepository.findByUserNicknameFromUsersId(worstUserId);

        // worst => data, drawingOrder
        Telestation worstUser = telestationRepository.findWorstUserByGamesIdUsersId(gameId, worstUserId);
        Integer worstUserDrawingOrder = worstUser.getDrawingOrder();
        String worstUserData = worstUser.getData();

        // pre best => data, drawingOrder
        Integer preWorstUserDrawingOrder = 0;
        String preWorstUserData = null;
        if(worstUser.getDrawingOrder() - 1 == 0){
            Integer personnel = telestationRepository.findCountByGamesIdDrawing_order(telestationReq.getGameId());
            Telestation preWorstUser = telestationRepository.findPreUserByGamesIdDataIndexDrawingOrder(telestationReq.getGameId(), bestUser.getDataIndex(), personnel);
            preWorstUserDrawingOrder = preWorstUser.getDrawingOrder();
            preWorstUserData = preWorstUser.getData();

        }else{
            Telestation preWorstUser = telestationRepository.findPreUserByGamesIdDataIndexDrawingOrder(telestationReq.getGameId(), bestUser.getDataIndex(), bestUser.getDrawingOrder() - 1);
            preWorstUserDrawingOrder = preWorstUser.getDrawingOrder();
            preWorstUserData = preWorstUser.getData();
        }

        Map<String, Object> res = new HashMap<>();
        Map<String, Object> worst = new HashMap<>();
        Map<String, Object> best = new HashMap<>();

        res.put("worst", worst);
        res.put("best", best);

        worst.put("nickname", worstUserNickname);
        worst.put("preDrawingOrder", preWorstUserDrawingOrder);
        worst.put("preData", preWorstUserData);
        worst.put("drawingOrder", worstUserDrawingOrder);
        worst.put("data", worstUserData);

        best.put("nickname", bestUserNickname);
        best.put("preDrawingOrder", preBestUserDrawingOrder);
        best.put("preData", preBestUserData);
        best.put("drawingOrder", bestUserDrawingOrder);
        best.put("data", bestUserData);

        System.out.println("voteResult >> res = " + res);

        return res;
    }



}
