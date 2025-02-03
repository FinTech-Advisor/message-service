package org.advisor.message.service;

import lombok.RequiredArgsConstructor;
import org.advisor.message.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageStatusService {

    private final MessageInfoService infoService;
    private final MessageRepository messageRepository;

    }
}
