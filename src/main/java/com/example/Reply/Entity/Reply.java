package com.example.Reply.Entity;

import com.example.Board.Entity.Board;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;       //댓글번호

    private String text;        //댓글내용

    private String writer;      //작성자

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
