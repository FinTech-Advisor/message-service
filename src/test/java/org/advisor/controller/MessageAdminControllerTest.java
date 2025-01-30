package org.advisor.message.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.advisor.globals.rests.JSONData;
import org.advisor.member.MemberUtil;
import org.advisor.message.service.MessageInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles({"default", "test"})
@AutoConfigureMockMvc
@TestPropertySource(properties = "cors.allowed=*")  // 테스트하기위한 properties 추가
public class MessageAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageInfoService messageInfoService;

    @Autowired
    private MemberUtil memberUtil;

    private RequestMessage requestMessage;

    @BeforeEach
    void init() {
        requestMessage = new RequestMessage();
        requestMessage.setGid("testGid");
        requestMessage.setEmail("admin@test.com");
        requestMessage.setSubject("Admin Test Subject");
        requestMessage.setContent("Admin Test Content");
    }

    @Test
    @DisplayName("관리자가 특정 회원에게 메시지 전송 테스트")
    void sendToMemberTest() throws Exception {
        String body = objectMapper.writeValueAsString(requestMessage);

        String res = mockMvc.perform(post("/admin/message/send/member/user123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONData jsonData = objectMapper.readValue(res, JSONData.class);
        System.out.println("관리자가 회원에게 보낸 메시지: " + jsonData.getData());
    }

    @Test
    @DisplayName("관리자가 모든 회원의 메시지 목록 조회 테스트")
    void getAllMessagesTest() throws Exception {
        String res = mockMvc.perform(get("/admin/message/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONData jsonData = objectMapper.readValue(res, JSONData.class);
        System.out.println("관리자 메시지 목록: " + jsonData.getData());
    }

    @Test
    @DisplayName("관리자가 특정 메시지 상세 조회 테스트")
    void getMessageDetailsTest() throws Exception {
        String res = mockMvc.perform(get("/admin/message/view/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONData jsonData = objectMapper.readValue(res, JSONData.class);
        System.out.println("관리자가 조회한 메시지: " + jsonData.getData());
    }
}
