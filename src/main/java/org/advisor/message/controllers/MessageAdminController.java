package org.advisor.message.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.advisor.globals.exceptions.BadRequestException;
import org.advisor.globals.rests.JSONData;
import org.advisor.member.MemberUtil;
import org.advisor.message.service.MessageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/message")
@RequiredArgsConstructor
public class MessageAdminController {

    private MemberUtil memberUtil;
    private MessageService messageService;

    @PostMapping("/send/{memberId}")
    public JSONData sendToMember(@PathVariable String mid, @RequestBody @Valid RequestMessage request) {
        if (!memberUtil.isAdmin()) {
            throw new BadRequestException("관리자만 사용할 수 있습니다.");
        }
        return null;
    }

    @PostMapping("/send/{mid}")
    public JSONData sendToGroup(@PathVariable String mid, @RequestBody @Valid RequestMessage request) {
        if (!memberUtil.isAdmin()) {
            throw new BadRequestException("관리자만 사용할 수 있습니다.");
        }
        return null;
    }

    @GetMapping("/list/{mid}")
    public JSONData getAllMessages(@PathVariable String mid) {
        if (!memberUtil.isAdmin()) {
            throw new BadRequestException();
        }
        return null;
    }

    @GetMapping("/view/{seq}")
    public JSONData getMessageDetails(@PathVariable Long seq) {
        if (!memberUtil.isAdmin()) {
            throw new BadRequestException();
        }
        return null;
    }
}
