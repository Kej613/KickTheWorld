package com.example.Eatery.Repository;

import com.example.Eatery.Entity.Eatery;
import com.example.Eatery.Entity.EateryImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EateryImgRepository extends JpaRepository<EateryImg, Long> {

    List<EateryImg> findByEatery_IdOrderByIdAsc(Long id);

    void deleteByEatery(Eatery eatery);
}


