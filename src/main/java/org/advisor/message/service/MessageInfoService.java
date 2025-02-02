package org.advisor.message.service;

import lombok.RequiredArgsConstructor;
import org.advisor.global.exceptions.BadRequestException;
import org.advisor.member.entities.Member;
import org.advisor.member.repositories.MemberRepository;
import org.advisor.message.controllers.RequestMessage;
import org.advisor.message.entities.Message;
import org.advisor.message.repositories.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageInfoService {

    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    /**
     * 회원 ID로 Member 객체 조회 (중복 코드 정리)
     */
    private Member getMemberById(String id) {
        return memberRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new BadRequestException("회원을 찾을 수 없습니다."));
    }

    /**
     * 메시지 전송
     */
    @Transactional
    public Message sendMessage(RequestMessage request) {
        return messageRepository.save(Message.builder()
                .gid(request.getGid())
                .sender(getMemberById(request.getSender()))
                .receiver(getMemberById(request.getReceiver()))
                .subject(request.getSubject())
                .content(request.getContent())
                .status(request.getStatus())
                .notice(request.isNotice())
                .build());
    }

    /**
     * 특정 회원이 보낸/받은 메시지 목록 조회
     */
    @Transactional(readOnly = true)
    public List<Message> listMessages(String mid) {
        return messageRepository.findBySender_MidOrReceiver_Mid(mid, mid);
    }

    /**
     * 특정 메시지 상세 조회
     */
    @Transactional(readOnly = true)
    public Optional<Message> viewMessage(Long seq) {
        return messageRepository.findById(seq);
    }
}
