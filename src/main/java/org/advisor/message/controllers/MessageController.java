package org.advisor.message.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.advisor.globals.exceptions.BadRequestException;
import org.advisor.globals.libs.Utils;
import org.advisor.globals.rests.JSONData;
import org.advisor.message.service.MessageService;
import org.advisor.message.validations.MessageValidator;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final MessageValidator messageValidator;
    private final Utils utils;

    /*
    @GetMapping("/write/{bid}")
    public JSONData write(@PathVariable String bid) {
        // 작성 폼으로 이동
        return null;
    }
    */

    @Operation(summary = "문의 메시지 전송", description = "회원이 관리자에게 메시지를 보냅니다.")
    @ApiResponse(responseCode = "201", description = "메시지 전송 완료")
    @PostMapping("/send/{bid}")
    public JSONData send(@RequestBody @Valid RequestMessage request, @PathVariable String bid, Errors errors) {
        // 문의 메세지(회원이 관리자에게) - 검증을 통해 에러가 발견되지 않았다면 메세지 전송에 성공
        messageValidator.validate(request, errors);
        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }
        messageService.sendMessage(request);
        return null;
    }

    @Operation(summary = "공지 메시지 전송", description = "관리자가 회원들에게 공지 메시지를 보냅니다.")
    @ApiResponse(responseCode = "201", description = "공지 메시지 전송 완료")
    @PostMapping("/notice/send")
    public JSONData sendNo() {
        // 문의 메세지(회원이 관리자에게) - 검증을 통해 에러가 발견되지 않았다면 메세지 전송에 성공
        return null;
    }

    @Operation(summary = "메시지 목록 조회", description = "특정 게시판의 메시지 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "메시지 목록 반환")
    @GetMapping("/list/{bid}")
    public JSONData list(@PathVariable("bid") String bid) {
        // 탭 형식을 사용해 페이지 이동을 하지 않아도 되도록
        return null;
    }

    @Operation(summary = "메시지 상세 조회", description = "특정 메시지의 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "메시지 상세 정보 반환")
    @GetMapping("/view/{seq}")
    public JSONData view(@PathVariable("seq") Long seq) {
        // 메세지 상세보기 / 회원 - 공지 알림, 이상 거래 알림, 본인이 보낸 문의 메시지 / 관리자 - 공지 알림, 이상 거래 알림, 모든 회원이 보낸 메시지 상세
        messageService.viewMessage(seq);
        return null;
    }
}
