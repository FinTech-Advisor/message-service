package org.advisor.message.service;

import lombok.RequiredArgsConstructor;
import org.advisor.member.MemberUtil;
import org.advisor.member.exceptions.MemberNotFoundException;
import org.advisor.message.constants.MessageStatus;
import org.advisor.message.controllers.RequestMessage;
import org.advisor.message.entities.Message;
import org.advisor.message.repositories.MessageRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Lazy
@Service
@RequiredArgsConstructor
public class MessageSendService {
    private final MemberUtil memberUtil;
    private final MessageRepository messageRepository;

    @Transactional
    public Message process(RequestMessage form) {

        String email = form.getEmail();
        String receiverEmail = null;

        // 공지가 아니라면 수신자의 이메일을 조회
        if (!form.isNotice()) {
            Message message = messageRepository.findByReceiver(email) // 수정: findByEmail -> findByReceiver
                    .orElseThrow(MemberNotFoundException::new); // 수정: 예외 타입 변경 (MemberNotFoundException -> RuntimeException 또는 사용자 정의 예외)
            receiverEmail = message.getReceiver(); // Message 객체에서 수신자 이메일 가져오기
        }

        Message message = Message.builder()
                .gid(form.getGid())
                .notice(form.isNotice())
                .subject(form.getSubject())
                .content(form.getContent())
                .sender(memberUtil.getMember().getEmail())
                .receiver(receiverEmail)
                .status(MessageStatus.UNREAD)
                .build();

        // 메시지 저장
        messageRepository.saveAndFlush(message);

        return message;
    }
}