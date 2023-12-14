package com.example.Board.Repository;

import com.example.Board.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, QuerydslPredicateExecutor<Board> {
//    @Query("select b, w from Board b left join b.writer w where b.bno=:bno")
//    Object getBoardWithWriter(@Param("bno") Long bno);
//
//    @Query("select b, r from Board b left join Reply r on r.board = b where b.bno = :bno")
//    List<Object[]> getBoardWithReply(@Param("bno") Long bno);
}
