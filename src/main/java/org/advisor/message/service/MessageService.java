package org.advisor.message.service;

import lombok.RequiredArgsConstructor;
import org.advisor.message.controllers.RequestMessage;
import org.advisor.message.entities.Message;
import org.advisor.message.repositories.MessageRepository;
import org.springframework.stereotype.Service;
import org.advisor.message.constants.MessageStatus;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    // 메시지 전송
    public Message sendMessage(RequestMessage request) {
        Message message = Message.builder()
                .gid(request.getGid()) // 메시지 그룹 ID (Q&A 관리 등에 활용 가능)
                .sender(request.getEmail())  // 발신자 정보 (이메일)
                .receiver("admin") // 기본적으로 관리자가 받는다고 가정
                .subject(request.getSubject()) // 메시지 제목
                .content(request.getContent()) // 메시지 내용
                .status(MessageStatus.UNREAD) // 기본값: 읽지 않음
                .build();

        return messageRepository.save(message); // 메세지 DB에 저장
    }

    // 메시지 조회 시 상태 변경 (UNREAD → READ)
    public Optional<Message> viewMessage(Long seq) {
        Optional<Message> messageOpt = messageRepository.findById(seq);
        messageOpt.ifPresent(message -> {
            if (message.getStatus() == MessageStatus.UNREAD) { // UNREAD 메시지라면
                message.setStatus(MessageStatus.READ); // 상태를 READ 로 변경
                messageRepository.save(message); // 변경된 상태 저장
            }
        });
        return messageOpt; // 조회된 메시지 반환
    }
}
