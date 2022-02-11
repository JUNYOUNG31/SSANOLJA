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
        SetDataAndDataIndex(user1.getUsersId(), game.get().getPlayGameId(), telestationReq.getDrawingOrder(), "조성현데이타", dataIndex1);
        SetDataAndDataIndex(user2.getUsersId(), game.get().getPlayGameId(), telestationReq.getDrawingOrder(), "data:image/jpeg", dataIndex2);
        //------------------------------------------------------------------


        Telestation tel = SetDataAndDataIndex(user.getUsersId(), game.get().getPlayGameId(), telestationReq.getDrawingOrder(), telestationReq.getData(), dataIndex);


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



   private Telestation SetDataAndDataIndex(Integer usersId, Integer gamesId, Integer drawingOrder, String data, Integer dataIndex){
       Telestation tel = telestationRepository.FindByUsersIdGamesIdDrawingOrder(usersId, gamesId, drawingOrder);
       tel.setData(data);
       tel.setDataIndex(dataIndex);
       telestationRepository.save(tel);
       return tel;
   }


    public   Map<String,Object> showAlbum(TelestationReq telestationReq){
        System.out.println("111111111111111111telestationReq" + telestationReq);
        if(telestationReq.getRound() != 1){
            System.out.println("2.라운드부터 여기서 투표 결과 저장합니다!");
            System.out.println("d인덱스화긴화긴 " + telestationReq.getDataIndex());
            System.out.println("d인덱스화긴화긴 " + telestationReq.getBestVote());
            System.out.println("d인덱스화긴화긴 " + telestationReq.getWorstVote());

            Integer bestVote = telestationRepository.findBestByDataIndexandDrawingOrder(telestationReq.getDataIndex(),  telestationReq.getBestVote());
            Integer worstVote = telestationRepository.findWorstByDataIndexandDrawingOrder(telestationReq.getDataIndex(), telestationReq.getWorstVote());
            System.out.println("vote" + bestVote + "worst" + worstVote);

//            Telestation telestation = telestationRepository.findbyDataIndexandDrawingOrder(telestationReq.getDataIndex(), telestationReq.getDrawingOrder());

            Telestation bestTele = telestationRepository.findByIndexAndDrawingOrder(telestationReq.getDataIndex(), telestationReq.getBestVote());
            bestTele.setBestVote(bestVote + 1);
            telestationRepository.save(bestTele);
            Telestation worstTele = telestationRepository.findByIndexAndDrawingOrder(telestationReq.getDataIndex(), telestationReq.getWorstVote());
            worstTele.setWorstVote(worstVote + 1);
            telestationRepository.save(worstTele);

        }

        System.out.println("1. 데이타 뽑아가려고 여기에 도착했습니다!!!");
        Map<String, Object> returnData = new HashMap<String, Object>();
        System.out.println("gamesId" + telestationReq.getGameId() + "getRound" + telestationReq.getRound());
        Integer dataIndex = telestationRepository.findDataIndexByGamesIdUserOrder(telestationReq.getGameId(), telestationReq.getRound());
        System.out.println("DataIndex 확인 " + dataIndex);

//        List<Telestation> getAlbumList = getAlbumList(telestationReq, dataIndex);
        List<String> getAlbumList = new ArrayList<>();
        Integer personnel = telestationRepository.findCountByGamesIdDrawing_order(telestationReq.getGameId());
        for(int i = 1; i <= personnel; i ++){
           getAlbumList.add(getAlbumList(telestationReq, dataIndex, i));
        }

        returnData.put("dataIndex",dataIndex);
        returnData.put("dataList", getAlbumList );
        System.out.println("returnData가 어떻게 찍히는지 궁금하다 "+returnData);

        return returnData;
    }



    private String getAlbumList(TelestationReq telestationReq, Integer dataIndex, Integer drawingOrder){
        System.out.println("telestationReq로조회ㅣㅣㅣㅣ");
        return telestationRepository.findDataByGamesIdUserOrder(telestationReq.getGameId(), dataIndex, drawingOrder);
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
