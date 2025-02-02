package org.advisor.message.entities;

import jakarta.persistence.*;
import lombok.*;
import org.advisor.member.entities.Member;
import org.advisor.message.constants.MessageStatus;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(name="idx_notice_created_at", columnList = "notice DESC, createdAt DESC"))
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(length = 45, nullable = false)
    private String mid;

    @Column(length = 45, nullable = false)
    private String name;

    private String gid;

    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable = false)
    private MessageStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sender_id", nullable = false)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="receiver_id", nullable = false)
    private Member receiver;

    @Column(length = 120, nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean notice;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 메시지 생성 시 자동으로 createdAt 설정
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public boolean isReceiver(Member currentUser) {
        return this.receiver.equals(currentUser);
    }
}
