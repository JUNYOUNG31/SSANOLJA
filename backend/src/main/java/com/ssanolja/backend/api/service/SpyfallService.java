package com.ssanolja.backend.api.service;



import com.ssanolja.backend.api.response.SpyfallRes;
import com.ssanolja.backend.db.entity.*;
import com.ssanolja.backend.db.repository.SpyfallJobRepository;
import com.ssanolja.backend.db.repository.SpyfallPlaceRepository;
import com.ssanolja.backend.db.repository.SpyfallRepository;
import com.ssanolja.backend.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
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

        SpyfallRes spyfallRes = new SpyfallRes();

        spyfallRes.setPlace(place.getPlaceName());

        Spyfall spy = spyfallRepository.save(Spyfall.builder()
                .user(users.get(0))
                .game(game)
                .spyfallJob(jobList.get(0))
                .build());
        spyfallRes.getJobs().put(spy.getUser().getUserNickname(), spy.getSpyfallJob().getJobName());

//        log.info("스파이 정보 {}", spyfallRes.getJobs());

        jobList.remove(0);
        Collections.shuffle(jobList);


        //7명이라 하면, 6명한테 장소와 직업 부여. 마지막 사람에겐 확정적으로 스파이를 부여하고 장소는 부여하지 않음
        for(int i = 1; i < users.size(); i++) {
                // 스파이 부여
                Spyfall citizen = spyfallRepository.save(Spyfall.builder()
                        .user(users.get(i))
                        .game(game)
                        .spyfallJob(jobList.get(i-1))
                        .build());
                spyfallRes.getJobs().put(citizen.getUser().getUserNickname(), citizen.getSpyfallJob().getJobName());
        }
        return spyfallRes;
    }

    @Transactional
    public SpyfallPlace selectPlace() {

        List<SpyfallPlace> places = spyfallPlaceRepository.findAll();
        Random random = new Random();


        return places.get(random.nextInt(places.size()));
    }


    public List<SpyfallJob> selectJobList(SpyfallPlace spyfallPlace,int size) {
        List<SpyfallJob> jobs = spyfallJobRepository.findAllBySpyfallPlace(spyfallPlace);
        return jobs.subList(0,size);
    }
}