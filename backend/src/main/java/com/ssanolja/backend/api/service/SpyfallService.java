package com.ssanolja.backend.api.service;



import com.ssanolja.backend.api.response.SpyfallRes;
import com.ssanolja.backend.db.entity.*;
import com.ssanolja.backend.db.repository.SpyfallJobRepository;
import com.ssanolja.backend.db.repository.SpyfallPlaceRepository;
import com.ssanolja.backend.db.repository.SpyfallRepository;
import com.ssanolja.backend.db.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class SpyfallService {

    private final SpyfallRepository spyfallRepository;
    private final SpyfallPlaceRepository spyfallPlaceRepository;
    private final SpyfallJobRepository spyfallJobRepository;

    public SpyfallService(SpyfallRepository spyfallRepository, SpyfallPlaceRepository spyfallPlaceRepository, SpyfallJobRepository spyfallJobRepository) {
        this.spyfallRepository = spyfallRepository;
        this.spyfallPlaceRepository = spyfallPlaceRepository;
        this.spyfallJobRepository = spyfallJobRepository;
    }

    @Transactional
    public SpyfallRes makeSpyfall(List<User> users, Game game) {
        // 장소를 정하는 메서드
        Collections.shuffle(users);
        SpyfallPlace place = selectPlace();

        List<SpyfallJob> jobList = selectJobList(place,users.size());
        Collections.shuffle(jobList);

        SpyfallRes spyfallRes = new SpyfallRes();

        spyfallRes.setPlace(place.getPlaceName());

        //7명이라 하면, 6명한테 장소와 직업 부여. 마지막 사람에겐 확정적으로 스파이를 부여하고 장소는 부여하지 않음
        for(int i = 0; i < users.size(); i++) {
            if(i < users.size()-1) {
                //시민에겐 직업 부여
                Spyfall citizen = spyfallRepository.save(Spyfall.builder()
                        .user(users.get(i))
                        .game(game)
                        .spyfallJob(jobList.get(i))
                        .build());

                spyfallRes.getJobs().put(citizen.getUser().getUserNickname(), citizen.getSpyfallJob().getJobName());
            }
            else if(i == users.size()-1) {
                // 스파이 부여
                Spyfall spy = spyfallRepository.save(Spyfall.builder()
                        .user(users.get(i))
                        .game(game)
                        .build());
                spyfallRes.getJobs().put(spy.getUser().getUserNickname(), "spy");
           }
        }

        return spyfallRes;
    }


    public SpyfallPlace selectPlace() {

        List<SpyfallPlace> places = spyfallPlaceRepository.findAll();
        Random random = new Random();


        return places.get(random.nextInt(places.size()-1));
    }


    public List<SpyfallJob> selectJobList(SpyfallPlace spyfallPlace,int size) {
        List<SpyfallJob> jobs = spyfallJobRepository.findAllBySpyfallPlace(spyfallPlace);
        return jobs.subList(0,size);
    }
}