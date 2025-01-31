package org.advisor.message.service;

import lombok.RequiredArgsConstructor;
import org.advisor.member.entities.Member;
import org.advisor.member.MemberUtil;
import org.advisor.member.exceptions.MemberNotFoundException;
import org.advisor.message.constants.MessageStatus;
import org.advisor.message.controllers.RequestMessage;
import org.advisor.message.entities.Message;
import org.advisor.message.repositories.MessageRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Lazy
@Service
@RequiredArgsConstructor
public class MessageSendService {
    private final MemberUtil memberUtil;
    private final MessageRepository messageRepository;

    public Message process(RequestMessage form) {

        String email = form.getEmail();
        String receiverEmail = null;

        // 공지가 아니라면 수신자의 이메일을 조회
        if (!form.isNotice()) {
            Member receiver = messageRepository.findByEmail(email)
                    .orElseThrow(MemberNotFoundException::new);
            receiverEmail = receiver.getEmail(); // Member → String 변환
        }

        Message message = Message.builder()
                .gid(form.getGid())
                .notice(form.isNotice())
                .subject(form.getSubject())
                .content(form.getContent())
                .sender(memberUtil.getMember())
                .receiver(receiverEmail)
                .status(MessageStatus.UNREAD)
                .build();

        // 메시지 저장
        return messageRepository.save(message);
    }
}
