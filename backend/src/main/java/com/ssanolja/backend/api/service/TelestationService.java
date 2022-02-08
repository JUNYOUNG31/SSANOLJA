package com.ssanolja.backend.api.service;

import com.ssanolja.backend.api.response.TelestationRes;
import com.ssanolja.backend.db.entity.Game;
import com.ssanolja.backend.db.entity.Telestation;
import com.ssanolja.backend.db.entity.User;
import com.ssanolja.backend.db.repository.TelestationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TelestationService {

    private TelestationRepository telestationRepository;

    public TelestationService(TelestationRepository telestationRepository){
            this.telestationRepository = telestationRepository;
    }

    public TelestationRes userInsert(List<User> users, Game game){
            TelestationRes telestationRes = new TelestationRes();
            System.out.println(users);
            for(int i = 0; i < users.size(); i++) {
                Telestation player = telestationRepository.save(Telestation.builder()
                        .user(users.get(i))
                        .game(game)
                        .build());
            }
            return telestationRes;
    }
}
