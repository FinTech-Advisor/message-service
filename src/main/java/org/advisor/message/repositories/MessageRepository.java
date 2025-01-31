package org.advisor.message.repositories;

import org.advisor.member.entities.QMember;
import org.advisor.message.entities.Message;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, QuerydslPredicateExecutor<Message> {
    @EntityGraph(attributePaths = "authorities")
    Optional<Message> findByEmail(String email);

    default boolean exists(String email) {
        QMember member = QMember.member;

        return exists(member.email.eq(email));
    }

    List<Message> findByReceiver(String receiver);
}
