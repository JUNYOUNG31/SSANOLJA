package com.ssanolja.backend.db.repository;


import com.ssanolja.backend.db.entity.Spyfall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpyfallRepository extends JpaRepository<Spyfall, Integer> {

}
