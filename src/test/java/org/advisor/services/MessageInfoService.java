package org.advisor.services;

import org.advisor.global.exceptions.BadRequestException;
import org.advisor.member.entities.Member;
import org.advisor.member.repositories.MemberRepository;
import org.advisor.message.constants.MessageStatus;
import org.advisor.message.controllers.RequestMessage;
import org.advisor.message.entities.Message;
import org.advisor.message.repositories.MessageRepository;
import org.advisor.message.service.MessageInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles({"default", "test"})
class MessageInfoServiceTest {

    @InjectMocks
    private MessageInfoService messageInfoService;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MemberRepository memberRepository;

    private Member sender;
    private Member receiver;
    private Message testMessage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mockito 초기화

        // 📌 테스트용 Member 객체 생성
        sender = Member.builder().seq(1L).mid("sender123").build();
        receiver = Member.builder().seq(2L).mid("receiver456").build();

        // 📌 테스트용 Message 객체 생성
        testMessage = Message.builder()
                .seq(1L)
                .gid("testGid")
                .sender(sender)
                .receiver(receiver)
                .subject("Test Subject")
                .content("Test Content")
                .status(MessageStatus.UNREAD)
                .notice(false)
                .build();
    }

    /** ✅ 메시지 전송 테스트 */
    @Test
    void sendMessage_Success() {
        // 🔹 given: 멤버가 존재함을 가정
        when(memberRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(memberRepository.findById(2L)).thenReturn(Optional.of(receiver));
        when(messageRepository.save(any())).thenReturn(testMessage);

        // 🔹 when: 메시지 전송 요청
        RequestMessage request = new RequestMessage("testGid", "1", "2", "Test Subject", "Test Content", MessageStatus.UNREAD, false);
        Message result = messageInfoService.sendMessage(request);

        // 🔹 then: 결과 검증
        assertNotNull(result);
        assertEquals("Test Subject", result.getSubject());
        assertEquals("Test Content", result.getContent());
        assertEquals(sender, result.getSender());
        assertEquals(receiver, result.getReceiver());

        // ✅ 저장 메서드가 실행되었는지 검증
        verify(messageRepository, times(1)).save(any());
    }

    /** ✅ 메시지 전송 실패 테스트 (잘못된 ID) */
    @Test
    void sendMessage_Fail_MemberNotFound() {
        // 🔹 given: 멤버가 존재하지 않음
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        // 🔹 when & then: 예외 발생 검증
        RequestMessage request = new RequestMessage();
        assertThrows(BadRequestException.class, () -> messageInfoService.sendMessage(request));

        // ✅ 저장 메서드가 실행되지 않았는지 검증
        verify(messageRepository, never()).save(any());
    }

    /** ✅ 특정 회원의 메시지 목록 조회 */
    @Test
    void listMessages_Success() {
        // 🔹 given: 특정 회원이 보낸/받은 메시지가 존재
        when(messageRepository.findBySender_MidOrReceiver_Mid("sender123", "sender123"))
                .thenReturn(List.of(testMessage));

        // 🔹 when: 메시지 목록 조회 요청
        List<Message> messages = messageInfoService.listMessages("sender123");

        // 🔹 then: 결과 검증
        assertFalse(messages.isEmpty());
        assertEquals(1, messages.size());
        assertEquals("Test Subject", messages.get(0).getSubject());

        // ✅ 메시지 조회 메서드가 실행되었는지 검증
        verify(messageRepository, times(1)).findBySender_MidOrReceiver_Mid(anyString(), anyString());
    }

    /** ✅ 특정 메시지 상세 조회 */
    @Test
    void viewMessage_Success() {
        // 🔹 given: 특정 메시지가 존재
        when(messageRepository.findById(1L)).thenReturn(Optional.of(testMessage));

        // 🔹 when: 메시지 상세 조회 요청
        Optional<Message> result = messageInfoService.viewMessage(1L);

        // 🔹 then: 결과 검증
        assertTrue(result.isPresent());
        assertEquals("Test Subject", result.get().getSubject());

        // ✅ 메시지 조회 메서드가 실행되었는지 검증
        verify(messageRepository, times(1)).findById(1L);
    }

    /** ✅ 특정 메시지 조회 실패 테스트 (존재하지 않는 메시지) */
    @Test
    void viewMessage_Fail_MessageNotFound() {
        // 🔹 given: 메시지가 존재하지 않음
        when(messageRepository.findById(1L)).thenReturn(Optional.empty());

        // 🔹 when: 메시지 조회 요청
        Optional<Message> result = messageInfoService.viewMessage(1L);

        // 🔹 then: 결과 검증
        assertTrue(result.isEmpty());

        // ✅ 메시지 조회 메서드가 실행되었는지 검증
        verify(messageRepository, times(1)).findById(1L);
    }
}
