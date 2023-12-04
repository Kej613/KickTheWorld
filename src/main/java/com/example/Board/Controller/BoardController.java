package com.example.Board.Controller;

import com.example.Board.Dto.BoardDto;
import com.example.Board.Service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.Board.Dto.PageRequestDTO;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequestMapping("/board")
@RequiredArgsConstructor    //GuestbookService를 DI하기 위해 추가
public class BoardController {

    private final BoardService service;

    @GetMapping("/")
    public String index() {
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        model.addAttribute("result", service.getList(pageRequestDTO));
    }


    //게시물 등록
    @GetMapping("/register")
    public void register() {
    }

    @PostMapping("/register")
    public String registerPost(BoardDto dto, RedirectAttributes redirectAttributes) {
        Long bno = service.register(dto);
        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }

//    @GetMapping("/read")
//    public void read(long bno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
//
//        log.info("bno:" + bno);
//        BoardDto dto = service.read(bno);
//
//        model.addAttribute("dto", dto);
//    }

    @GetMapping({"/read", "/modify"})
    public void read(long bno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {

        BoardDto dto = service.read(bno);

        model.addAttribute("dto", dto);
    }

    //게시글 삭제
    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes redirectAttributes) {
        service.remove(bno);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }

    //게시글 수정
    @PostMapping("/modify")
    public String modify(BoardDto dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes) {

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("bno", dto.getBno());

        return "redirect:/board/list";
    }



}
