package com.example.Member.repository;


import com.example.Member.entity.Member;
import com.example.Pay.domain.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByMemId(String memId); //유저 아이디로 회원 찾기

    @Override
    Optional<Member> findById(Long id);

    boolean existsByMemId(String memId);    //회원아이디가 있는지 여부
}