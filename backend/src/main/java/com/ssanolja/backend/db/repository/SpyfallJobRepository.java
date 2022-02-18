package com.ssanolja.backend.db.repository;


import com.ssanolja.backend.db.entity.SpyfallJob;
import com.ssanolja.backend.db.entity.SpyfallPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpyfallJobRepository extends JpaRepository<SpyfallJob, Integer> {

    List<SpyfallJob> findAllBySpyfallPlace(SpyfallPlace place);
}
