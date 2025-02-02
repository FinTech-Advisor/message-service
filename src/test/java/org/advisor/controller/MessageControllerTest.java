package org.advisor.controller;

import org.advisor.global.libs.Utils;
import org.advisor.message.controllers.MessageController;
import org.advisor.message.entities.Message;
import org.advisor.message.service.MessageInfoService;
import org.advisor.message.validations.MessageValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles({"default", "test"})
@AutoConfigureMockMvc
class MessageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private Utils utils;

    @Mock
    private MessageInfoService messageInfoService;

    @Mock
    private MessageValidator messageValidator;

    @InjectMocks
    private MessageController messageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mockito 초기화
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build(); // MockMvc 설정
    }

    @Test
    void sendMessage_Success() throws Exception {
        when(messageInfoService.sendMessage(any())).thenReturn(new Message()); // ✅ JSONData 대신 Message 객체 반환

        mockMvc.perform(post("/message/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"subject\":\"테스트 제목\", \"content\":\"테스트 메시지\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void listMessages_Success() throws Exception {
        when(messageInfoService.listMessages(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/message/list/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray()); // JSON 응답이 배열인지 확인
    }

    @Test
    void viewMessage_Success() throws Exception {
        // 📌 테스트용 Message 객체 생성
        Message testMessage = Message.builder()
                .seq(1L)
                .subject("Test Subject")
                .content("Test Content")
                .createdAt(LocalDateTime.now())
                .build();

        when(messageInfoService.viewMessage(1L)).thenReturn(Optional.of(testMessage)); // ✅ JSONData 대신 Message 객체 반환

        mockMvc.perform(get("/message/info/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.subject").value("Test Subject")) // ✅ JSON 응답 값 검증
                .andExpect(jsonPath("$.data.content").value("Test Content"));
    }

    @Test
    void viewMessage_NotFound() throws Exception {
        when(messageInfoService.viewMessage(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/message/info/1"))
                .andExpect(status().isBadRequest()); // 메시지가 없을 경우 400 응답 확인
    }
}
