package org.advisor.message.controllers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.advisor.message.constants.MessageReply;
import org.advisor.message.constants.MessageStatus;

@Data
public class RequestMessage {
    private Long seq; // 회원 번호
    private String mode;

    private boolean notice; // 공지알람 ( T / F )

    @Email
    private String email; // 이메일 형식

    private String mid; // 메세지 id , 공지사항 알림인지, 회원들 이슈들, 개인에게 보내질 것들 세부적으로 나눌 것들을 mid 로 지정해서 분류

    @NotBlank
    private String gid; // 그룹 id

    @NotBlank
    private String subject; // 제목

    @NotBlank
    private String content; // 내용

    private String sender; // 보내는 회원

    private String receiver; // 받는 회원

    private MessageStatus status; // 열람 미열람

    private MessageReply reply; // 답변 미답변

}
