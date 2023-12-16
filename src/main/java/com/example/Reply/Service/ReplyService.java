package com.example.Reply.Service;

import com.example.Board.Entity.Board;
import com.example.Reply.Dto.ReplyDTO;
import com.example.Reply.Entity.Reply;
import com.example.Trip.Entity.Trip;

import java.util.List;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);  //댓글 등록

    List<ReplyDTO> getList(Long bno);       //특정 게시물의 댓글 목록

    void modify(ReplyDTO replyDTO); //댓글 수정

    void remove(Long rno);  //댓글 삭제

    default Reply dtoToEntity(ReplyDTO replyDTO) {
        Board board = Board.builder().bno(replyDTO.getBno()).build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .writer(replyDTO.getWriter())
                .board(board)
                .build();

        return reply;
    }

    //Reply객체를 ReplyDto로 변환, Board 객체가 필요하지 않으므로 게시물 번호만
    default ReplyDTO entityToDTO(Reply reply) {
        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .writer(reply.getWriter())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();

        return dto;
    }
}
