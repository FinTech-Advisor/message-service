package org.advisor.message.repositories;

import org.advisor.message.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, QuerydslPredicateExecutor<Message> {
    Optional<Message> findByReceiver(String receiverEmail); // 수정: findByEmail -> findByReceiver
}