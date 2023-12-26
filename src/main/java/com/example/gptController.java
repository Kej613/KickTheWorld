package com.example;

import groovy.util.logging.Slf4j;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/chat-gpt")
public class gptController {
    private final ChatService chatService;
    private final ChatgptService chatgptService;

    // 이제 GET 요청도 처리 가능
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String test(@RequestParam(required = false) String question, Model model) {
//        // question이 null이면 기본값으로 설정
//        if (question == null) {
//            question = "Default question";
//        }

        String response = chatService.getChatResponse(question);

        // Response 객체에 질문과 응답 설정
        Response chatResponse = new Response();
        chatResponse.setQuestion(question);
        chatResponse.setResponse(response);

        // 모델에 chatResponse 추가
        model.addAttribute("chatResponse",response);
        return response;
    }


}