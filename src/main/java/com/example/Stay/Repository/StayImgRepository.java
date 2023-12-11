package com.example.Stay.Repository;

import com.example.Stay.Entity.Stay;
import com.example.Stay.Entity.StayImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StayImgRepository extends JpaRepository<StayImg, Long> {
    List<StayImg> findByStay_IdOrderByIdAsc(Long id);
    void deleteByStay(Stay Stay);
    StayImg findByStay_IdAndRepimgYn(Long id, String repimgYn);
}
