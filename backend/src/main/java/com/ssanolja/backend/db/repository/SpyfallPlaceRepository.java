package com.ssanolja.backend.db.repository;


import com.ssanolja.backend.db.entity.SpyfallPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpyfallPlaceRepository extends JpaRepository<SpyfallPlace, Integer> {
}
