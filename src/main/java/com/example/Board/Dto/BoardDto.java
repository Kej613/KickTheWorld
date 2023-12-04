package com.example.Board.Dto;

import com.example.Security.entity.Member;
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
    private String writer;      //작성자
    private LocalDateTime regDate, modDate;
    private int replyCount; //댓글 수
}
