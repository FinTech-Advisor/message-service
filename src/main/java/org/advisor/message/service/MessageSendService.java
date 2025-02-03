package org.advisor.message.service;

import lombok.RequiredArgsConstructor;
import org.advisor.message.controllers.RequestMessage;
import org.advisor.message.entities.Message;
import org.advisor.message.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSendService {

    private final MessageRepository messageRepository;



    public Message process(RequestMessage form) {
        // 공지 여부에 따라 수신자 설정 (공지면 "ALL_USERS"로 저장)
        String receiverEmail = form.isNotice() ? "ALL_USERS" : form.getReceiver();

        Message message = Message.builder()
                .sender()
                .receiver()
                .build();

        return messageRepository.save(message);
    }
}
