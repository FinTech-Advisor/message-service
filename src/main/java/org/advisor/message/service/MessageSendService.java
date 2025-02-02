package org.advisor.message.service;

import lombok.RequiredArgsConstructor;
import org.advisor.member.entities.Member;
import org.advisor.message.constants.MessageStatus;
import org.advisor.message.controllers.RequestMessage;
import org.advisor.message.entities.Message;
import org.advisor.message.repositories.MessageRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.advisor.member.repositories.MemberRepository;

@Service
@RequiredArgsConstructor
public class MessageSendService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Message process(RequestMessage form) {
        String email = form.getEmail();
        String receiverEmail = null;

        // 공지가 아니라면 수신자의 이메일을 조회
        if (!form.isNotice()) {
            Member receiver = memberRepository.findByEmail(email)
                    .orElse(null); // 수신자를 찾지 못한 경우 null 반환
            if (receiver != null) {
                receiverEmail = receiver.getEmail();
            } else {
                // 수신자를 찾지 못한 경우 예외 처리 또는 기본값 설정
                // 예: receiverEmail = "default@email.com";
                // 또는 throw new RuntimeException("Receiver not found with email: " + email);
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String senderEmail = userDetails.getUsername(); // 사용자 이메일을 senderEmail로 설정

        Member sender = memberRepository.findByEmail(senderEmail)
                .orElse(null); // 발신자를 찾지 못한 경우 null 반환

        if (sender == null) {
            // 발신자를 찾지 못한 경우 예외 처리 또는 기본값 설정
            // 예: throw new RuntimeException("Sender not found with email: " + senderEmail);
        }

        Message message = Message.builder()
                .gid(form.getGid())
                .notice(form.isNotice())
                .subject(form.getSubject())
                .content(form.getContent())
                .sender(sender) // 발신자 정보를 Member 객체로 설정
                .receiver(receiverEmail)
                .status(MessageStatus.UNREAD)
                .build();

        messageRepository.saveAndFlush(message);
        return message;
    }
}