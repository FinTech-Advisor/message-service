package org.advisor.message.repositories;

import org.advisor.message.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // 특정 회원이 보낸 메시지 조회
    List<Message> findBySender_Mid(String mid);

    // 특정 회원이 받은 메시지 조회
    List<Message> findByReceiver_Mid(String mid);

    // 특정 회원이 보낸/받은 메시지를 한 번에 조회
    List<Message> findBySender_MidOrReceiver_Mid(String mid, String mid2);
}
