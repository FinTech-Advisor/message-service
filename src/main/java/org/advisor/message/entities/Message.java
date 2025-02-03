package org.advisor.message.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.advisor.member.constants.Authority;
import org.advisor.message.constants.MessageReply;
import org.advisor.message.constants.MessageStatus;

@Data
@Entity
public class Message {

    @Id
    @Column(length = 45)
    private String mid;

    private String gid;

    @Column(length = 90, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority listAuthority;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority sendAuthority;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority viewAuthority;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private MessageStatus chkRead;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private MessageStatus chkUnread;

    @Enumerated(EnumType.STRING)
    private MessageReply Reply;

    @Enumerated(EnumType.STRING)
    private MessageReply noReply;

}
