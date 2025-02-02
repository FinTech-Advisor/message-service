package org.advisor.message.service;

import lombok.RequiredArgsConstructor;
import org.advisor.member.entities.Member;
import org.advisor.member.repositories.MemberRepository;
import org.advisor.message.controllers.RequestMessage;
import org.advisor.message.entities.Message;
import org.advisor.message.repositories.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageSendService {

    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;



    @Transactional
    public Message process(RequestMessage form) {
        // 공지 여부에 따라 수신자 설정 (공지면 "ALL_USERS"로 저장)
        String receiverEmail = form.isNotice() ? "ALL_USERS" : form.getReceiver();

        Member sender = memberRepository.findByEmail(form.getSender())
                .orElseThrow(() -> new RuntimeException("Sender not found with email: " + form.getSender()));

        Member receiver = memberRepository.findByEmail(form.getReceiver())
                .orElseThrow(() -> new RuntimeException("Receiver not found with email: " + form.getReceiver()));

        Message message = Message.builder()
                .sender(sender)  // ✅ Member 객체 저장
                .receiver(receiver) // ✅ Member 객체 저장
                .build();

        return messageRepository.save(message);
    }
}
