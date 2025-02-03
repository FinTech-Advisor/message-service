package org.advisor.message.repositories;

import org.advisor.message.entities.Message;
import org.advisor.message.entities.MessageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MessageDataRepository extends JpaRepository<MessageData, Long>, QuerydslPredicateExecutor<Message> {

    Optional<MessageData> findBySeq(Long seq);
}
