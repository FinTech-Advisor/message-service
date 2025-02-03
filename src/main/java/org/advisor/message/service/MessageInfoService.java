package org.advisor.message.service;

import lombok.RequiredArgsConstructor;
import org.advisor.global.paging.ListData;
import org.advisor.message.controllers.MessageSearch;
import org.advisor.message.controllers.RequestMessage;
import org.advisor.message.entities.Message;
import org.advisor.message.entities.MessageData;
import org.advisor.message.exceptions.MessageNotFoundException;
import org.advisor.message.repositories.MessageDataRepository;
import org.advisor.message.repositories.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageInfoService {

    private final MessageRepository messageRepository;
    private final MessageDataRepository messageDataRepository;
    private final ModelMapper modelMapper;

    /**
     * 메세지 한 개 조회
     *
     * @param seq
     * @return
     */
    public MessageData get(Long seq) {
        MessageData item = messageDataRepository.findBySeq(seq).orElseThrow(MessageNotFoundException::new);

        return item;
    }

    public RequestMessage getForm(Long seq) {
        return getForm(get(seq));
    }

    public ListData<MessageData> getList(MessageSearch search) {
        int page = Math.max(search.getPage(), 1);
        Message message = null;

    }

}
