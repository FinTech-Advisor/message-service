package org.advisor.message.controllers;

import lombok.Data;
import org.advisor.globals.paging.CommonSearch;
import org.advisor.message.constants.MessageReply;
import org.advisor.message.constants.MessageStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class MessageSearch extends CommonSearch {
    private String mode; // 받은 메시지인지 보낸 메세지인지(default는 받은 메시지 - receive)

    private List<String> sender; // 보낸 사람을 검색
    private List<MessageStatus> statuses; // 열람 여부에 따른 검색
    private List<MessageReply> replies; // 열람 여부에 따른 검색

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eDate;
}