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
            return telestationRes;
    }

    public Map<String, Object> saveData(TelestationReq telestationReq) throws Exception {

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@DRAWING ORDER 화긴화긴 " + telestationReq.getDrawingOrder());

        System.out.println("2. saveData 시작됨.");
        Integer dataIndex, preUserOrder;
        Telestation telestation = null;


        //------------------------------------
        Telestation telestation1 = null;
        Telestation telestation2 = null;
        Integer dataIndex1 = null, dataIndex2 = null;
        Integer preUserOrder1, preUserOrder2;
        //------------------------------------



        // ------------------------------- 저장과정이고
        // room_code >> find room_id
//        Room room = roomService.findRoom(telestationReq.getRoomCode()).get();
        Optional<Room> room = roomRepository.findByRoomCode(telestationReq.getRoomCode());

        // room_id >> game_id
        Optional<Game> game = gameRepository.findByRoom(room);  //game_id !!!!

        //---------------------------------------
        User user1 = userRepository.findByUserNickname("조성현"); // user_id!!!!
        User user2 = userRepository.findByUserNickname("강광은"); // user_id!!!!
        //---------------------------------------



        // user_nickname >> find user_id
        User user = userRepository.findByUserNickname(telestationReq.getUserNickname()); // user_id!!!!


        System.out.println("user null인지 확인 11111" + user);
        System.out.println("3. 게임, 유저 찾음.");

        if(telestationReq.getDrawingOrder() != 1){
            System.out.println("4. drawingOrder가 1이 아닐떄 시작.");
            System.out.println("ㄹㅇ 유저 찾아서 안나오는거임? 자바 많이 죽었네");
            System.out.println("user null인지 222222 확인" + user);
            System.out.println("game null인지 확인" + game);
            System.out.println("getDrawingOrder null인지 확인" + telestationReq.getDrawingOrder());

            //----------------------------------------------------------
            telestation1 = saveUserAndGetUserOrder(user1, game, telestationReq.getDrawingOrder());
            telestation2 = saveUserAndGetUserOrder(user2, game, telestationReq.getDrawingOrder());
            //----------------------------------------------------------

            telestation = saveUserAndGetUserOrder(user, game, telestationReq.getDrawingOrder());  //userOrder 찾아오고 사용자 정보 저장
            System.out.println("4.5 _ 확인들어갑니당");

            //---------------------------------------------------
            preUserOrder1 = (telestation1.getUserOrder() - 1 == 0 ? telestationReq.getPersonnel() : telestation1.getUserOrder() - 1); //userOrder 이전 값
            preUserOrder2 = (telestation2.getUserOrder() - 1 == 0 ? telestationReq.getPersonnel() : telestation2.getUserOrder() - 1); //userOrder 이전 값
            //---------------------------------------------------


            preUserOrder = (telestation.getUserOrder() - 1 == 0 ? telestationReq.getPersonnel() : telestation.getUserOrder() - 1); //userOrder 이전 값

            System.out.println("이전 유저오더 확인" + preUserOrder);


            //---------------------------------------------
            dataIndex1 = getNotFirstRoundDataIndex(game.get().getPlayGameId(), preUserOrder1, telestationReq.getDrawingOrder()); //두번째 라운드 부터 받은 데이터의 이전 data index 찾아오기
            dataIndex2 = getNotFirstRoundDataIndex(game.get().getPlayGameId(), preUserOrder2, telestationReq.getDrawingOrder()); //두번째 라운드 부터 받은 데이터의 이전 data index 찾아오기
            //-----------------------------------------------------

            dataIndex = getNotFirstRoundDataIndex(game.get().getPlayGameId(), preUserOrder, telestationReq.getDrawingOrder()); //두번째 라운드 부터 받은 데이터의 이전 data index 찾아오기



            System.out.println("5. drawingOrder가 1이 아닐떄 종료.");
        }else{


            //------------------------------------------------------------
            Telestation tel1 = telestationRepository.findByUsersIdAndGamesId(21, game.get().getPlayGameId());
            tel1.setDrawingOrder(1);
            telestationRepository.save(tel1);

            Telestation tel2 = telestationRepository.findByUsersIdAndGamesId(22, game.get().getPlayGameId());
            tel2.setDrawingOrder(1);
            telestationRepository.save(tel2);
            //------------------------------------------------------------


            Telestation tel = telestationRepository.findByUsersIdAndGamesId(23, game.get().getPlayGameId());
            tel.setDrawingOrder(1);
            telestationRepository.save(tel);



            System.out.println("6. drawingOrder가 1이 일때 시작.");
//            dataIndex = getFirstRoundDataIndex(game.get().getPlayGameId(),user.getUsersId()); //첫번째 라운드의 telestations_id 찾아오기


            //----------------------------------------------------------------------------------
            dataIndex1 = telestationRepository.findByTelestationsIdFromGamesIdUsersId(game.get().getPlayGameId(), 21);
            dataIndex2 = telestationRepository.findByTelestationsIdFromGamesIdUsersId(game.get().getPlayGameId(), 22);
            //----------------------------------------------------------------------------------

            dataIndex = telestationRepository.findByTelestationsIdFromGamesIdUsersId(game.get().getPlayGameId(), user.getUsersId());


            System.out.println("7. drawingOrder가 1이 일때 종료.");
        }
        
        System.out.println("dataIndex 확인 " + dataIndex);

        System.out.println("8. 텔레스테이션에 데이터 넣기 시작.");


        //------------------------------------------------------------
        System.out.println("더미데이타 dataIndex drawingOrder setting");
        Telestation tel1 = telestationRepository.findByUsersIdAndGamesIdDrawingOrder(21, game.get().getPlayGameId(), telestationReq.getDrawingOrder());
        tel1.setData("조성현데이타");
        tel1.setDataIndex(dataIndex1);
        telestationRepository.save(tel1);
        System.out.println("telestation+"+tel1);

        Telestation tel2 = telestationRepository.findByUsersIdAndGamesIdDrawingOrder(22, game.get().getPlayGameId(), telestationReq.getDrawingOrder());
        tel2.setData("강광은데이타");
        tel2.setDataIndex(dataIndex2);
        telestationRepository.save(tel2);
        System.out.println("더미데이타 dataIndex drawingOrder setting 끝");
        System.out.println("telestation+"+tel2);
        //------------------------------------------------------------


        //data, data_index, drawing_order(라운드) set
       Telestation tel = telestationRepository.findByUsersIdAndGamesIdDrawingOrder(user.getUsersId(), game.get().getPlayGameId(), telestationReq.getDrawingOrder());
        tel.setData(telestationReq.getData());
        tel.setDataIndex(dataIndex);
       telestationRepository.save(tel);
       System.out.println("telestation+"+tel);
        System.out.println("9. 텔레스테이션에 데이터 넣기 종료.");



       //-------------------------------------뿌리는 과정---------------------------------
        System.out.println("======================= 뿌리는 과정 시작 =============================");
        Map<String, Object> sendData = new HashMap<String, Object>();

        if(tel.getUserOrder() != telestationReq.getPersonnel()){ //처음으로
            System.out.println("1. 마지막에 있는 애가 아닌거지가 실행.");
           Integer byUsersId = telestationRepository.findByUsersId(tel.getUserOrder() + 1 , game.get().getPlayGameId());
            System.out.println("2. byUserId 찾기 ");
           System.out.println("============byuserId확인 " + byUsersId);

           String byUserNicknameFromUsersId = userRepository.findByUserNicknameFromUsersId(byUsersId);
            System.out.println("3. byUserNicknameFromUsersId 찾기 ");
           System.out.println("================userNickname 화긴 "+byUserNicknameFromUsersId);

            sendData.put("userNickname", byUserNicknameFromUsersId);
            sendData.put("data", telestationReq.getData());
            System.out.println("4. sendData.put 찾기 ");
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



    private Telestation saveUserAndGetUserOrder(User user, Optional<Game> game,  Integer drawingOrder) throws Exception {
        System.out.println("saveUserAndGetUserOrder 실행됨???");
        System.out.println("user: " + user + "game" + game + "drawingOrder" + drawingOrder);
        //1. userid, gamesid, drawingorder로 drawing_order 1번의 user_order 찾아옴
            Integer findUserOrder = telestationRepository.findByUsersIdGamesIdDrawingOrder(user.getUsersId(), game.get().getPlayGameId(), 1);
        System.out.println("A. findUserOrder : " + findUserOrder);
        Integer userOrder = findUserOrder;
        System.out.println("B. userOrder : " + userOrder);
//        System.out.println("userOrder 확인 "+ userOrder);

        //2. user_id, game_id, user_order 저장한다.
        //save(users_id games_id user_order)
        Telestation userInsert = telestationRepository.save(Telestation.builder()
                .user(user)
                .game(game.orElseThrow(() -> new Exception("ohlcv result set null"))) //-> game 저장 어떻게 해야할지 타입 모르겠음.
                .userOrder(userOrder)
                .drawingOrder(drawingOrder)
                .build()
        );
        System.out.println("C실행 CCCCCCCC");
        System.out.println(userInsert);
        return userInsert;
    }

    private Integer getNotFirstRoundDataIndex(Integer gamesId, Integer preUserOrder, Integer drawingOrder){

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@   gamedsID : " + gamesId);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@   preUserOrder : " + preUserOrder);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@   drawingOrder : " + drawingOrder);
        Integer findDataIndex = telestationRepository.findByGamesIdUserOrderDrawingOrder(gamesId, preUserOrder, drawingOrder - 1);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@   findDataIndex : " + findDataIndex);
        Integer dataIndex = findDataIndex;
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@   dataIndex : " + dataIndex);
        return dataIndex;

    }


    public   List<Telestation> showAlbum(TelestationReq telestationReq){

        if(telestationReq.getRound() != 1){
            /*
            1. if(round!=1) => 프론트로부터 data_index, worst, best, round, games_id
                -> 투표 결과 저장
             */
            System.out.println("2.라운드부터 여기서 투표 결과 저장합니다!");
            Telestation telestation =  findVote(telestationReq);
            System.out.println("2-1. telestationVote잘 찾아 왔는지 " + telestation.getBestVote() + "베스트 : 워스트" + telestation.getWorstVote());

            telestation.setBestVote(telestation.getBestVote() + 1);
            telestation.setWorstVote(telestation.getWorstVote() + 1);
            telestationRepository.save(telestation);

        }

        System.out.println("1. 데이타 뽑아가려고 여기에 도착했습니다!!!");
        List<Telestation> returnData = findDataAndDataIndex(telestationReq);

        System.out.println("returnData가 어떻게 찍히는지 궁금하다 "+returnData);

        return returnData;
    }



    private Telestation findVote(TelestationReq telestationReq){
        Telestation telestation = telestationRepository.findVoteByDataIndexandDrawingOrder(telestationReq.getDataIndex(), telestationReq.getDrawingOrder());
        return telestation;
    }


    private List<Telestation> findDataAndDataIndex(TelestationReq telestationReq){
        return  telestationRepository.findDataIndexDataByGamesIdUserOrder(telestationReq.getGamesId(), telestationReq.getDataIndex(), telestationReq.getGamesId(), telestationReq.getRound());
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
