package org.advisor.message.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.advisor.message.constants.MessageStatus;
import org.advisor.message.entities.Message;
import org.advisor.message.repositories.MessageRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageStatusService {

    private final MessageInfoService infoService;
    private final MessageRepository messageRepository;

    @Transactional
    public void change(Long seq) {
        Message message = infoService.get(seq);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            String currentUsername = null;

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                currentUsername = userDetails.getUsername(); // 여기서 사용자의 email을 받아옵니다.
            } else if (principal instanceof String) {
                currentUsername = (String) principal; // Username이 email인 경우
            }

            // currentUsername이 null이 아니고, 메시지의 수신자와 현재 사용자의 이메일이 일치하는 경우에만 상태를 변경합니다.
            if (currentUsername != null && message.getReceiver().equals(currentUsername)) {
                message.setStatus(MessageStatus.READ);
                messageRepository.save(message);
            }
        }
    }
}