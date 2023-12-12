package com.example.Pay.repository;

import com.example.Pay.domain.Pay;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PayRepository extends CrudRepository<Pay, String> {
    public Optional<Pay> findByOrdNo(String ordNo) throws Exception;

    public Optional<List<Pay>> findAllByPayStatusIn(List<String> statusList) throws Exception;

//    @Query("SELECT p " +
//            "FROM Pay p " +
//            "WHERE p.ordNo LIKE %:memId%")
//    List<Pay> findPayByMemId(@Param("memId") String memId);

    @Query("SELECT p FROM Pay p WHERE p.ordNo LIKE %:memId% AND p.payStatus IN ('P', 'C')")
    List<Pay> findPayByMemId(@Param("memId") String memId);
}
