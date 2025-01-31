package org.advisor.message.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.advisor.global.exceptions.BadRequestException;
import org.advisor.global.libs.Utils;
import org.advisor.global.rests.JSONData;
import org.advisor.message.entities.Message;
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


    /*
    @GetMapping("/write/{bid}")
    public JSONData write(@PathVariable String bid) {
        // 작성 폼으로 이동
        return null;
    }
    */

    @PostMapping("/send/{mid}")
    public JSONData send(@RequestBody @Valid RequestMessage request, @PathVariable String mid, Errors errors) {
        // 문의 메세지(회원이 관리자에게) - 검증을 통해 에러가 발견되지 않았다면 메세지 전송에 성공
        messageValidator.validate(request, errors);
        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }
        Message message = messageInfoService.sendMessage(request);
        return new JSONData(message);
    }

    @PostMapping("/send")
    public JSONData sendNo(@RequestBody @Valid RequestMessage request, Errors errors) {
        // 문의 메세지(회원이 관리자에게) - 검증을 통해 에러가 발견되지 않았다면 메세지 전송에 성공

        messageValidator.validate(request, errors);
        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }
        Message message = messageInfoService.sendMessage(request);
        return new JSONData(message);
    }

    @GetMapping("/list/{mid}")
    public JSONData list(@PathVariable("mid") String mid) {
        // 탭 형식을 사용해 페이지 이동을 하지 않아도 되도록
        return new JSONData();
    }

    @GetMapping("/info/{seq}")
    public JSONData info(@PathVariable("seq") Long seq) {
        // 메세지 상세보기 / 회원 - 공지 알림, 이상 거래 알림, 본인이 보낸 문의 메시지 / 관리자 - 공지 알림, 이상 거래 알림, 모든 회원이 보낸 메시지 상세
        // 회원 -> 관리자에게 송신?
        Message message = messageInfoService.viewMessage(seq).orElseThrow(
                () -> new BadRequestException("해당 메시지를 찾을 수 없습니다.")
        );
        return new JSONData(message);
    }
}
