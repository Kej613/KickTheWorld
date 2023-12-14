package com.example.Member.controller;

import com.example.Member.constant.Role;
import com.example.Member.dto.MemberFormDto;
import com.example.Member.entity.Member;
import com.example.Member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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


    // 회원 수정 폼
    @GetMapping(value = "/update/{id}")
    public String updateMemberForm(@RequestParam Long id, Model model) {
        Optional<Member> optionalMember = memberService.findById(id);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            model.addAttribute("member", member);
            return "member/UpdateForm";
        } else {
            // 회원을 찾을 수 없는 경우 처리 (Optional이 비어있는 경우)
            // 적절한 오류 페이지로 리다이렉트하거나 응용 프로그램에 맞게 처리하세요.
            model.addAttribute("errorMessage", "회원을 찾을 수 없습니다."); // 에러 메시지 추가
            return "error";
        }
//        Optional<Member> member = memberService.findById(id);
//        model.addAttribute("member", member);
//        return "member/UpdateForm";
    }

    // 회원 수정
    @PostMapping(value = "/update/{id}")
    public String updateMember(@RequestParam Long id, @Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류 처리
            return "member/UpdateForm";
        }

        try {
            memberService.updateMember(id, memberFormDto);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/UpdateForm";
        }

        return "redirect:/";
    }

    // 회원 삭제
    @GetMapping(value = "/delete")
    public String deleteMember(@RequestParam Long id) {
        memberService.deleteMember(id);
        return "redirect:/";
    }

    // 아이디중복검사
    @RequestMapping("/checkUsername")
    @ResponseBody
    public ResponseEntity<String> checkUsername(@RequestParam String memId) {
        if (memberService.isUsernameExists(memId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 아이디입니다.");
        }
        return ResponseEntity.ok("사용가능한 아이디입니다.");
    }


}

