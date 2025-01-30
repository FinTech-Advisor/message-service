package org.advisor.message.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.advisor.globals.rests.JSONData;
import org.advisor.message.service.MessageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/write/{bid}")
    public JSONData write(@PathVariable String bid) {
        // 작성 폼으로 이동
        return null;
    }

    @PostMapping("/send")
    public JSONData send(@RequestBody @Valid RequestMessage request) {
        // 문의 메세지(회원이 관리자에게) - 검증을 통해 에러가 발견되지 않았다면 메세지 전송에 성공
        messageService.sendMessage(request);
        return null;
    }

    @PostMapping("/notice/send")
    public JSONData sendNo() {
        // 문의 메세지(회원이 관리자에게) - 검증을 통해 에러가 발견되지 않았다면 메세지 전송에 성공
        return null;
    }

    @GetMapping
    public JSONData list(@PathVariable("bid") String bid) {
        // 탭 형식을 사용해 페이지 이동을 하지 않아도 되도록
        return null;
    }

    @GetMapping("/view/{seq}")
    public JSONData view(@PathVariable("seq") Long seq) {
        // 메세지 상세보기 / 회원 - 공지 알림, 이상 거래 알림, 본인이 보낸 문의 메시지 / 관리자 - 공지 알림, 이상 거래 알림, 모든 회원이 보낸 메시지 상세
        messageService.viewMessage(seq);
        return null;
    }
}
