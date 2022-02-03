package com.ssanolja.backend.api.service;



import com.ssanolja.backend.db.entity.Game;
import com.ssanolja.backend.db.repository.SpyfallRepository;
import com.ssanolja.backend.db.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SpyfallService {

    private final SpyfallRepository spyfallRepository;

    public SpyfallService(SpyfallRepository spyfallRepository) {
        this.spyfallRepository = spyfallRepository;
    }

//    @Transactional
//    public void makeSpyfall(List<String> users, Game game) {
//        // 장소를 정하는 메서드
//
//        for (String userNickname :
//                users) {
//            // 직업을 하나 뽑는 메서드
//
//            spyfallRepository.save(Spyfall.builder()
//                    .user()
//                    .game(game)
//                    .spyfallJob(spyfalljob)
//                    .build());
//        }
//    }
}
