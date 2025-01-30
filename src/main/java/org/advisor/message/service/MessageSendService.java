package org.advisor.message.service;

import lombok.RequiredArgsConstructor;
import org.advisor.member.Member;
import org.advisor.member.MemberUtil;
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
    private final MessageRepository repository;
    private final Member member;

    public Message process(RequestMessage form) {

        String email = form.getEmail();
        Member receiver = !form.isNotice() ? memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new) : null;

        Message message = Message.builder()
                .gid(form.getGid())
                .notice(form.isNotice())
                .subject(form.getSubject())
                .content(form.getContent())
                .sender(memberUtil.getMember())
                .receiver(receiver)
                .status(MessageStatus.UNREAD)
                .build();

        repository.saveAndFlush(message);

        return message;
    }
}