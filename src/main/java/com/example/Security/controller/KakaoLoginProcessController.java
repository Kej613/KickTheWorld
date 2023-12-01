package com.example.Security.controller;


import com.example.Security.entity.MsgEntity;
import com.example.Security.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import com.example.Security.dto.KakaoDTO;
@RequiredArgsConstructor
@RestController
public class KakaoLoginProcessController {

    private final KakaoLoginService kakaoService;

    @GetMapping("/oauth2/code/kakao")
    public ResponseEntity<MsgEntity>callback(HttpServletRequest request) throws Exception {

        KakaoDTO kakaoInfo = kakaoService.getKakaoInfo(request.getParameter("code"));

        return ResponseEntity.ok()
                .body(new MsgEntity("Success", kakaoInfo));

    }

}
