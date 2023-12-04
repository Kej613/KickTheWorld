//package com.example.Reply.Controller;
//
//import com.example.Reply.Dto.ReplyDTO;
//import com.example.Reply.Service.ReplyService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/replies")
//@Log4j2
//@RequiredArgsConstructor
//public class ReplyController {
//
//    @Autowired
//    private final ReplyService replyService;
//
//    @GetMapping(value = "/admin/trip/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<ReplyDTO>> getListByTrip(@PathVariable("id") Long id) {
//
//        log.info("id: " + id);
//
//        return new ResponseEntity<>(replyService.getList(id), HttpStatus.OK);
//    }
//}