package org.advisor.message.controllers;

import lombok.RequiredArgsConstructor;
import org.advisor.global.rests.JSONData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/message")
@RequiredArgsConstructor
public class MessageAdminController {

    @PostMapping("/send/{mid}")
    public JSONData send() {

        return new JSONData(null);
    }

    @GetMapping("/list")
    public JSONData list() {

        return new JSONData(null);
    }

    @GetMapping("/view/{seq}")
    public JSONData view() {

        return new JSONData(null);
    }

    @PostMapping("/send")
    public JSONData sendAlert() {

        return new JSONData(null);
    }
}
