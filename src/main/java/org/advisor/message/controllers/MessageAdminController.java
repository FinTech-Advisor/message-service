package org.advisor.message.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.advisor.globals.exceptions.BadRequestException;
import org.advisor.globals.rests.JSONData;
import org.advisor.member.MemberUtil;
import org.advisor.message.service.MessageService;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/admin/message")
@RequiredArgsConstructor
public class MessageAdminController {

    private final MemberUtil memberUtil;
    private final MessageService messageService;
    private final MessageSource messageSource;

    /**
     * 특정 회원에게 메시지 전송
     */
    @PostMapping("/send/member/{mid}")
    public JSONData sendToMember(@PathVariable String mid, @RequestBody @Valid RequestMessage request) {
        if (!memberUtil.isAdmin()) {
            throw new BadRequestException(messageSource.getMessage("error.admin_only", null, Locale.getDefault()));
        }
        return new JSONData(messageService.sendMessage(request));
    }

    /**
     * 특정 그룹에게 메시지 전송
     */
    @PostMapping("/send/group/{groupId}")
    public JSONData sendToGroup(@PathVariable String groupId, @RequestBody @Valid RequestMessage request) {
        if (!memberUtil.isAdmin()) {
            throw new BadRequestException(messageSource.getMessage("error.admin_only", null, Locale.getDefault()));
        }
        return new JSONData(messageService.sendMessage(request));
    }

    /**
     * 모든 메시지 조회
     */
    @GetMapping("/list/{mid}")
    public JSONData getAllMessages(@PathVariable String mid) {
        if (!memberUtil.isAdmin()) {
            throw new BadRequestException(messageSource.getMessage("error.admin_only", null, Locale.getDefault()));
        }
        return new JSONData(messageService.listMessages(mid));
    }

    /**
     * 특정 메시지 상세 조회
     */
    @GetMapping("/view/{seq}")
    public JSONData getMessageDetails(@PathVariable Long seq) {
        if (!memberUtil.isAdmin()) {
            throw new BadRequestException(messageSource.getMessage("error.admin_only", null, Locale.getDefault()));
        }
        return new JSONData(messageService.viewMessage(seq));
    }
}
