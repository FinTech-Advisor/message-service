package org.advisor.message.controllers;

import lombok.RequiredArgsConstructor;
import org.advisor.global.libs.Utils;
import org.advisor.global.rests.JSONData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final Utils utils;

    @PostMapping("/send")
    public JSONData send() {

        return new JSONData();
    }

    @GetMapping("/list")
    public JSONData list() {

        return new JSONData();
    }
    @GetMapping("/view/{seq}")
    public JSONData view() {

        return new JSONData();
    }
    @GetMapping("/notice/list")
    public JSONData NoticeList() {

        return new JSONData();
    }
    @GetMapping("/notice/view/{seq}")
    public JSONData noticeView() {

        return new JSONData();
    }

    // 웹 훅 전송
    utils.sendHook("message", data);

    return new JSONData(data);
}
