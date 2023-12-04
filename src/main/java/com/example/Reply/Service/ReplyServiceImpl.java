//package com.example.Reply.Service;
//
//import com.example.Reply.Dto.ReplyDTO;
//import com.example.Reply.Entity.Reply;
//import com.example.Reply.Repository.ReplyRepository;
//import com.example.Trip.Entity.Trip;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class ReplyServiceImpl implements ReplyService {
//
//    @Autowired
//    private final ReplyRepository replyRepository;
//
//    @Override
//    public Long register(ReplyDTO replyDTO) {
//        Reply reply = dtoToEntity(replyDTO);
//
//        replyRepository.save(reply);
//
//        return reply.getRno();
//    }
//
//    @Override
//    public List<ReplyDTO> getList(Long id) {
//        List<Reply> result = replyRepository.getRepliesByTripOrderByRno(Trip.builder().id(id).build());
//
//        return result.stream().map(reply -> entityToDTO(reply)).collect(Collectors.toList());
//    }
//
//    @Override
//    public void modify(ReplyDTO replyDTO) {
//        Reply reply = dtoToEntity(replyDTO);
//
//        replyRepository.save(reply);
//    }
//
//    @Override
//    public void remove(Long rno) {
//        replyRepository.deleteById(rno);
//    }
//}