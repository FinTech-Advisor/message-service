package org.advisor.message.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.advisor.message.constants.MessageStatus;

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(indexes = @Index(name="idx_notice_created_at", columnList = "notice DESC, createdAt DESC"))
public class Message {
    @Id @GeneratedValue
    private Long seq;

    @Column(length=45, nullable = false)
    private String gid;

    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable = false)
    private MessageStatus status;

    @JoinColumn(name="sender")
    private String sender;

    @JoinColumn(name="receiver")
    private String receiver;

    @Column(length = 120, nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String content;
}
