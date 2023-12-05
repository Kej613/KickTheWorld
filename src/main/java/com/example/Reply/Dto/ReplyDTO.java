package com.example.Reply.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDTO {

    private Long rno; //댓글번호

    private String text;  //내용

    private String replier;     //작성자

    private Long bno;        //게시글번호

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
