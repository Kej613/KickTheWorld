package com.example.Trip.Repository;

import com.example.Stay.Entity.Stay;
import com.example.Trip.Entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface TripRepository extends JpaRepository<Trip, Long>, QuerydslPredicateExecutor<Trip>, TripRepositoryCustom{
//    @Query("SELECT t FROM Trip t WHERE t.address LIKE CONCAT('%', :query, '%') OR t.name LIKE CONCAT('%', :query, '%')")
//    Page<Trip> findByAddressOrName(@Param("query") String query, Pageable pageable);

//@Query("SELECT t FROM Trip t WHERE t.address LIKE CONCAT('%', :address, '%')")
//Page<Trip> findByAddress(@Param("address") String address, Pageable pageable);
//

    @Query("select t " +
            "from Trip t " +
            "where t.theme like %:theme% " +
            "and t.address like %:address% " +
            "order by t.id desc")
    Page<Trip> findByCategory(@Param("theme") String theme, @Param("address") String address, Pageable pageable);


}

//public interface TripRepository extends JpaRepository<Trip, Long>, QuerydslPredicateExecutor<Trip>, TripRepositoryCustom {
//    @Query("SELECT t FROM Trip t WHERE t.address LIKE CONCAT('%', :address, '%')")
//    Page<Trip> findByAddress(@Param("address") String address, Pageable pageable);
//
//}

