package com.example.Eatery.Repository;

import com.example.Eatery.Dto.EateryItemDto;
import com.example.Eatery.Entity.Eatery;
import com.example.Trip.Entity.Trip;
import com.example.Trip.Repository.TripRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;


public interface EateryRepository extends JpaRepository<Eatery, Long>, QuerydslPredicateExecutor<Eatery>, EateryRepositoryCustom {

    @Query("select e " +
            "from Eatery e " +
            "where e.eaterycategory like %:eaterycategory% " +
            "and e.address like %:address% " +
            "order by e.id desc")
    Page<EateryItemDto> findByAddressAndEateryCategory(@Param("eaterycategory") String eaterycategory, @Param("address") String address, Pageable pageable);

}