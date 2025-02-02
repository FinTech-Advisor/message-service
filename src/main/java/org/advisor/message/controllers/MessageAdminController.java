package org.advisor.message.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.advisor.global.exceptions.BadRequestException;
import org.advisor.global.rests.JSONData;
import org.advisor.member.MemberUtil;
import org.advisor.message.service.MessageInfoService;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/admin/message")
@RequiredArgsConstructor
public class MessageAdminController {

    private final MemberUtil memberUtil;
    private final MessageInfoService messageInfoService;
    private final MessageSource messageSource;

    /**
     * 특정 회원에게 문의 답변 메시지 전송
     */
    @PostMapping("/send/{mid}")
    public JSONData sendToMember(@PathVariable String mid, @RequestBody @Valid RequestMessage request) {
        checkAdmin();
        return new JSONData(messageInfoService.sendMessage(request));
    }

    /**
     * 공지 알림 전송
     */
    @PostMapping("/send")
    public JSONData sendAnnouncement(@RequestBody @Valid RequestMessage request) {
        checkAdmin();
        return new JSONData(messageInfoService.sendMessage(request));
    }

    /**
     * 특정 회원의 메시지 목록 조회
     */
    @GetMapping("/list/{memberId}")
    public JSONData getAllMessages(@PathVariable String memberId) {
        checkAdmin();
        return new JSONData(messageInfoService.listMessages(memberId)); // memberId 전달
    }

    /**
     * 특정 메시지 상세 조회
     */
    @GetMapping("/view/{seq}")
    public JSONData getMessageDetails(@PathVariable Long seq) {
        checkAdmin();
        return new JSONData(messageInfoService.viewMessage(seq));
    }

    /**
     * 관리자 권한 체크
     */
    private void checkAdmin() {
        if (!memberUtil.isAdmin()) {
            throw new BadRequestException(messageSource.getMessage("error.admin_only", null, Locale.getDefault()));
        }
    }
}
