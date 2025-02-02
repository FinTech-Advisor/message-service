package org.advisor.controller;

import org.advisor.member.MemberUtil;
import org.advisor.member.entities.Member;
import org.advisor.message.constants.MessageStatus;
import org.advisor.message.controllers.RequestMessage;
import org.advisor.message.controllers.MessageAdminController;
import org.advisor.message.entities.Message;
import org.advisor.message.service.MessageInfoService;
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
class MessageAdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MemberUtil memberUtil;

    @Mock
    private MessageInfoService messageInfoService;

    @InjectMocks
    private MessageAdminController messageAdminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mockito 초기화
        mockMvc = MockMvcBuilders.standaloneSetup(messageAdminController).build(); // MockMvc 설정
    }

    @Test
    void sendToMember_Success() throws Exception {
        when(memberUtil.isAdmin()).thenReturn(true);

        // 📌 빌더 패턴 활용하여 Member 객체 생성
        Member sender = Member.builder().mid("senderUser").build();
        Member receiver = Member.builder().mid("admin").build();

        // 📌 빌더 패턴 활용하여 Message 객체 생성
        Message testMessage = Message.builder()
                .seq(1L)
                .gid("testGid")
                .sender(sender)
                .receiver(receiver)
                .subject("Test Subject")
                .content("Test Content")
                .status(MessageStatus.UNREAD)
                .notice(false)
                .createdAt(LocalDateTime.now())
                .build();

        when(messageInfoService.sendMessage(any(RequestMessage.class))).thenReturn(testMessage);

        mockMvc.perform(post("/admin/message/send/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"subject\":\"테스트 제목\", \"content\":\"테스트 메시지\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.receiver.mid").value(receiver.getMid())); // receiver 객체 내부 값 검증
    }

    @Test
    void getAllMessages_Success() throws Exception {
        when(memberUtil.isAdmin()).thenReturn(true);
        when(messageInfoService.listMessages(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/message/list/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }
}
