package org.advisor.message.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Data
@Entity
@IdClass(MessageViewId.class)
public class MessageView {

    @Id
    private Long seq; // 메세지 번호

    @Id
    private int hash;




}
