package org.advisor.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.advisor.global.rests.JSONData;
import org.advisor.message.controllers.RequestMessage;
import org.advisor.message.entities.Message;
import org.advisor.message.service.MessageInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageInfoService messageInfoService;

    private RequestMessage requestMessage;

    @BeforeEach
    void setUp() {
        // 메시지 초기 데이터 생성
        requestMessage = new RequestMessage();
        requestMessage.setGid("testGid");
        requestMessage.setEmail("user@test.com");
        requestMessage.setSubject("Test Subject");
        requestMessage.setContent("Test Content");
    }

    @Test
    @DisplayName("회원이 메시지 전송 테스트")
    void sendMessageTest() throws Exception {
        String body = objectMapper.writeValueAsString(requestMessage);

        String res = mockMvc.perform(post("/message/send/user123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONData jsonData = objectMapper.readValue(res, JSONData.class);
        Message message = objectMapper.readValue(objectMapper.writeValueAsString(jsonData.getData()), Message.class);
        System.out.println("응답 데이터: " + message);
    }

    @Test
    @DisplayName("회원의 메시지 목록 조회 테스트")
    void listMessagesTest() throws Exception {
        String res = mockMvc.perform(get("/message/list/user123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONData jsonData = objectMapper.readValue(res, JSONData.class);
        System.out.println("메시지 목록: " + jsonData.getData());
    }

    @Test
    @DisplayName("메시지 상세 조회 테스트")
    void viewMessageTest() throws Exception {
        String res = mockMvc.perform(get("/message/view/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONData jsonData = objectMapper.readValue(res, JSONData.class);
        System.out.println("메시지 상세 조회 결과: " + jsonData.getData());
    }
}
