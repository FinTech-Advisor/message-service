package org.advisor.services;

import org.advisor.message.controllers.RequestMessage;
import org.advisor.message.entities.Message;
import org.advisor.message.repositories.MessageRepository;
import org.advisor.message.service.MessageInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"default", "test"})
@AutoConfigureMockMvc
@TestPropertySource(properties = "cors.allowed=*")  // 테스트하기위한 properties 추가
public class MessageInfoServiceTest {

    @Autowired
    private MessageInfoService messageInfoService;

    @Autowired
    private MessageRepository messageRepository;

    private RequestMessage requestMessage;

    @BeforeEach
    void init() {
        requestMessage = new RequestMessage();
        requestMessage.setGid("testGid");
        requestMessage.setEmail("user@test.com");
        requestMessage.setSubject("Test Subject");
        requestMessage.setContent("Test Content");
    }

    @Test
    @DisplayName("메시지 전송 테스트")
    void sendMessageTest() {
        Message message = messageInfoService.sendMessage(requestMessage);
        assertNotNull(message);
        assertEquals("Test Subject", message.getSubject());
    }

    @Test
    @DisplayName("특정 회원의 메시지 목록 조회 테스트")
    void listMessagesTest() {
        assertDoesNotThrow(() -> messageInfoService.listMessages("user123"));
    }

    @Test
    @DisplayName("메시지 상세 조회 테스트")
    void viewMessageTest() {
        assertDoesNotThrow(() -> messageInfoService.viewMessage(1L));
    }
}
