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
        // ✅ 메시지 조회 (존재하지 않으면 예외 발생)
        Message message = infoService.viewMessage(seq)
                .orElseThrow(() -> new IllegalArgumentException("해당 메시지를 찾을 수 없습니다."));

        // ✅ 현재 로그인된 사용자 가져오기
        String currentUsername = getCurrentUsername();

        // ✅ 사용자가 로그인 되어 있고, 수신자인 경우 상태 변경
        if (currentUsername != null && message.getReceiver().getEmail().equals(currentUsername)) {
            message.setStatus(MessageStatus.READ);
            messageRepository.save(message);
        }
    }

    /**
     * ✅ 현재 로그인된 사용자의 이메일(username) 가져오기
     */
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername(); // 사용자의 이메일 반환
            } else if (principal instanceof String) {
                return (String) principal; // 일반 문자열(이메일) 반환
            }
        }
        return null; // 로그인되지 않은 경우
    }
}
