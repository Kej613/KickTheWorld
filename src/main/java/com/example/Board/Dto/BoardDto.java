package com.example.Board.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class BoardDto {
    private Long bno;
    private String title;
    private String content;
    private String category; //카테고리
    private String writer;      //작성자
    private LocalDateTime regDate, modDate;
    private int replyCount; //댓글 수
}
