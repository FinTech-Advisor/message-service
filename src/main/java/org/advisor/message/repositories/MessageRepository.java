package org.advisor.message.repositories;

import org.advisor.message.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, QuerydslPredicateExecutor<Message> {

    // 특정 회원이 받은 메시지 조회
    List<Message> findByReceiver(String receiver);

    // 특정 회원이 보낸 메시지 조회
    List<Message> findBySender(String sender);

    // 특정 seq 값으로 메시지 조회
    Message findBySeq(Long seq);
}
