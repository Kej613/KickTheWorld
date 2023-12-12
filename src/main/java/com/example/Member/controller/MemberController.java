package com.example.Member.controller;

import com.example.Member.constant.Role;
import com.example.Member.dto.MemberFormDto;
import com.example.Member.entity.Member;
import com.example.Member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/Signup_test";
    }
    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){

        //validation
        if(bindingResult.hasErrors()){
            return "member/Signup_test";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder, Role.USER);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/LoginForm_test";
        }

        return "redirect:/";
    }

    //로그인
    @GetMapping(value = "/login")
    public String loginMember(Model model){
//        model.addAttribute("kakaoUrl", kakaoLoginService.getKakaoLogin());
        return "member/LoginForm_test";
    }


    //로그인 에러시
    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "member/LoginForm_test";
    }


//    // 회원 수정 폼
//    @GetMapping(value = "/update")
//    public String updateMemberForm(@RequestParam Long id, Model model) {
//        Optional<Member> member = memberService.findById(id);
//        model.addAttribute("member", member);
//        return "member/UpdateForm";
//    }
//
//    // 회원 수정
//    @PostMapping(value = "/update")
//    public String updateMember(@RequestParam Long id, @Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            // 유효성 검사 오류 처리
//            return "member/UpdateForm";
//        }
//
//        try {
//            memberService.updateMember(id, memberFormDto);
//        } catch (IllegalStateException e) {
//            model.addAttribute("errorMessage", e.getMessage());
//            return "member/UpdateForm";
//        }
//
//        return "redirect:/";
//    }
//
//    // 회원 삭제
//    @GetMapping(value = "/delete")
//    public String deleteMember(@RequestParam Long id) {
//        memberService.deleteMember(id);
//        return "redirect:/";
//    }

}

