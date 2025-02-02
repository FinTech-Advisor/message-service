package org.advisor.message.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.advisor.global.exceptions.BadRequestException;
import org.advisor.global.libs.Utils;
import org.advisor.global.rests.JSONData;
import org.advisor.message.service.MessageInfoService;
import org.advisor.message.validations.MessageValidator;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final Utils utils;
    private final MessageInfoService messageInfoService;
    private final MessageValidator messageValidator;

    @PostMapping("/send")
    public JSONData send(@RequestBody @Valid RequestMessage request, Errors errors) {
        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }
        return new JSONData(messageInfoService.sendMessage(request));
    }

    @GetMapping("/list/{mid}")
    public JSONData list(@PathVariable String mid) {
        return new JSONData(messageInfoService.listMessages(mid)); // ✅ String → Long 변환
    }

    @GetMapping("/info/{seq}")
    public JSONData info(@PathVariable Long seq) {
        return messageInfoService.viewMessage(seq)
                .map(JSONData::new)
                .orElseThrow(() -> new BadRequestException("해당 메시지를 찾을 수 없습니다."));
    }
}
