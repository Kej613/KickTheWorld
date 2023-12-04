//package com.example.Reply.Entity;
//
//import com.example.Board.Entity.Board;
//import jakarta.persistence.*;
//import lombok.*;
//
//
//@Entity
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@ToString(exclude = "board")
//public class Reply extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long rno;
//
//    private String text;
//
//    private String replier;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Board board;
//}
