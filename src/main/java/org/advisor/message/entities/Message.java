package org.advisor.message.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.advisor.member.Member;
import org.advisor.member.MemberUtil;
import org.advisor.message.constants.MessageStatus;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(indexes = @Index(name="idx_notice_created_at", columnList = "notice DESC, createdAt DESC"))
public class Message {

    private Member member;
    private MemberUtil memberUtil;


    @Id @GeneratedValue
    private Long seq;

    @Column(length = 45, nullable = false)
    private String mid;

    @Column(length = 45, nullable = false)
    private String name; // ex)bid -> freetalk 일경우 name은 자유게시판 name은 한국어 변환시켰다고 생각하기 freetalk -> 자유게시판 / QNA -> 질의문답

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

    @Column(nullable = false)
    private Boolean notice; // 인덱스에서 사용됨

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public boolean isReceiver() {
        // MemberUtil을 사용하여 현재 로그인한 사용자 정보 가져오기
        Member currentUser = memberUtil.getMember();

        // 수신자와 현재 로그인한 사용자가 같은지 비교
        return this.receiver.equals(currentUser.getEmail());
    }
}
