package org.advisor.message.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.advisor.message.constants.MessageStatus;

import java.time.LocalDateTime;

@Data
@Entity
@Table(indexes = {
        @Index(name="idx_msg_created_at", columnList = "createdAt DESC"),
        @Index(name="idx_msg_notice_created_at", columnList = "notice DESC, createdAt DESC")
})
public class MessageData {

    @Id @GeneratedValue
    private Long seq;

    @ManyToOne
    @JoinColumn(name="mid")
    private Message message;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 45, nullable = false)
    private String gid;

    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable = false)
    private MessageStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sender_id", nullable = false)
    private String sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="receiver_id", nullable = false)
    private String receiver;

    @Column(length = 120, nullable = false)
    private String subject;

    @Lob
    private String content;

    @Column(nullable = false)
    private Boolean notice;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
