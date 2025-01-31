package org.advisor.message.service;

import lombok.RequiredArgsConstructor;
import org.advisor.message.constants.MessageStatus;
import org.advisor.message.entities.Message;
import org.advisor.message.repositories.MessageRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Lazy
@Service
@RequiredArgsConstructor
public class MessageStatusService {

    private final MessageInfoService infoService;
    private final MessageRepository messageRepository;

    @Transactional
    public void change(Long seq) {
        Message message = infoService.get(seq);

        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            // UserDetails 타입인 경우
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String currentUsername = userDetails.getUsername(); // 사용자 이름 (또는 이메일) 가져오기

                // 수신자 여부 판단
                if (message.getReceiver().equals(currentUsername)) {
                    message.setStatus(MessageStatus.READ);
                    messageRepository.save(message);
                }
            }
        }
    }
}