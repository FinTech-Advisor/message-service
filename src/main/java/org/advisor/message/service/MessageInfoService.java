package org.advisor.message.service;

import lombok.RequiredArgsConstructor;
import org.advisor.member.entities.Member;
import org.advisor.message.constants.MessageStatus;
import org.advisor.message.controllers.RequestMessage;
import org.advisor.message.entities.Message;
import org.advisor.message.exceptions.MessageNotFoundException;
import org.advisor.message.repositories.MessageRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.advisor.member.repositories.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageInfoService {

    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    // ... (loadUserByUsername 메서드 생략 - UserDetailsService 구현 안함) ...

    // 메시지 전송
    public Message sendMessage(RequestMessage request) {
        // 현재 인증된 사용자의 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentUsername = userDetails.getUsername();

        Member sender = memberRepository.findByEmail(currentUsername)
                .orElse(null); // orElse(null)을 사용하여 회원 정보를 찾지 못한 경우 null을 반환하도록 수정

        // 회원 정보를 찾지 못한 경우 예외 처리
        if (sender == null) {
            throw new RuntimeException("Sender not found with email: " + currentUsername);
        }

        Message message = Message.builder()
                .gid(request.getGid()) // 메시지 그룹 ID (Q&A 관리 등에 활용 가능)
                .sender(sender)  // 수정된 부분: 발신자 정보 (Member 객체)
                .receiver("admin") // 기본적으로 관리자가 받는다고 가정
                .subject(request.getSubject()) // 메시지 제목
                .content(request.getContent()) // 메시지 내용
                .status(MessageStatus.UNREAD) // 기본값: 읽지 않음
                .build();

        return messageRepository.save(message); // 메시지 DB에 저장
    }

    // 특정 회원이 받은 메시지 조회
    public List<Message> listMessages(String mid) {
        List<Message> messages = messageRepository.findByReceiver(mid);
        if (messages.isEmpty()) {
            throw new MessageNotFoundException();
        }
        return messages;
    }

    // 메시지 조회 시 상태 변경 (UNREAD → READ)
    public Message readMessage(Long seq) {
        Message message = messageRepository.findById(seq)
                .orElseThrow(MessageNotFoundException::new);

        // 메시지의 상태가 UNREAD인 경우에만 READ로 변경
        if (message.getStatus() == MessageStatus.UNREAD) {
            message.setStatus(MessageStatus.READ);
            messageRepository.save(message); // 상태 변경 저장
        }

        return message; // 조회된 메시지 반환
    }

    public Message get(Long seq) {
        return messageRepository.findById(seq)
                .orElseThrow(() -> new MessageNotFoundException());
    }
}