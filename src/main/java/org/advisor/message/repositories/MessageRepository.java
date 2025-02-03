package org.advisor.message.repositories;

import org.advisor.message.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MessageRepository extends JpaRepository<Message, String>, QuerydslPredicateExecutor<Message> {

    default boolean exists(String mid) {
        QMessage message = Qmessage.mesasge;

        return exists(message.mid.eq(mid));
    }
}
