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
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
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

            telestationRes.setPlayGameId(game.getPlayGameId());

            return telestationRes;
    }

    public Map<String, Object> saveData(TelestationReq telestationReq) throws Exception {

        Integer dataIndex, preUserOrder;
        Telestation telestation = null;


        //--------------------DUMMY-------------------------------
        Telestation telestation1 = null;
        Telestation telestation2 = null;
        Integer dataIndex1 = null, dataIndex2 = null;
        Integer preUserOrder1, preUserOrder2;
        //------------------------------------


        // room_code >> find room_id
        Optional<Room> room = roomRepository.findByRoomCode(telestationReq.getRoomCode());

        // room_id >> game_id
        Optional<Game> game = gameRepository.findByRoom(room);

        //--------------------DUMMY-------------------------------
        User user1 = userRepository.findByUserNickname("조성현");
        User user2 = userRepository.findByUserNickname("강광은");
        //----------------------------------------------------------

        // user_nickname >> find user_id
        User user = userRepository.findByUserNickname(telestationReq.getUserNickname());

        if(telestationReq.getDrawingOrder() != 1){

            //--------------------DUMMY-------------------------------
            telestation1 = saveUserAndGetUserOrder(user1, game, telestationReq.getDrawingOrder());
            telestation2 = saveUserAndGetUserOrder(user2, game, telestationReq.getDrawingOrder());
            //----------------------------------------------------------

            telestation = saveUserAndGetUserOrder(user, game, telestationReq.getDrawingOrder());  //userOrder 찾아오고 사용자 정보 저장

            //--------------------DUMMY-------------------------------
            preUserOrder1 = (telestation1.getUserOrder() - 1 == 0 ? telestationReq.getPersonnel() : telestation1.getUserOrder() - 1); //userOrder 이전 값
            preUserOrder2 = (telestation2.getUserOrder() - 1 == 0 ? telestationReq.getPersonnel() : telestation2.getUserOrder() - 1); //userOrder 이전 값
            //---------------------------------------------------

            preUserOrder = (telestation.getUserOrder() - 1 == 0 ? telestationReq.getPersonnel() : telestation.getUserOrder() - 1); //userOrder 이전 값


            //--------------------DUMMY-------------------------------
            dataIndex1 = getNotFirstRoundDataIndex(game.get().getPlayGameId(), preUserOrder1, telestationReq.getDrawingOrder()); //두번째 라운드 부터 받은 데이터의 이전 data index 찾아오기
            dataIndex2 = getNotFirstRoundDataIndex(game.get().getPlayGameId(), preUserOrder2, telestationReq.getDrawingOrder()); //두번째 라운드 부터 받은 데이터의 이전 data index 찾아오기
            //-----------------------------------------------------

            dataIndex = getNotFirstRoundDataIndex(game.get().getPlayGameId(), preUserOrder, telestationReq.getDrawingOrder()); //두번째 라운드 부터 받은 데이터의 이전 data index 찾아오기

        }else{

            //--------------------DUMMY-------------------------------
            SetFirstDrawingData(user1.getUsersId(), game.get().getPlayGameId());
            SetFirstDrawingData(user2.getUsersId(), game.get().getPlayGameId());
            //------------------------------------------------------------------

            SetFirstDrawingData(user.getUsersId(), game.get().getPlayGameId());

            //--------------------DUMMY-------------------------------
            dataIndex1 = telestationRepository.findTeleIdByGamesIdUsersId(game.get().getPlayGameId(), user1.getUsersId());
            dataIndex2 = telestationRepository.findTeleIdByGamesIdUsersId(game.get().getPlayGameId(), user2.getUsersId());
            //------------------------------------------------------------------

            dataIndex = telestationRepository.findTeleIdByGamesIdUsersId(game.get().getPlayGameId(), user.getUsersId());

        }

        //--------------------DUMMY-------------------------------
        SetDataAndDataIndex(user1.getUsersId(), game.get().getPlayGameId(), telestationReq, dataIndex1);
        SetDataAndDataIndex(user2.getUsersId(), game.get().getPlayGameId(), telestationReq, dataIndex2);
        //------------------------------------------------------------------


        Telestation tel = SetDataAndDataIndex(user.getUsersId(), game.get().getPlayGameId(), telestationReq, dataIndex);


       //-------------------------------------뿌리는 과정---------------------------------
        Map<String, Object> sendData = new HashMap<String, Object>();

        if(tel.getUserOrder() != telestationReq.getPersonnel()){
           Integer byUsersId = telestationRepository.findUsersIdByUserOrderGamesId(tel.getUserOrder() + 1 , game.get().getPlayGameId());
           String byUserNicknameFromUsersId = userRepository.findByUserNicknameFromUsersId(byUsersId);

            sendData.put("userNickname", byUserNicknameFromUsersId);
            sendData.put("data", telestationReq.getData());
            System.out.println("4. sendData.put 찾기 ");
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



   private Telestation SetDataAndDataIndex(Integer usersId, Integer gamesId, TelestationReq telestationReq, Integer dataIndex){
       Telestation tel = telestationRepository.FindByUsersIdGamesIdDrawingOrder(usersId, gamesId, telestationReq.getDrawingOrder());
       tel.setData(telestationReq.getData());
       tel.setDataIndex(dataIndex);
       telestationRepository.save(tel);
       return tel;
   }


    public   Map<String,Object> showAlbum(TelestationReq telestationReq){

        if(telestationReq.getRound() != 1){
            System.out.println("2.라운드부터 여기서 투표 결과 저장합니다!");
            Telestation telestation =  findVote(telestationReq);
            System.out.println("2-1. telestationVote잘 찾아 왔는지 " + telestation.getBestVote() + "베스트 : 워스트" + telestation.getWorstVote());
            telestation.setBestVote(telestation.getBestVote() + 1);
            telestation.setWorstVote(telestation.getWorstVote() + 1);
            telestationRepository.save(telestation);

        }

        System.out.println("1. 데이타 뽑아가려고 여기에 도착했습니다!!!");
        Map<String, Object> returnData = new HashMap<String, Object>();

        Integer dataIndex = telestationRepository.findDataIndexByGamesIdUserOrder(telestationReq.getGamesId(), telestationReq.getRound());
        System.out.println("DataIndex 확인 " + dataIndex);

        List<Telestation> getAlbumList = getAlbumList(telestationReq, dataIndex);
        returnData.put("dataIndex",dataIndex);
        returnData.put("data", getAlbumList );
        System.out.println("returnData가 어떻게 찍히는지 궁금하다 "+returnData);

        return returnData;
    }



    private Telestation findVote(TelestationReq telestationReq){
        Telestation telestation = telestationRepository.findVoteByDataIndexandDrawingOrder(telestationReq.getDataIndex(), telestationReq.getDrawingOrder());
        return telestation;
    }


    private List<Telestation> getAlbumList(TelestationReq telestationReq, Integer dataIndex){
        return telestationRepository.findDataByGamesIdUserOrder(telestationReq.getGamesId(), dataIndex);
    }

    //앨범 끝나고 투표 결과{
    // /voteResult
    // 게임아이디

    // 리턴 : best 결과 젤 높은 앨범과 뭐 보고 했는지, userNickname, drawing_order
    //       worst 결과  젤 높은 앨범 뭐 보고 했는지, userNickname, drawing_order
    // => 중복에 대한 처리 DISTINCT 하면 어떻게 뜨더라 => 젤 먼저번에 나오는 사람으로 넘겨주기
    //
    //}

    public Map<String, Object> voteResult(TelestationReq telestationReq){

        Integer gameId = telestationReq.getGamesId();
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
            Integer personnel = telestationRepository.findCountByGamesIdDrawing_order(telestationReq.getGamesId());
            Telestation preBestUser = telestationRepository.findPreUserByGamesIdDataIndexDrawingOrder(telestationReq.getGamesId(), bestUser.getDataIndex(), personnel);
            preBestUserDrawingOrder = preBestUser.getDrawingOrder();
            preBestUserData = preBestUser.getData();
        }else{
            Telestation preBestUser = telestationRepository.findPreUserByGamesIdDataIndexDrawingOrder(telestationReq.getGamesId(), bestUser.getDataIndex(), bestUser.getDrawingOrder() - 1);
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
            Integer personnel = telestationRepository.findCountByGamesIdDrawing_order(telestationReq.getGamesId());
            Telestation preWorstUser = telestationRepository.findPreUserByGamesIdDataIndexDrawingOrder(telestationReq.getGamesId(), bestUser.getDataIndex(), personnel);
            preWorstUserDrawingOrder = preWorstUser.getDrawingOrder();
            preWorstUserData = preWorstUser.getData();

        }else{
            Telestation preWorstUser = telestationRepository.findPreUserByGamesIdDataIndexDrawingOrder(telestationReq.getGamesId(), bestUser.getDataIndex(), bestUser.getDrawingOrder() - 1);
            preWorstUserDrawingOrder = preWorstUser.getDrawingOrder();
            preWorstUserData = preWorstUser.getData();
        }

        Map<String, Object> res = new HashMap<>();
        Map<String, Object> worst = new HashMap<>();
        Map<String, Object> best = new HashMap<>();

        res.put("worst", worst);
        res.put("best", best);

        worst.put("Nickname", worstUserNickname);
        worst.put("preDrawingOrder", preWorstUserDrawingOrder);
        worst.put("preData", preWorstUserData);
        worst.put("DrawingOrder", worstUserDrawingOrder);
        worst.put("Data", worstUserData);

        best.put("Nickname", bestUserNickname);
        best.put("preDrawingOrder", preBestUserDrawingOrder);
        best.put("preData", preBestUserData);
        best.put("DrawingOrder", bestUserDrawingOrder);
        best.put("Data", bestUserData);

        return res;
    }



}
