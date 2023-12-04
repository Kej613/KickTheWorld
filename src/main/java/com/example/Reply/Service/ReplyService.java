//package com.example.Reply.Service;
//
//import com.example.Board.Entity.Board;
//import com.example.Reply.Dto.ReplyDTO;
//import com.example.Reply.Entity.Reply;
//import com.example.Trip.Entity.Trip;
//
//import java.util.List;
//
//public interface ReplyService {
//
//    Long register(ReplyDTO replyDTO);
//
//    List<ReplyDTO> getList(Long id);
//
//    void modify(ReplyDTO replyDTO);
//
//    void remove(Long rno);
//
//    default Reply dtoToEntity(ReplyDTO replyDTO) {
//        Board board = Board.builder().bno(replyDTO.Bno()).build();
//
//        Reply reply = Reply.builder()
//                .rno(replyDTO.getRno())
//                .text(replyDTO.getText())
//                .replier(replyDTO.getReplier())
//                .board(board)
//                .build();
//
//        return reply;
//    }
//
//    default ReplyDTO entityToDTO(Reply reply) {
//        ReplyDTO dto = ReplyDTO.builder()
//                .rno(reply.getRno())
//                .text(reply.getText())
//                .replier(reply.getReplier())
//                .regDate(reply.getRegDate())
//                .modDate(reply.getModDate())
//                .build();
//
//        return dto;
//    }
//}
