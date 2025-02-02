package org.advisor.message.controllers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.advisor.message.constants.MessageStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestMessage {

    private boolean notice; // ✅ 기존 유지 (getter 자동 생성됨)

    @Email
    private String email; // 이메일 형식

    private Long seq; // 회원 번호

    private String mid; // 메세지 id , 공지사항 알림인지, 회원들 이슈들, 개인에게 보내질 것들 세부적으로 나눌 것들을 mid 로 지정해서 분류

    @NotBlank
    private String gid; // 그룹 id

    @NotBlank
    private String subject; // 제목

    @NotBlank
    private String content; // 내용

    private String sender; // ✅ 직접 값 전달
    private String receiver; // ✅ 직접 값 전달

    private MessageStatus status; // ✅ 추가: 메시지 상태 값

}
